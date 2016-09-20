package model;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.PrimMaze3dGenerator;
import controller.Controller;


public class MyModel implements Model {

	Controller c;
	HashMap<String,Maze3d> myMazes;
	private ExecutorService threadPool;
	
	public MyModel(Controller c){
		this.c = c;
		myMazes = new HashMap<String,Maze3d>();
		threadPool = Executors.newFixedThreadPool(10);
	}
	

	public void generateMaze(String name, int height, int width, int depth){
		if(this.nameAlreadyUsed(name) == "OK"){
		Position start = new MyPosition(0, 0, 0);
		Position goal = new MyPosition(height-1, width-1, depth-1);
		Maze3dGenerator myGnrtr = new PrimMaze3dGenerator(start, goal, height, width, depth);
		Maze3d mazeToGenerate = myGnrtr.generate();	
		
		myMazes.put(name, mazeToGenerate);
		c.PrintOnScreen("Maze " + name + " is ready");
		}else{
			c.PrintOnScreen(this.nameAlreadyUsed(name));
		}
	}
	
/*	public Maze3d getMaze(String name){
		
		Maze3d mazeToRtrn = myMazes.get(name);
		if(mazeToRtrn == null){
			c.PrintOnScreen("The maze was not found - please try again later");
		}
		
		return mazeToRtrn;
	}*/
	

	public void generateMaze(String name, byte[] mazeInByte){
		if(this.nameAlreadyUsed(name) == "OK"){
		Maze3d mazeToSave = new MyMaze3d(mazeInByte);
		myMazes.put(name, mazeToSave);
		}
		else{
			c.PrintOnScreen(this.nameAlreadyUsed(name));
		}
	}
	
	public Maze3d getMazeByName(String name){
		return myMazes.get(name);
	}


	public int[][] getCrossOfMaze(String name, char XYZ, int index) {
		Maze3d mazeHelper = getMazeByName(name);
		int[][] toPrint = null;
		if(XYZ == 'X' || XYZ == 'x'){
			if(index >= mazeHelper.getHeight() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
			toPrint = mazeHelper.getCrossSectionByX(index);
			}
		}
		else if(XYZ == 'Y' || XYZ == 'y'){
			if(index >= mazeHelper.getWidth() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
			toPrint = mazeHelper.getCrossSectionByY(index);
			}
		}
		else if(XYZ == 'Z' || XYZ == 'z'){
			if(index >= mazeHelper.getDepth() || index < 0){
				c.PrintOnScreen("The index you gave was not valid, please try again later.");
			}
			else{
			toPrint = mazeHelper.getCrossSectionByZ(index);
			}
		}
		else{
			c.PrintOnScreen("The index you gave was not valid, please try again later.");
			toPrint = null;
		}
		
		//printing the maze
		return toPrint;
	}


	public void SaveMazeToFile(String mazeName, String fileName) {
		
	}


	public File getFileFromPath(String path) throws NullPointerException{
		File fileToFind = new File(path);
		try{
		return fileToFind;
		}
		catch(NullPointerException npe){
			throw npe;
		}
	}
	
	private String nameAlreadyUsed(String name){
		String strToRtrn;
		if(this.getMazeByName(name) != null){
			strToRtrn = "the name "+ name + " is already used, please try a different name.";
		}
		else{
			strToRtrn = "OK";
		}
		
		return strToRtrn;
	}

}
