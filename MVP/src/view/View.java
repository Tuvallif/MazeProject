package view;

import java.io.File;

public interface View {

	void start();
	
	void printOnScreen(String stringToPrint);
	
	void printLineOnScreen(String stringToPrint);
	
	void PrintDir(File myFile);
	
	void PrintMazeOnScreen(int[][][] toPrint);
	
	void PrintMazeCross(int[][] crossToPrint);
	
	void setFile(File fileToSet);

}
