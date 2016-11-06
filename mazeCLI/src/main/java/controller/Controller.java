package controller;

import java.util.HashMap;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;
/**
 * This interface is running the game, it has View and Model and it is the bridge between them
 * when initialized has nothing and later will use setModel and SetView for that
 * @author Tuval Lifshitz
 *
 */
public interface Controller {

	/**
	 * This method sets the Model of the controller
	 * @param m the Model to be set for Controller
	 */
	void setModel(Model m);
	
	/**
	 * This method sets the View of the controller
	 * @param v the View to be set for Controller
	 */
	void setView(View v);
	/**
	 * This method transfers messages to mView to be printed on the screen
	 * @param toPrnt A String to be printed on the screen in the manner View chooses
	 */
	void PrintOnScreen(String toPrnt);
	/**
	 * This method gets the data that the user entered as input to View
	 * @return the user's String-the input
	 */
	String getDataFromScreen();
//	/**
//	 * 
//	 * @param commandToExecute
//	 * @param params
//	 */
//	void excuteCommand(Command commandToExecute, String[] params);
	/**
	 * This message returns a Maze3d according to a given name
	 * @param name the name of maze to be checked
	 * @return a maze with the name "name" if name does not exist returns false
	 */
	Maze3d getMaze(String name);
	/**
	 * This message returns the map of <String, Command>
	 * @return A map with all the strings and commands
	 */
	HashMap <String, Command> getMap();
	/**
	 * This method sets the map of Strings and Commands so will know the matches/
	 */
	void setHashMap();

	/**
	 * This method returns the Error String in the correct format to be recognized and so the user will know
	 * @param toPrint the mesage of the error String
	 * @return A String[] with error in [0] and toPrint at [1]
	 */
	String[] returnErrorString(String toPrint);

	/**
	 * This method checks if the String is a legal Commad and makes it one in the correct format if it is.
	 * if not will return a message or an error String[]
	 * @param strToChck The String that the user entered as his command
	 * @return a String in the format to be identified for the command to work
	 */
	String[] checkIfLegalCommand(String strToChck);
	
	/**
	 * This method execute the command that it receives with the parameters from param[]
	 * @param commandToExecute the command to execute 
	 * @param params all the data that the command needs to execute
	 */
	void excuteCommand(Command commandToExecute, String [] params);
}
