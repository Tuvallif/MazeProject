package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Scanner;

import algorithms.search.Solution;
/**
 * This clas is a View class using the cli in the pc.
 * @author Tuval Lifshitz
 *
 */
public class CLIGameView extends Observable implements View  {

	BufferedReader in;
	PrintWriter out;
	
	@Override
	/**
	 * This metho9d starts the view and makes it appear on the screen with everything that needed for the program to work
	 */
	public void start() {
		//buffered reader and writer
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		String commandString = "start";
		this.printGuide();
		//SHOULD BE IN A NULL TRY CATCH???
		//while everything is ok and not exit
		while(!commandString.toLowerCase().equals("exit")){
			out.write("Please enter you command:");
			out.flush();
			try{
				commandString=in.readLine().trim();
				this.setChanged();
				this.notifyObservers(commandString);
			}catch(IOException ioe){
				this.printLineOnScreen("There was an IOesception, please try again.");
			}

		}

	}

	/**
	 * This methoid prints the guide of the cliview on the screen and the user can look and pick what he wants to do
	 */
	public void printGuide(){
		System.out.println("Please enter your command in the following format:\n"
				+ "dir <path>\n"
				+ "generate 3d maze <name> <other params>\n"
				+ "display <name>\n"
				+ "display cross section by {X,Y,Z} <index> for <name>\n"
				+ "save maze <name> <file name>\n"
				+ "load maze <file name> <name>\n"
				+ "maze size <name>\n"
				+ "file size <name>\n"
				+ "solve <name> <algorithm>\n"
				+ "display solution <name>\n"
				+ "exit\n" );
	}

	@Override
	public void printOnScreen(String stringToPrint) {
		out.print(stringToPrint);	
		out.flush();
		
	}

	@Override
	public void printLineOnScreen(String stringToPrint) {
		out.println(stringToPrint);	
		out.flush();
		
	}

	@Override
	public void PrintDir(File myFile) {
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(myFile.list()));
		for(int i = 0;i < names.size();i++){
			printLineOnScreen(names.get(i));
		}		
	}

	@Override
	public void PrintMazeOnScreen(int[][][] toPrint) {
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
	public void setSolutionList(Solution mySol) {
		// TODO Auto-generated method stub
		
	}
	
}
	


