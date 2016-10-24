package model;

import java.beans.FeatureDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
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

public class GameModel extends Observable implements Model,Serializable {

	HashMap<String,Maze3d> myMazes;
	HashMap<String,Solution> mySolutions;
	HashMap <String, int[][][]> myMazeBoard;
	Solution curSol; 
	private ExecutorService threadPool;
	
	//Constructor for class
	public GameModel(){
		//Initializing myMazes as empty HahMap
		myMazes = new HashMap<String,Maze3d>();
		//allowing only 10 threads
		threadPool = Executors.newFixedThreadPool(10);
		//Initializing mySolutions as empty HahMap
		mySolutions = new HashMap<String, Solution>();
		myMazeBoard =  new HashMap <String, int[][][]>();
	}
	


	@Override
	public Maze3d getMazeByName(String name) {
		//getting it from myMazes
		return myMazes.get(name);
	}

	@Override
	public void generateMaze(String name, int height, int width, int depth) {
		//checking if the name is already in use - if not enter
		if(this.nameAlreadyUsed(name) == "OK"){
			//for threads
			Future<Maze3d> futureMaze = threadPool.submit(new Callable<Maze3d>() {
				@Override
				public Maze3d call() throws Exception {
					//creating startPos
					Position start = new MyPosition(0, 0, 0);
					//creating goalPos
					Position goal = new MyPosition(height-1, width-1, depth-1);
					//creaiting generator
					Maze3dGenerator myGnrtr = new PrimMaze3dGenerator(start, goal, height, width, depth);
					//generating maze using generator
					Maze3d mazeToGenerate = myGnrtr.generate();
					//WHY??
					String[] toSend = {"generate", name};
					return mazeToGenerate;
				}
			});
			try {
				myMazes.put(name, futureMaze.get());
				//letting presenter know that the maze is ready
				this.setChanged();
				this.notifyObservers("maze " + name + " is ready");
				//EXCEPTIONS
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("There was an InterruptedException with creating the maze");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("There was an ExecutionException with creating the maze");
			}
		//if the name is already been used
		}else{
			this.setChanged();
			this.notifyObservers(this.nameAlreadyUsed(name));
		}
	}

