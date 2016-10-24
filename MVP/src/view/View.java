package view;

import java.io.File;

import algorithms.search.Solution;

public interface View {

	void start();
	
	void printOnScreen(String stringToPrint);
	
	void printLineOnScreen(String stringToPrint);
	
	void PrintDir(File myFile);
	
	void PrintMazeOnScreen(int[][][] toPrint);
	
	void PrintMazeCross(int[][] crossToPrint);

	void setSolutionList(Solution mySol);

}
