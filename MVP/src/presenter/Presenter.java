package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.GameModel;
import view.CLIGameView;

public class Presenter implements Observer{

	private CLIGameView v;
	private GameModel m;
	private HashMap<String,Command> myHashMap;
	
	public Presenter(CLIGameView v, GameModel m){
		this.v = v;
		this.m = m;
		this.initializeCommandMap();
		
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o.equals(v)){
			String[] commandString = checkIfLegalCommand(arg.toString());
			Command myCmnd = this.myHashMap.get(commandString[0]);
			try{
				myCmnd.doCommand(commandString);
			}catch(NullPointerException npe){
				//NEVER HAPPENS -just in case
				v.printLineOnScreen("The command was not found");
			}
		}else if(o.equals(m)){
			if(arg.getClass().equals(String.class)){
				v.printLineOnScreen(arg.toString());
			}
			else if(arg.getClass().equals(int[][].class)){
				v.PrintMazeCross((int[][])arg);
			}
			else if((arg.getClass().equals(int[][][].class))){
				v.PrintMazeOnScreen((int[][][])arg);
			}
		}
		
	}
	
	public String[] checkIfLegalCommand(String strToChck) {
		strToChck = strToChck.trim();
		String[] helper = strToChck.split(" ", 2);
		String[] toRtrn;
		switch(helper[0].toLowerCase()){
		case "dir":
			//it has enough data
			if(helper.length >= 2){
				toRtrn = new String[2];
				toRtrn[0] = "dir";
				toRtrn[1] = helper[1];
			}
			else{
				return returnErrorString("Please enter a valid path next time.");				
			}
			break;
		case "generate":
			//it has enough data
			helper = strToChck.split(" ", 7);
			if(helper.length >=7){
				if(!helper[1].toLowerCase().equals("3d") || !helper[2].toLowerCase().equals("maze") ){
					toRtrn = returnErrorString("Please enter the command in the format - generate  3d maze <name><height, width, depth>." );
					break;
				}
			}else{
				toRtrn = returnErrorString("Please enter the command AND name in the format generate  3d maze <name><height, width, depth>.");
				break;
			}
			toRtrn = new String[5];
			toRtrn[0] = "generate 3d maze";
			toRtrn[1] = helper[3];
			toRtrn[2] = helper[4];
			toRtrn[3] = helper[5];
			toRtrn[4] = helper[6];
			break;
		case "display":
			helper = strToChck.split(" ", 3);
			//making sure it's display <name>
			if(!helper[1].toLowerCase().toLowerCase().equals("cross") && !helper[1].toLowerCase().equals("solution")){
				try{
					helper = strToChck.split(" ", 2);
					toRtrn = new String [2];
					toRtrn[0] = "display";
					toRtrn[1] = helper[1];
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display <name>" );
				}
				break;
			}
			else if(helper.length > 1 && helper[1].toLowerCase().equals("cross")){
				//Dividing it into write cells
				helper = strToChck.split(" ");
				//check that everything OK
				if(helper.length >= 8 && helper[2].toLowerCase().equals("section") && helper[3].toLowerCase().equals("by") &&
						helper[6].toLowerCase().equals("for")){
					//making sure that all the data was given
					try{
						toRtrn = new String[4];
						toRtrn[0] = "display cross section by";
						toRtrn[1] = helper[4];
						toRtrn[2] = helper[5];
						toRtrn[3] = helper[7];
					}catch(IndexOutOfBoundsException e){
						toRtrn = returnErrorString("Please enter all the command in the format - display cross section by {X,Y,Z} <index> for <name>" );
					}
				}
				else{
				toRtrn = returnErrorString("Please enter the command in the format - display cross section by {X,Y,Z} <index> for <name>" );	
				}
				break;

			}
			else if(helper.length > 1 && helper[1].toLowerCase().equals("solution")){

				try{
					toRtrn = new String [2];
					toRtrn[0] = "display solution";
					toRtrn[1] = helper[2];
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display solution <name>" );
				}
				break;
			}
			else{
				toRtrn = returnErrorString("Could not identigy your displat command-please try again later." );
				break;
			}
		case "save":
			helper = strToChck.split(" ", 4);
			try{
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - save maze <name> <file name>" );
				}else{
					toRtrn = new String[3];
					toRtrn[0] = "save maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - save maze <name> <file name>" );				 
			}
			break;
		case "load":
			helper = strToChck.split(" ", 4);
			try{
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - load maze <file name> <name>" );
				}else{
					toRtrn = new String[3];
					toRtrn[0] = "load maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - load maze <file name> <name>" );				 
			}
			break;
		case "maze":
			helper = strToChck.split(" ", 3);
			try{
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - maze size <name>" );
				}else{
					toRtrn = new String[2];
					toRtrn[0] = "maze size";
					toRtrn[1] = helper[2];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - maze size <name>" );				 
			}
			break;
		case "file":
			helper = strToChck.split(" ", 3);
			try{
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - file size <name>" );
				}else{
					toRtrn = new String[2];
					toRtrn[0] = "file size";
					toRtrn[1] = helper[2];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the file in the format - file size <name>" );				 
			}
			break;
		case "solve":
			helper = strToChck.split(" ", 3);
			try{
				toRtrn = new String[3];
				toRtrn[0] = "solve";
				toRtrn[1] = helper[1];
				toRtrn[2] = helper[2];
				if(!toRtrn[2].toLowerCase().equals("bfs") && !toRtrn[2].toLowerCase().equals("dfs") && !toRtrn[2].toLowerCase().equals("best")){
					toRtrn = returnErrorString("Please enter the name of the algorithem in the format - solve <name> <algorithm>" );
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - solve <name> <algorithm>" );				 
			}
			break;
		case "exit":
			toRtrn = new String[1];
			toRtrn[0] = "exit";
			break;
		default:
			toRtrn = returnErrorString("could not found your command - please try again later." );
		}

		return toRtrn;

	}
	
	public String[] returnErrorString(String toPrint) {
		String[] toRtrn = new String[2];
		toRtrn[0] = "error";
		toRtrn[1] = toPrint;
		
		return toRtrn;
	}

	private void initializeCommandMap(){
		myHashMap = new HashMap<String, Command>();
		myHashMap = new HashMap<>();
		Command myDirCommand = new DirCommand(v,m);
		Command myDisplayCommand = new DisplayCommand(v,m);
		Command myDisplayCrossCommand = new DisplayCrossCommand(v,m);
		Command myErrorCommand = new ErrorCommand(v);
		Command myExitCommand = new ExitCommand(v,m);
		Command myFileSizeCommand = new FileSizeCommand(v,m);
		Command myGenerateCommand = new GenerateCommand(v,m);
		Command myLoadCommand = new LoadCommand(v,m);
		Command myMazeSizeCommand = new MazeSizeCommand(v,m);
		Command mySaveCommand = new SaveCommand(v,m);
		Command mySolveCommand = new SolveCommand(v,m);
		Command myDisplaySolutionCommand = new DisplaySolutionCommand(v,m);
		myHashMap.put("dir", myDirCommand);
		myHashMap.put("generate 3d maze", myGenerateCommand);
		myHashMap.put("display", myDisplayCommand);
		myHashMap.put("display cross section by", myDisplayCrossCommand);
		myHashMap.put("save maze", mySaveCommand);
		myHashMap.put("load maze", myLoadCommand);
		myHashMap.put("maze size", myMazeSizeCommand);
		myHashMap.put("file size", myFileSizeCommand);
		myHashMap.put("solve", mySolveCommand);
		myHashMap.put("display solution", myDisplaySolutionCommand);
		myHashMap.put("exit", myExitCommand);
		myHashMap.put("error", myErrorCommand);	
	}
}