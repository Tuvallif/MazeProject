package controller;


import java.util.HashMap;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class MyController implements Controller{

	private View v;
	private Model m;
	private HashMap<String, Command> myHashMap;
	
	
	public MyController(){

	}
	

	public void setModel(Model m) {
		this.m = m;
		
	}
	

	public void setView(view.View v) {
		this.v = v;
		
	}


	public void setHashMap(){
		myHashMap = new HashMap<String, Command>();
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

	public void PrintOnScreen(String toPrnt) {
		v.printLineOnScreen(toPrnt);		
	}


	public String getDataFromScreen() {
		return v.getData();
	}


/*	@Override
	public Command setCommandFromString() {
		String userCommand = v.getData();
		String[] toCheck = checkIfLegalCommand(userCommand);
		Command toRtrn = myHashMap.get(toCheck);
		return toRtrn;
	}*/


	public void excuteCommand(Command commandToExecute, String [] params) {
		commandToExecute.setParams(params);
		commandToExecute.doCommand();		
	}
	
/*	@Override
	public String[] checkIfLegalCommand(String strToChck) {
		String[] helper = strToChck.split(" ");
		String[] toRtrn;
		switch(helper[0]){
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
			if(helper.length >=4){
				if(helper[1] != "3d" || helper[2] != "maze"){
					toRtrn = returnErrorString("Please enter the command in the format - generate  3d maze <name>." );
					break;
				}
			}else{
				toRtrn = returnErrorString("Please enter the command AND name in the format generate  3d maze <name>.");
				break;
			}
			toRtrn = new String[2];
			toRtrn[0] = "generate 3d maze";
			toRtrn[1] = helper[3];
			break;
		case "display":
			if(helper.length > 1 && helper[1] == "cross"){
				//making sure that all the data was given
				try{
				toRtrn = new String[4];
				toRtrn[0] = "display cross section by";
				toRtrn[1] = helper[4];
				toRtrn[2] = helper[5];
				toRtrn[3] = helper[6];
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display cross section by {X,Y,Z} <index> for <name>" );
				}
				break;
				
			}
			else if(helper.length > 1 && helper[1] == "solution"){
				
				try{
				toRtrn = new String [2];
				toRtrn[0] = "display solution";
				toRtrn[1] = helper[3];
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display solution <name>" );
				}
				break;
			}
			else{
				try{
				toRtrn = new String [2];
				toRtrn[0] = "display";
				toRtrn[1] = helper[2];
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display <name>" );
				}
				break;
			}
		case "save":
			 try{
				 if(helper[1] != "maze"){
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
			 try{
				 if(helper[1] != "maze"){
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
			 try{
				 if(helper[1] != "size"){
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
			 try{
				 if(helper[1] != "size"){
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
			try{
				toRtrn = new String[3];
				toRtrn[0] = "solve";
				toRtrn[1] = helper[1];
				toRtrn[2] = helper[2];
				if(toRtrn[2] != "bfs" || toRtrn[2] != "dfs" || toRtrn[2] != "bestfs"){
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
		
	}*/
	
	
	

	public Maze3d getMaze(String name) {
		return m.getMazeByName(name);
	}
/*
	@Override
	public String[] returnErrorString(String toPrint) {
		String[] toRtrn = new String[2];
		toRtrn[0] = "error";
		toRtrn[1] = "Please enter a path next time.";
		
		return toRtrn;
	}*/


	public HashMap<String, Command> getMap() {
		return myHashMap;
	}
	
	
	
	
}
