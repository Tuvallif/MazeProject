package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



import model.Model;
import view.View;
/**
 * This class is to load an old maze from the file
 * @author Tuval Lifshitz
 *
 */
public class LoadCommand implements Command {

	View v;
	Model m;
	File myFileToOpen;
	String name;
	
	/**
	 * The constructor just initialize the parameters
	 * @param v view as the user chooses to use
	 * @param m Model as the user chooses to use
	 */
	public LoadCommand(View v, Model m) {
		this.v = v;
		this.m = m;
		
	}
	@Override
	public void doCommand() {
		//creating a new byteArray
		byte[] mazeAsByte = new byte[1000];
		//if the file is not null
		if(myFileToOpen != null){
			try{
				//creating a file stream with the file
				FileInputStream fis = new FileInputStream(myFileToOpen);
				//reading the maze byteArray 
				fis.read(mazeAsByte);
				fis.close();
			}
			//if there was a problem with finding the file
			catch(FileNotFoundException fnfe){
				v.printLineOnScreen("The file does not exist/ is a directory rather than a regular file, \n"
						+ "or for some other reason cannot be opened for reading.");
			}
			//problem with the  reading from the file
			catch(IOException ioe){
				v.printLineOnScreen("There was a problem reading from the file/closing the file.");
			}
			//generating a new maze
			m.generateMaze(name, mazeAsByte);
		}
		//bringing everything back to null
		myFileToOpen = null;
		name  = null;
			
	}
	@Override
	public void setParams(String[] params) {
		//getting the file
		myFileToOpen = m.getFileFromPath(params[1]);
		//getting the maze
		name = params[2];
		
	}

}