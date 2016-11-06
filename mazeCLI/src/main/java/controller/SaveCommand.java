package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import algorithms.maze.Maze3d;
import model.Model;
import view.View;
/**
 * This class is to save the mazse in the file
 * @author Tuval Lifshitz
 *
 */
public class SaveCommand implements Command{

	View v;
	Model m;
	Maze3d mazeToUse;
	File fileToSave;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public SaveCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}


	@Override
	public void doCommand() {
		//if maze is null
		if(mazeToUse == null){
			v.printLineOnScreen("The requested maze was not found, please try again later with a correct name.");
		}
		//maze is NOT null
		else{
			try{
				//file output stream
			FileOutputStream fos = new FileOutputStream(fileToSave);
			//writing and closing
			fos.write(mazeToUse.toByteArray());
			fos.close();
			}
			//NO file
			catch(FileNotFoundException fnfe){
				v.printLineOnScreen("The file was not found, please type a correct name next time.");
			} 
			//EXCEPTION
			catch (IOException ioe) {
				v.printLineOnScreen("There was a problem writing into the file/closing the file.");
			}


		}
		//brining everything back to null
		mazeToUse = null;
		fileToSave = null;

	}


	@Override
	public void setParams(String[] params) {
		//getting the maze and file
		mazeToUse = m.getMazeByName(params[1]);
		fileToSave = m.getFileFromPath(params[2]);
	}

}



