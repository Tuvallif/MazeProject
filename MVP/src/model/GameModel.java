package model;

import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.maze.Maze3d;
import algorithms.maze.MyMaze3d;
import algorithms.maze.MyPosition;
import algorithms.maze.Position;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.PrimMaze3dGenerator;

public class GameModel extends Observable implements Model {

	HashMap<String,Maze3d> myMazes;
	private ExecutorService threadPool;
	
	public GameModel(){
		myMazes = new HashMap<String,Maze3d>();
		threadPool = Executors.newFixedThreadPool(10);
	}
	


	@Override
	public Maze3d getMazeByName(String name) {
		return myMazes.get(name);
	}

	@Override
	public void generateMaze(String name, int height, int width, int depth) {
		if(this.nameAlreadyUsed(name) == "OK"){
			Position start = new MyPosition(0, 0, 0);
			Position goal = new MyPosition(height-1, width-1, depth-1);
			Maze3dGenerator myGnrtr = new PrimMaze3dGenerator(start, goal, height, width, depth);
			Maze3d mazeToGenerate = myGnrtr.generate();			
			myMazes.put(name, mazeToGenerate);
			String[] toSend = {"generate", name};
			this.setChanged();
			this.notifyObservers("maze " + name + " is ready");
		}else{
			this.setChanged();
			this.notifyObservers("The name is already in use, please try a different name");
		}

	}

	@Override
	public int[][] getCrossOfMaze(String name, char XYZ, int index) {
		Maze3d mazeHelper = getMazeByName(name);
		int[][] toPrint = null;
		String toNotify;
		if(XYZ == 'X' || XYZ == 'x'){
			if(index >= mazeHelper.getHeight() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			else{
			toPrint = mazeHelper.getCrossSectionByX(index);
			}
		}
		else if(XYZ == 'Y' || XYZ == 'y'){
			if(index >= mazeHelper.getWidth() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			else{
			toPrint = mazeHelper.getCrossSectionByY(index);
			}
		}
		else if(XYZ == 'Z' || XYZ == 'z'){
			if(index >= mazeHelper.getDepth() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			else{
			toPrint = mazeHelper.getCrossSectionByZ(index);
			}
		}
		else{
			toNotify = "The index you gave was not valid, please try again later.";
			toPrint = null;
			this.setChanged();
			this.notifyObservers(toNotify);
		}
		
		//printing the maze
		return toPrint;
	}

	@Override
	public void SaveMazeToFile(String mazeName, String fileName) {

		
	}

	@Override
	public File getFileFromPath(String path) {
		File fileToFind = new File(path);
		try{
		return fileToFind;
		}
		catch(NullPointerException npe){
			throw npe;
		}
	}

	@Override
	public void generateMaze(String name, byte[] mazeInByte) {
		if(this.nameAlreadyUsed(name) == "OK"){
		Maze3d mazeToSave = new MyMaze3d(mazeInByte);
		myMazes.put(name, mazeToSave);
		}
		else{
			this.setChanged();
			this.notifyObservers(this.nameAlreadyUsed(name));
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
