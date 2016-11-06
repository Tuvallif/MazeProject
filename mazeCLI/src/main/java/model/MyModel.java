package model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.demo.Searchable;
import algorithms.demo.mySearchable;
import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.PrimMaze3dGenerator;
import algorithms.search.AbstractSearch;
import algorithms.search.BFS;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Search;
import algorithms.search.Solution;
import controller.Controller;


public class MyModel implements Model {

	Controller c;
	HashMap<String,Maze3d> myMazes;
	private ExecutorService threadPool;
	/**
	 * The constructoe sets the controller as it should be in MVC
	 * @param c THhe Controller of the Model as it should be in MVC
	 */
	public MyModel(Controller c){
		this.c = c;
		myMazes = new HashMap<String,Maze3d>();
		//Allowing 10 threads
		threadPool = Executors.newFixedThreadPool(10);
	}

	@Override
	public void generateMaze(String name, int height, int width, int depth){
		//everything is good
		if(this.nameAlreadyUsed(name) == "OK"){
			Position start = new MyPosition(0, 0, 0);
			Position goal = new MyPosition(height-1, width-1, depth-1);
			Maze3dGenerator myGnrtr = new PrimMaze3dGenerator(start, goal, height, width, depth);
			//generating the maze
			Maze3d mazeToGenerate = myGnrtr.generate();	
			//putting it in the map
			myMazes.put(name, mazeToGenerate);
			//giving the message
			c.PrintOnScreen("Maze " + name + " is ready");
		}
		//problem - it is already in use(the name)
		else{
			c.PrintOnScreen(this.nameAlreadyUsed(name));
		}
	}

	@Override
	public void generateMaze(String name, byte[] mazeInByte){
		//everything is good
		if(this.nameAlreadyUsed(name) == "OK"){
			//generating the maze
			Maze3d mazeToSave = new MyMaze3d(mazeInByte);
			//putting it in the map
			myMazes.put(name, mazeToSave);
		}
		else{
			//problem - it is already in use(the name)
			c.PrintOnScreen(this.nameAlreadyUsed(name));
		}
	}
	@Override
	public Maze3d getMazeByName(String name){
		return myMazes.get(name);
	}

	@Override
	public int[][] getCrossOfMaze(String name, char XYZ, int index) {
		//getting the maze
		Maze3d mazeHelper = getMazeByName(name);
		//the board
		int[][] toPrint = null;
		//cheecking which cross
		if(XYZ == 'X' || XYZ == 'x'){
			//checking legal
			if(index >= mazeHelper.getHeight() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
				toPrint = mazeHelper.getCrossSectionByX(index);
			}
		}
		else if(XYZ == 'Y' || XYZ == 'y'){
			//checking legal
			if(index >= mazeHelper.getWidth() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
				toPrint = mazeHelper.getCrossSectionByY(index);
			}
		}
		else if(XYZ == 'Z' || XYZ == 'z'){
			//checking legal
			if(index >= mazeHelper.getDepth() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
				toPrint = mazeHelper.getCrossSectionByZ(index);
			}
		}
		//not valid
		else{
			c.PrintOnScreen("The index you gave was not valid, please try again later.");
			toPrint = null;
		}

		//printing the maze
		return toPrint;
	}

	@Override
	public void SaveMazeToFile(String mazeName, String fileName) {

	}

	@Override
	public File getFileFromPath(String path) throws NullPointerException{
		//getting the file
		File fileToFind = new File(path);
		try{
			//the file
			return fileToFind;
		}
		catch(NullPointerException npe){
			throw npe;
		}
	}
	
	/**
	 * This method creates and returns a full string for the message when the name of the maze is already been used or not
	 * @param name the name of the maze that we want to check if in used
	 * @return OK if the name is not in use, a String with the message for the user else
	 */
	private String nameAlreadyUsed(String name){
		String strToRtrn;
		//the name exist in the map
		if(this.getMazeByName(name) != null){
			strToRtrn = "the name "+ name + " is already used, please try a different name.";
		}
		//not in use
		else{
			strToRtrn = "OK";
		}

		return strToRtrn;
	}


	@Override
	public Solution getSlForMaze(String mazeName, String Src) {
		//getting the maze
		Maze3d mazeToCheck = this.getMazeByName(mazeName);
		//making it searchable 
		Searchable mySrchbl;
		Search mySrc;
		boolean valid = true;
		Solution toReturn = null;
		try{
			//maze exists
			if(mazeToCheck != null){
				mySrchbl = new mySearchable(mazeToCheck);
			}
			//no maze
			else{
				c.PrintOnScreen("The system could not identify your maze, please try again later with a vaid maze name.\n");
				valid = false;
				return null;
			}
			//checking the search type
			if(Src.toLowerCase().equals("bfs") && valid == true){
				mySrc = new BestFirstSearch(new BFS(mySrchbl, AbstractSearch.getComperator("c")));
			}
			else if(Src.toLowerCase().equals("best") && valid == true){
				mySrc = new BestFirstSearch(new BFS(mySrchbl, AbstractSearch.getComperator("best")));
			}
			else if(Src.toLowerCase().equals("dfs") && valid == true){
				mySrc = new BestFirstSearch(new DFS(mySrchbl));
			}
			//not valid
			else{
				c.PrintOnScreen("The searchtype was not found, please try again later with BFS/DFS/BEST.\n");
				valid = false;
				return toReturn;
			}
			//doing the command if valid is true
			if(valid){
				toReturn = mySrc.FindPath();
				for(int i = 0; i <toReturn.getMySolution().size(); i++){
					c.PrintOnScreen(toReturn.getMySolution().get(i).toString());
				}
			}
		}catch(IndexOutOfBoundsException iobe){
			c.PrintOnScreen("There was an out of abounds exception, please try again.");
		}	

		return toReturn;

	}

}
