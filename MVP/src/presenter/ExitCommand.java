package presenter;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setParams(String[] params) {
		// TODO Auto-generated method stub
		
	}

}
