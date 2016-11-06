package view;

import java.io.File;
/**
 * This Interface is in charge of the view side, meaning the user can see and react to whatever is going on in the program.
 * anyone can implement as they choose
 * @author Tuval Lifshitz
 *
 */
public interface View {
	/**
	 * This method starts the view and activates everything that needs to be activated 
	 */
	void start();
	/**
	 * This method Prints a given String on the screen - with going down one line down when finished
	 * @param stringToPrint the String to print -usually a message of info
	 */
	void printLineOnScreen(String stringToPrint);
	/**
	 * This method gets the data from the bufered reader 
	 * @return the data top use as a String
	 */
	String getData();
	/**
	 * This method prints the maze to the screen - all the floors and all
	 * the floors are x-height
	 *   d e p t h
	 * w
	 * i
	 * d
	 * t
	 * h
	 * @param toPrint a board of a maze to be printed on the screen
	 */
	void PrintMazeOnScreen(int[][][] toPrint);
	/**
	 *  This method prints the cross of the maze to the screen
	 * @param crossToPrint a cross of the maze's board(X/Y/Z) at certain index
	 */
	void PrintMazeCross(int[][] crossToPrint);
	/**
	 * this method prints the content of the file to the screen
	 * @param myFile a file that its content would be printed 
	 */
	void PrintDir(File myFile);
	/**
	 * This message sets the file-goes to the path
	 * @param fileToSet a path of the file includes its name
	 */
	void setFile(File fileToSet);
	/**
	 * This method Prints a given String on the screen - without going down one line down when finished
	 * @param stringToPrint the String to print -usually a message of info
	 */
	void printOnScreen(String stringToPrint);
	
}
