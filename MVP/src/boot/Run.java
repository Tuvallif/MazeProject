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
	/**
	 * The main method
	 * initiates the game
	 * @param args to be used in case you want to transfer something to it
	 */
	public static void main(String[] args){
		
		//creating a new Maze Window
		MazeWindow ui = new MazeWindow();
		//creating a new Maze Model
		GameModel m = new GameModel();
		//setting the presenter with the Model and View according to the design pattern
		Presenter p = new Presenter(ui, m);
		//adding the observers
		ui.addObserver(p);
		m.addObserver(p);
		
		//starting the program
		ui.start();
		
	}
}
