package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import algorithms.maze.Maze3d;
import model.Model;
import view.View;

public class SaveCommand implements Command{

	View v;
	Model m;
	
	public SaveCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}


	@Override
	public void doCommand(String[] params) {
		Maze3d mazeToUse = m.getMazeByName(params[1]);
		if(mazeToUse == null){
			v.printLineOnScreen("The requested maze was not found, please try again later with a correct name.");
		}else{
			File fileToSave = m.getFileFromPath(params[2]);
			try{
			FileOutputStream fos = new FileOutputStream(fileToSave);
			fos.write(mazeToUse.toByteArray());
			fos.close();
			}
			catch(FileNotFoundException fnfe){
				v.printLineOnScreen("The file was not found, please type a correct name next time.");
			} 
			catch (IOException ioe) {
				v.printLineOnScreen("There was a problem writing into the file/closing the file.");
			}


		}

	}

}


