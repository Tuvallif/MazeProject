package controller;

import java.util.HashMap;

import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public interface Controller {

	void setModel(Model m);
	
	void setView(View v);
	
	void PrintOnScreen(String toPrnt);
	
	String getDataFromScreen();
	
//	void PrintMaze(String name);
	
/*	Command setCommandFromString();*/
	
	void excuteCommand(Command commandToExecute, String[] params);
	
/*	String[] checkIfLegalCommand(String strToChck);*/
	
	Maze3d getMaze(String name);

/*	String[] returnErrorString(String toPrint);*/
	
	HashMap<String, Command> getMap();

	void setHashMap();
}
