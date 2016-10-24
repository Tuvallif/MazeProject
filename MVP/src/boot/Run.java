package boot;

import java.io.File;
import java.io.IOException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import model.GameModel;
import presenter.Presenter;
import presenter.Properties;
import view.CLIGameView;
import view.MazeWindow;

public class Run {
	
	public static void main(String[] args){
		
		//CLIGameView ui = new CLIGameView();
		MazeWindow ui = new MazeWindow();
		GameModel m = new GameModel();
		Presenter p = new Presenter(ui, m);
		ui.addObserver(p);
		m.addObserver(p);
		
		ui.start();
		
	}
}
