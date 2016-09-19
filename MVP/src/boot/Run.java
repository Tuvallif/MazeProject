package boot;

import model.GameModel;
import presenter.Presenter;
import view.CLIGameView;

public class Run {
	
	public static void main(String[] args){
		
		CLIGameView ui = new CLIGameView();
		GameModel m = new GameModel();
		Presenter p = new Presenter(ui, m);
		ui.addObserver(p);
		m.addObserver(p);
		
		ui.start();
		
	}
}
