package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



import model.Model;
import view.View;

public class LoadCommand implements Command {

	View v;
	Model m;
	
	public LoadCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	@Override
	public void doCommand(String[] params) {
		byte [] mazeAsByte = new byte[1000];
		File myFileToOpen = m.getFileFromPath(params[1]);
		if(myFileToOpen != null){
			try{
				FileInputStream fis = new FileInputStream(myFileToOpen);
				fis.read(mazeAsByte);
				fis.close();
			}catch(FileNotFoundException fnfe){
				v.printLineOnScreen("The file does not exist/ is a directory rather than a regular file, \n"
						+ "or for some other reason cannot be opened for reading.");
			}
			catch(IOException ioe){
				v.printLineOnScreen("There was a problem reading from the file/closing the file.");
			}
			m.generateMaze(params[2], mazeAsByte);
		}
			
	}

}