	@Override
	public int[][] getCrossOfMaze(String name, char XYZ, int index) {
		//calling the maze by the name
		Maze3d mazeHelper = getMazeByName(name);
		int[][] toPrint = null;
		String toNotify;
		//checking if X
		if(XYZ == 'X' || XYZ == 'x'){
			//checking legal
			if(index >= mazeHelper.getHeight() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			//print by X
			else{
			toPrint = mazeHelper.getCrossSectionByX(index);
			}
		}
		//checking if Y
		else if(XYZ == 'Y' || XYZ == 'y'){
			//checking legal
			if(index >= mazeHelper.getWidth() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			//print by Y
			else{
			toPrint = mazeHelper.getCrossSectionByY(index);
			}
		}
		//checking if Z
		else if(XYZ == 'Z' || XYZ == 'z'){
			//checking legal
			if(index >= mazeHelper.getDepth() || index < 0){
				toNotify = "The index you gave was not valid, please try again later.";
				this.setChanged();
				this.notifyObservers(toNotify);
			}
			//print by Z
			else{
			toPrint = mazeHelper.getCrossSectionByZ(index);
			}
		}
		//not valid
		else{
			toNotify = "The index you gave was not valid, please try again later.";
			toPrint = null;
			this.setChanged();
			this.notifyObservers(toNotify);
		}
		
		//returning the maze by the cross and index
		return toPrint;
	}

	@Override
	public void SaveMazeToFile(String mazeName, String fileName) {

		
	}

	@Override
	public File getFileFromPath(String path) {
		//creating the file using the given path
		File fileToFind = new File(path);
		//checking if worked
		try{
		return fileToFind;
		}
		//catching id didn't work
		catch(NullPointerException npe){
			throw npe;
		}
	}

	@Override
	public void generateMaze(String name, byte[] mazeInByte) {
		//checking if the name is already in use
		if(this.nameAlreadyUsed(name) == "OK"){
			//threads
			Future<Maze3d> futureMaze = threadPool.submit(new Callable<Maze3d>() {
				@Override
				public Maze3d call() throws Exception {
					Maze3d mazeToSave = new MyMaze3d(mazeInByte);
					myMazes.put(name, mazeToSave);
					return mazeToSave;
				}
			});
			//trying to prevent Exceptions
			try {
				//putting it in the list of mazes
				myMazes.put(name, futureMaze.get());
				//notifying presenter
				this.setChanged();
				//so there will be a message on the screen
				this.notifyObservers("maze " + name + " is ready");
			} catch (InterruptedException e) {
				this.setChanged();
				this.notifyObservers("There was an InterruptedException with creating the maze");
			} catch (ExecutionException e) {
				this.setChanged();
				this.notifyObservers("There was an ExecutionException with creating the maze");
			}
		}
		//letting the user know that the name is already in use
		else{
			this.setChanged();
			this.notifyObservers(this.nameAlreadyUsed(name));
		}
	}
	/** 
	 * this method creates the message for letting the user know that the maze name is already taken or not
	 * @param name the name of the maze that the user entered 
	 * @return a String with the message to print if in Use OK otherwise
	 */
	private String nameAlreadyUsed(String name){
		String strToRtrn;
		if(this.getMazeByName(name) != null){
			strToRtrn = "the name "+ name + " is already used, please try a different name.";
		}
		//if the name is OK and not in use - returns OK
		else{
			strToRtrn = "OK";
		}
		
		return strToRtrn;
	}



	@Override
	public Solution getSlForMaze(String mazeName, String Src) {
		//if we already solved it
		if(mySolutions.containsKey(this.getMazeByName(mazeName))){
			//creating a new one - and initializing it with the values of Solution
			Solution toReturn = (mySolutions.get(this.getMazeByName(mazeName)));
//			//getting the value of Solution from Map
//			toReturn.setMySolution(mySolutions.get(this.getMazeByName(mazeName)).getMySolution());
//			for(int i = 0; i <toReturn.getMySolution().size(); i++){
//				//this.setChanged();
//				//this.notifyObservers(toReturn.getMySolution().get(i).toString());
//				
//			}
			//letting Presenter know
			this.setChanged();
			this.notifyObservers(toReturn);
			//returning the solution
			return mySolutions.get(toReturn);
		}
		//first time to calculate the solution
		else{
		//getting the maze
		Maze3d mazeToCheck = this.getMazeByName(mazeName);
		//to use while searching
		Searchable mySrchbl;
		Search mySrc;
		boolean valid = true;
		//the Solution to return when we will finish
		Solution toReturn = null;
		//trying to initialize the Searchable
		try{
			//if maze exist
			if(mazeToCheck != null){
				mySrchbl = new mySearchable(mazeToCheck);
			}
			//maze does not exist
			else{
				//letting the user know
				this.setChanged();
				this.notifyObservers("The system could not identify your maze, please try again later with a vaid maze name.\n");
				//NOT VALID
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
				this.setChanged();
				//this.notifyObservers(toReturn.getMySolution().get(i).toString());
				this.notifyObservers(toReturn);			
			}
		}catch(IndexOutOfBoundsException iobe){
			this.setChanged();
			this.notifyObservers("There was an out of abounds exception, please try again.");
		}
		//putting the Solution in  the list of Solutions
		mySolutions.put(mazeName, toReturn);
		System.out.println("there is sol");
		return toReturn;

		}

	}
	
	/**
	 * save solutions to zip file
	 */
	@Override
	public void saveSolutionToFile()
	{
		//checking if the list has something to save
		if(mySolutions.isEmpty())
		{
			System.out.println("there is no solution for saving");
			return;
		}
		//Streaming	
		ObjectOutputStream out = null;
		try 
		{
			//creating the new file
			out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.zip")));
			//writing into it
			out.writeObject(mySolutions);
			//closing the file
			out.close();
		} 
		//exceptions
		catch (FileNotFoundException e) 
		{
			System.out.println("file not found");
		} 
		catch (IOException e) 
		{
			System.out.println("save solution to zip file failed");
		}
	}
	
	
	/**
	 * load solution from zip file
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void loadSolutionFromFile() 
	{
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.zip")));
			mySolutions = (HashMap<String, Solution>) in.readObject();
			in.close();
		} 
		catch (FileNotFoundException e)
		{
			setChanged();
			notifyObservers("file not found");
		} 
		catch (IOException e)
		{
			setChanged();
			notifyObservers("load solution from zip file failed");			
		} catch (ClassNotFoundException e) {
			setChanged();
			notifyObservers("Class not found exception");	
		}
	}
	
	@Override
	public int[][] getMazeCrossByHeight(String name, int height){
		//getting the maze
		Maze3d myMazeToCalculate = this.getMazeByName(name);
		//getting the Solution
		System.out.println(name);
		System.out.println(this.getMazeByName(name).toString());
		System.out.println(mySolutions.containsKey(name));
		Solution mySolForMaze = mySolutions.get(name);
		System.out.println(mySolForMaze.toString());
		//getting it by X(height)
		int[][] mazeLevel = myMazeToCalculate.getCrossSectionByX(height);
		//going over all the cells and copying them
		for(int i = 0; i < mazeLevel.length; ++i){
			for(int j = 0; j < mazeLevel[0].length; ++j){
				//creating Position to compare for hints
				Position temp = new MyPosition(height, i, j);
				//System.out.println("height = " + height);
				//System.out.println("width = " + i+ " depth = " + j + "value = " + mazeLevel[i][j]);
				//System.out.println("board vale = " + myMazeToCalculate.getBoard()[0][i][j]);
				
				//if door
					if(mazeLevel[i][j]==0){
						//if not highest floor+ the is a door above
						if(height < myMazeToCalculate.getHeight()-1 && myMazeToCalculate.getBoard()[height + 1][i][j] != 1){
							//if not lowest floor+ has door down
							if(height > 0 && myMazeToCalculate.getBoard()[height - 1][i][j] != 1){
								//can go up and down
								//checking if there is a hint there
								if(mySolForMaze.getMySolution().contains(temp)){
									mazeLevel[i][j] = 8;
								}else{
									mazeLevel[i][j] = 3;
								}
							}
							else{
								//can go up
								//checking if there is a hint there
								if(mySolForMaze.getMySolution().contains(temp)){
									mazeLevel[i][j] = 9;
								}else{
									mazeLevel[i][j] = 4;
								}
							}
						}
						//if not lowest floor+ has door down
						else if( height > 0 && myMazeToCalculate.getBoard()[height - 1][i][j] != 1){
							//can go down
							//checking if there is a hint there
							if(mySolForMaze.getMySolution().contains(temp)){
								mazeLevel[i][j] = 7;
							}else{
								mazeLevel[i][j] = 2;
							}
						}
						else{
							//simple door
							//checking if there is a hint there
							if(mySolForMaze.getMySolution().contains(temp)){
								mazeLevel[i][j] = 6;
							}else{
								mazeLevel[i][j] = 0;
							}
						}
					}
					//a wall
					else{
						mazeLevel[i][j] = 1;
					}
					//putting the goal
					System.out.println("mazeLevel[" + i+ "][" + j +"]= " + mazeLevel[i][j]);
					if(myMazeToCalculate.getGoalPosition().equals(new MyPosition(height, i, j))){
						mazeLevel[i][j] = 5;
					}

				
			}
		}
		
		return mazeLevel;

	}
	
	@Override
	public int[][][] getMazeWithAllOptions(String name){
		//getting the maze
		Maze3d myMazeToCalculate = this.getMazeByName(name);
		//getting the solution
		Solution mySol = this.getSlForMaze(name, "BEST");
		//setting the Solution for the maze
		mySolutions.put(name, mySol);
		System.out.println("mySolutions was added with " + name + " " + mySol.toString());
		//getting the board
		int[][][] mazeLevel = myMazeToCalculate.getBoard();
		//getting the height of the maze
		int height = myMazeToCalculate.getHeight();
		//putting all the levels according to height
		for(int i = 0; i < height; ++i){
			mazeLevel[i] = getMazeCrossByHeight(name, i);
		}
		//setting the change
		//this.setChanged();
		//this.notifyObservers(mySol);
		myMazeBoard.put(name, mazeLevel);
		System.out.println("mazeboard was added with key " + name + " and value" + mazeLevel.toString());
		return mazeLevel;
	}
	

}
