package presenter;

import java.io.File;

import model.Model;
import view.View;

public class DirCommand implements Command {
	View v;
	Model m;
	File fileToSend;
	
	public DirCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		if(fileToSend != null && !fileToSend.isDirectory()){
			v.printLineOnScreen("The requested file is not a directory, please try again later with directory.");
		}
		else{
			v.PrintDir(fileToSend);
		}
		fileToSend = null;

	}

	@Override
	public void setParams(String[] params) {
		try{
		fileToSend = m.getFileFromPath(params[1]);
		}catch(NullPointerException npe){
			v.printLineOnScreen("The path was not found, please try again later with valid path.");
			return;
		}
	}
}
