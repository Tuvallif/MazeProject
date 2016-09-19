package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.maze.Maze3d;
import algorithms.maze.Position;
import controller.Controller;

public class MyView implements View {

	private BufferedReader myBuff;
	private PrintWriter myWrt;
	private CLI myCLI;
	private Controller c;
	String[] dataAboutMaze = new String[9];
	Maze3d mazeToPrnt;

	public MyView(Controller c){
		this.c = c;
		myBuff = new BufferedReader(new InputStreamReader(System.in));
		myWrt = new PrintWriter(new OutputStreamWriter(System.out));

	}

	@Override
	public void start(){
		myCLI = new CLI(myBuff, myWrt, c.getMap());
		try{
		myCLI.start();
		} catch(IOException ioe){
			this.printLineOnScreen("The as an error with IO.");
		}

	}

	public int[]  getMazeInfo(){
		int[] toReturn = new int[9];
		String[] dataAboutMaze = new String[9];
		for(int i = 0; i< 9; i++){
			toReturn[i] = IOHelper(dataAboutMaze[i]);
		}
		return toReturn;
	}

	private int IOHelper (String toPrint){
		printLineOnScreen("Please enter the " + toPrint + "of the maze: ");
		int helper = -18;
		while(helper < 0)
		{
			try{
				helper = Integer.parseInt(myBuff.readLine());
			}catch(IOException ie){
				printLineOnScreen("There was a problem with theh " + toPrint + " you entered, please try again: ");
			}
		}
		return helper;
	}

	@Override
	public void printLineOnScreen(String stringToPrint) {
		myWrt.println(stringToPrint);	
		myWrt.flush();
	}
	
	@Override
	public void printOnScreen(String stringToPrint) {
		myWrt.print(stringToPrint);	
		myWrt.flush();
	}
	
	

	@Override
	public String getData() {
		String toRtrn = null;
		while(toRtrn == null){
			try{
				toRtrn = myBuff.readLine();
			}catch(IOException e){
				printLineOnScreen("The data was unclear, please try again: ");
			}
		}

		return toRtrn;
	}

	public void PrintMazeOnScreen(int[][][] toPrint){
		printLineOnScreen("PRINTING");
		for (int i = 0; i < toPrint.length; ++i) {
			printLineOnScreen(" i =" + i);
			for (int j = 0; j < toPrint[i].length; ++j) {
				for (int k = 0; k < toPrint[i][j].length; ++k) {
					// System.out.print(" i ="+ i);
					// System.out.print(" j ="+ j);
					// System.out.print(" k ="+ k + "value is ");
					printOnScreen(Integer.toString(toPrint[i][j][k]));
				}
				printLineOnScreen("");
			}
			printLineOnScreen("");

		}
	}

	@Override
	public void PrintMazeCross(int[][] crossToPrint) {
		for(int i = 0;i < crossToPrint.length; i++){
			for(int j = 0; j< crossToPrint[i].length; j++){
				printOnScreen(Integer.toString(crossToPrint[i][j]));
			}
			printLineOnScreen("");
		}
		printLineOnScreen("");
	}


	@Override
	public void PrintDir(File myFile) {	
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(myFile.list()));
		for(int i = 0;i < names.size();i++){
			printLineOnScreen(names.get(i));
		}
	}
	

	@Override
	public void setFile(File fileToSet) {
		
		
	}	
	
}
