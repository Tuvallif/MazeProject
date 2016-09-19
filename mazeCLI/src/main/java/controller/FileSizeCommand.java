package controller;

import java.io.File;

import model.Model;
import view.View;

public class FileSizeCommand implements Command {

	Model m;
	View v;
	
	public FileSizeCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	@Override
	public void doCommand(String[] params) {
		File myFile = m.getFileFromPath(params[1]);
		if(myFile != null){
			v.printLineOnScreen(Long.toString(myFile.length()));
		}
		else{
			v.printLineOnScreen("There was a problem with opnening the file or reading the data.");
		}
	}

}
