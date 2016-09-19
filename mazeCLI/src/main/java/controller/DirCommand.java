package controller;

import java.io.File;

import model.Model;
import view.View;

public class DirCommand implements Command {
	View v;
	Model m;
	
	public DirCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand(String[] params) {
		File fileToSend;
		try{
		fileToSend = m.getFileFromPath(params[1]);
		}catch(NullPointerException npe){
			v.printLineOnScreen("The path was not found, please try again later with valid path.");
			return;
		}
		if(!fileToSend.isDirectory()){
			v.printLineOnScreen("The requested file is not a directory, please try again later with directory.");
		}
		else{
			v.PrintDir(fileToSend);
		}

	}
}
