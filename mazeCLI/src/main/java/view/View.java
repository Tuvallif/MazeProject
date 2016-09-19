package view;

import java.io.File;

public interface View {

	void start();
	
	void printLineOnScreen(String stringToPrint);
	
	String getData();
	
	void PrintMazeOnScreen(int[][][] toPrint);
	
	void PrintMazeCross(int[][] crossToPrint);
	
	void PrintDir(File myFile);
	
	void setFile(File fileToSet);

	void printOnScreen(String stringToPrint);
	
}
