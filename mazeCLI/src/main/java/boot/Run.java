package boot;

import controller.Controller;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;

public class Run {
	/**
	 * The main method
	 * initiates the game
	 * @param args to be used in case you want to transfer something to it
	 */
	public static void main(String[] args) {
		//crating the controller to control the whole program
		Controller c = new MyController();
		//creating a new View in type myView
		View v = new MyView(c);
		//creating a new Maze Model
		Model m = new MyModel(c);
		//setting model and view in Controller
		c.setModel(m);
		c.setView(v);
		c.setHashMap();
		//starting the program
		v.start();


	}

}
