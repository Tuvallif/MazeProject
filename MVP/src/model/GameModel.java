package model;

import java.beans.FeatureDescriptor;
import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
			Future<Maze3d> futureMaze = threadPool.submit(new Callable<Maze3d>() {
				@Override
				public Maze3d call() throws Exception {
					Position start = new MyPosition(0, 0, 0);
					Position goal = new MyPosition(height-1, width-1, depth-1);
					Maze3dGenerator myGnrtr = new PrimMaze3dGenerator(start, goal, height, width, depth);
					Maze3d mazeToGenerate = myGnrtr.generate();						
					String[] toSend = {"generate", name};
					return mazeToGenerate;
				}
			});
			try {
				myMazes.put(name, futureMaze.get());
				this.setChanged();
				this.notifyObservers("maze " + name + " is ready");
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("There was an InterruptedException with creating the maze");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("There was an ExecutionException with creating the maze");
			}
		}else{
			this.setChanged();
			this.notifyObservers(this.nameAlreadyUsed(name));
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
			Future<Maze3d> futureMaze = threadPool.submit(new Callable<Maze3d>() {
				@Override
				public Maze3d call() throws Exception {
					Maze3d mazeToSave = new MyMaze3d(mazeInByte);
					myMazes.put(name, mazeToSave);
					return mazeToSave;
				}
			});
			try {
				myMazes.put(name, futureMaze.get());
				this.setChanged();
				this.notifyObservers("maze " + name + " is ready");
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("There was an InterruptedException with creating the maze");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("There was an ExecutionException with creating the maze");
			}
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



	@Override
	public Solution getSlForMaze(String mazeName, String Src) {
		Maze3d mazeToCheck = this.getMazeByName(mazeName);
		Searchable mySrchbl;
		Search mySrc;
		boolean valid = true;
		Solution toReturn = null;
		try{
		if(mazeToCheck != null){
			mySrchbl = new mySearchable(mazeToCheck);
		}
		else{
			this.setChanged();
			this.notifyObservers("The system could not identify your maze, please try again later with a vaid maze name.\n");
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
			this.setChanged();
			this.notifyObservers("The searchtype was not found, please try again later with BFS/DFS/BEST.\n");
			valid = false;
			return toReturn;
		}
		//doing the command if valid is true
		if(valid){
			toReturn = mySrc.FindPath();
			for(int i = 0; i <toReturn.getMySolution().size(); i++){
				this.setChanged();
				this.notifyObservers(toReturn.getMySolution().get(i).toString());
			}
		}
		}catch(IndexOutOfBoundsException iobe){
			this.setChanged();
			this.notifyObservers("There was an out of abounds exception, please try again.");
		}	
		
		return toReturn;

	}

}
