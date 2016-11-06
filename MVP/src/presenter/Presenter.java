package presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import algorithms.search.Solution;
import model.GameModel;
import model.Model;
import view.CLIGameView;
import view.MazeWindow;
import view.View;
/**
 * This class is running the game and connecting View and Model
 * it is according to the design pattern and it contains two objects of View and Model, while this class is observing them
 * @author Tuval Lifshitz
 *
 */
public class Presenter implements Observer{

	private View v;
	private Model m;
	private HashMap<String,Command> myHashMap;
	ExecutorService myThreadPl = Executors.newFixedThreadPool(10);
	
	/**
	 * Constructor initializes all the variables that are required
	 * @param v the View of the presenter
	 * @param m the Model of the Presenter
	 */
	public Presenter(CLIGameView v, GameModel m){
		this.v = v;
		this.m = m;
		//initializing the map of commands
		this.initializeCommandMap();
		//getting all the old solutions
		m.loadSolutionFromFile();
		
	}

	/**
	 * Constructor initializes all the variables that are required
	 * @param v the View of the presenter
	 * @param m the Model of the Presenter
	 */
	public Presenter(MazeWindow v, GameModel m){
		this.v = v;
		this.m = m;
		//initializing the map of commands
		this.initializeCommandMap();
		//getting all the old solutions
		m.loadSolutionFromFile();
		
	}
	@Override
	public void update(Observable o, Object arg) {
		//if the update is from View
		if(o.equals(v)){
			//Debug
			//System.out.println(arg.toString());
			//getting the Command String
			String[] commandString = checkIfLegalCommand(arg.toString());
			//System.out.println(commandString.toString());
			//getting the command
			Command myCmnd = this.myHashMap.get(commandString[0]);
			//trying to do the command
			try{
				myCmnd.setParams(commandString);
				myCmnd.doCommand();
				//there was a problem
			}catch(NullPointerException npe){
				//NEVER HAPPENS -just in case
				v.printLineOnScreen("The command was not found");
			}
			//if the update is from Model
		}else if(o.equals(m)){
			//if added to it a String
			if(arg.getClass().equals(String.class)){
				//Print the String on the screen
				v.printLineOnScreen(arg.toString());
			}
			//if it was int[][]
			else if(arg.getClass().equals(int[][].class) || arg.getClass().equals(int[][][].class)){
				//then print mazeCross(it is a board)
				v.PrintMazeCross((int[][])arg);
			}
//			//it is the whole maze
//			else if((arg.getClass().equals(int[][][].class))){
//				//then print mazeCross(it is a board)
//				v.PrintMazeOnScreen((int[][][])arg);
//			}
			//it is a solution
			else if((arg.getClass().equals(Solution.class))){
				//System.out.println("arg was transfered as solution");
				//Setting the Solution for the View to have
				v.setSolutionList((Solution)arg);
			}
		}
		
	}
	/**
	 * This method check that the String of the command is a legal String and that can e understood 
	 * @param strToChck the String to check if is legal
	 * @return the divided String according to Command needs or an error if not found
	 */
	public String[] checkIfLegalCommand(String strToChck) {
		//trimming the white spaces
		strToChck = strToChck.trim();
		//cutting the first part
		String[] helper = strToChck.split(" ", 2);
		//what we will return
		String[] toRtrn;
		//checking what is the first word to try identify the command
		switch(helper[0].toLowerCase()){
		
		case "dir":
			//it has enough data
			if(helper.length >= 2){
				toRtrn = new String[2];
				//its a die command
				toRtrn[0] = "dir";
				//path
				toRtrn[1] = helper[1];
			}
			//problem reading dirCommand
			else{
				return returnErrorString("Please enter a valid path next time.");				
			}
			break;
			
		case "generate":
			//it has enough data
			helper = strToChck.split(" ", 7);
			//has enough words in the input
			if(helper.length >=7){
				//wants to generate a new maze 3d - but not valid input
				if(!helper[1].toLowerCase().equals("3d") || !helper[2].toLowerCase().equals("maze") ){
					toRtrn = returnErrorString("Please enter the command in the format - generate  3d maze <name><height, width, depth>." );
					break;
				}
				
			}
			//not enough words
			else{
				toRtrn = returnErrorString("Please enter the command AND name in the format generate  3d maze <name><height, width, depth>.");
				break;
			}
			//enough words - getting the info
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
					//getting the info
					helper = strToChck.split(" ", 2);
					toRtrn = new String [2];
					toRtrn[0] = "display";
					toRtrn[1] = helper[1];
					
				}
				//not enough words
				catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display <name>" );
				}
				break;
			}
			//making sure it is displayCross Command
			else if(helper.length > 1 && helper[1].toLowerCase().equals("cross")){
				//Dividing it into write cells
				helper = strToChck.split(" ");
				//check that everything OK
				if(helper.length >= 8 && helper[2].toLowerCase().equals("section") && helper[3].toLowerCase().equals("by") &&
						helper[6].toLowerCase().equals("for")){
					//making sure that all the data was given
					try{
						//getting the info
						toRtrn = new String[4];
						toRtrn[0] = "display cross section by";
						toRtrn[1] = helper[4];
						toRtrn[2] = helper[5];
						toRtrn[3] = helper[7];
					}catch(IndexOutOfBoundsException e){
						toRtrn = returnErrorString("Please enter all the command in the format - display cross section by {X,Y,Z} <index> for <name>" );
					}
				}
				//PROBLEMM
				else{
				toRtrn = returnErrorString("Please enter the command in the format - display cross section by {X,Y,Z} <index> for <name>" );	
				}
				break;

			}
			//displaying the Solution
			else if(helper.length > 1 && helper[1].toLowerCase().equals("solution")){

				try{
					//getting the info
					toRtrn = new String [2];
					toRtrn[0] = "display solution";
					toRtrn[1] = helper[2];
				}
				//not valid input
				catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display solution <name>" );
				}
				break;
			}
			//not Valid and could not recognize the command
			else{
				toRtrn = returnErrorString("Could not identigy your display command-please try again later." );
				break;
			}
			
		case "save":
			helper = strToChck.split(" ", 4);
			try{
				//trying to see if it is the right format - if not
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - save maze <name> <file name>" );
				}
				//valid format
				else{
					//getting the info
					toRtrn = new String[3];
					toRtrn[0] = "save maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
			}
			//not long enough String []
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - save maze <name> <file name>" );				 
			}
			break;
			
		case "load":
			helper = strToChck.split(" ", 4);
			try{
				//trying to see if it is the right format - if not
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - load maze <file name> <name>" );
				}else{
					//gweting the info
					toRtrn = new String[3];
					toRtrn[0] = "load maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
			}
			//String[] is too short
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - load maze <file name> <name>" );				 
			}
			break;
			
		case "maze":
			helper = strToChck.split(" ", 3);
			try{
				//trying to see if it is the right format - if not
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - maze size <name>" );
				}
				//right format
				else{
					toRtrn = new String[2];
					toRtrn[0] = "maze size";
					toRtrn[1] = helper[2];
				}
			}
			//String[] not long enough
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - maze size <name>" );				 
			}
			break;
			
		case "file":
			helper = strToChck.split(" ", 3);
			try{
				//trying to see if it is the right format - if not
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - file size <name>" );
				}
				//valid
				else{
					//getting the info
					toRtrn = new String[2];
					toRtrn[0] = "file size";
					toRtrn[1] = helper[2];
				}
			}
			//String[] too short
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the file in the format - file size <name>" );				 
			}
			break;
			
		case "solve":
			helper = strToChck.split(" ", 3);
			try{
				//trying to get the info
				toRtrn = new String[3];
				toRtrn[0] = "solve";
				toRtrn[1] = helper[1];
				toRtrn[2] = helper[2];
				//if not valid search type
				if(!toRtrn[2].toLowerCase().equals("bfs") && !toRtrn[2].toLowerCase().equals("dfs") && !toRtrn[2].toLowerCase().equals("best")){
					toRtrn = returnErrorString("Please enter the name of the algorithem in the format - solve <name> <algorithm>" );
				}
			}
			//if String[] too short
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - solve <name> <algorithm>" );				 
			}
			break;
			
		case "exit":
			toRtrn = new String[1];
			toRtrn[0] = "exit";
			break;
			//cpould not find the command at all - error
		default:
			toRtrn = returnErrorString("could not find your command - please try again later." );
		}

		return toRtrn;

	}
	/**
	 * This method creates an error String in a String[] format
	 * @param toPrint The String of the Error
	 * @return the same String only with error on the first5 cell
	 */
	public String[] returnErrorString(String toPrint) {
		String[] toRtrn = new String[2];
		toRtrn[0] = "error";
		toRtrn[1] = toPrint;
		
		return toRtrn;
	}
	/**
	 * This method initialize all the map of the coomands with Strings
	 */
	private void initializeCommandMap(){
		//initializing
		myHashMap = new HashMap<String, Command>();
		//creating all the command
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
		//putting them in the map
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
