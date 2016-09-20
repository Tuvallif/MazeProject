package presenter;

import view.View;

public class ErrorCommand implements Command {

	View v;
	String toPrint;
	
	public ErrorCommand(View v){
		this.v= v;
	}
	
	@Override
	public void doCommand() {
		try{
		v.printLineOnScreen(toPrint);
		}catch(NullPointerException npe){
			v.printLineOnScreen("NO MESSAGE FOUND");
		}
		toPrint = null;
	}

	@Override
	public void setParams(String[] params) {
		toPrint = params[1];		
	}

}
