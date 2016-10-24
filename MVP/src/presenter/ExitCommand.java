package presenter;
import java.io.IOException;

import model.Model;
import view.View;

public class ExitCommand implements Command {

	View v;
	Model m;
	
	public ExitCommand(View v, Model m) {
		this.v = v;
		this.m = m;
	}
	
	@Override
	public void doCommand() {
		m.saveSolutionToFile();
		
	}
	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		
	}

}
