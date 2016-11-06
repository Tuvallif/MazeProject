package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;
/**
 * This class is the way that my view can work with the user-using CLI
 * It works with the window and the user can see it
 * @author Tuval Lifshitz
 *
 */
public class CLI {
	
	//the in/out
	private BufferedReader in;
	private PrintWriter out;
	//all the commands
	private HashMap<String,Command> myHashMap;
	
	/**
	 * The constructor of CLI, it will initialize the IO objects and the map
	 * @param in the way to let data in
	 * @param out the way to let data out
	 * @param hshMp the map of commands with the Strings that identify them 
	 */
	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> hshMp){
		this.in = in;
		this.out = out;
		this.myHashMap = hshMp;
	}
	
	/**
	 * The method that starts everything-it will work until  user types "exit"
	 * @throws IOException in case of a problem with the trim of the String
	 */
	public void start() throws IOException{
		//the command in String
		String commandString=null;
		//a null command
		Command cmnd; 
		while(!"exit".equals(commandString)){
			//message to user
			out.write("Please enter you command:");
			out.flush();
			//getting the relevant info
			commandString=in.readLine().trim();
			//cutting it
			String[] params = checkIfLegalCommand(commandString);
			//checking the typre of the command
			cmnd = myHashMap.get(params[0]);
			//PROBLEM!
			if(cmnd == null){
				out.write("The string was not found OR could not found a command");
			}
			//if all is good - activates the command in a generic way :)
			else{
				cmnd.setParams(params);
				cmnd.doCommand();
			}
		}
	}
	/**
	 * This method checks if the String is a legal Commad and makes it one in the correct format if it is.
	 * if not will return a message or an error String[]
	 * @param strToChck The String that the user entered as his command
	 * @return a String in the format to be identified for the command to work
	 */
	public String[] checkIfLegalCommand(String strToChck) {
		//getting the relevant input
		strToChck = strToChck.trim();
		//cutting the first word out
		String[] helper = strToChck.split(" ", 2);
		//the String we will return
		String[] toRtrn;
		//checking what command the user wanted to do
		switch(helper[0].toLowerCase()){
		case "dir":
			//it has enough data
			if(helper.length >= 2){
				toRtrn = new String[2];
				toRtrn[0] = "dir";
				toRtrn[1] = helper[1];
			}
			else{
				return returnErrorString("Please enter a valid path next time.");				
			}
			break;
			//generate new maze
		case "generate":
			//it has enough data
			helper = strToChck.split(" ", 7);
			if(helper.length >=7){
				if(!helper[1].toLowerCase().equals("3d") || !helper[2].toLowerCase().equals("maze") ){
					//missing stuff
					toRtrn = returnErrorString("Please enter the command in the format - generate  3d maze <name><height, width, depth>." );
					break;
				}
			}else{
				//also missing stuff
				toRtrn = returnErrorString("Please enter the command AND name in the format generate  3d maze <name><height, width, depth>.");
				break;
			}
			//everything is good
			toRtrn = new String[5];
			toRtrn[0] = "generate 3d maze";
			toRtrn[1] = helper[3];
			toRtrn[2] = helper[4];
			toRtrn[3] = helper[5];
			toRtrn[4] = helper[6];
			break;
			//there are a few options for generate
		case "display":
			helper = strToChck.split(" ", 3);
			//making sure it's display <name>
			if(!helper[1].toLowerCase().toLowerCase().equals("cross") && !helper[1].toLowerCase().equals("solution")){
				try{
					//it is display maze
					helper = strToChck.split(" ", 2);
					toRtrn = new String [2];
					toRtrn[0] = "display";
					toRtrn[1] = helper[1];
				}catch(IndexOutOfBoundsException e){
					//error
					toRtrn = returnErrorString("Please enter all the command in the format - display <name>" );
				}
				break;
			}
			//maybe its by cross?checking....
			else if(helper.length > 1 && helper[1].toLowerCase().equals("cross")){
				//Dividing it into write cells
				helper = strToChck.split(" ");
				//check that everything OK
				if(helper.length >= 8 && helper[2].toLowerCase().equals("section") && helper[3].toLowerCase().equals("by") &&
						helper[6].toLowerCase().equals("for")){
					//making sure that all the data was given
					try{
						//it is by cross!
						toRtrn = new String[4];
						toRtrn[0] = "display cross section by";
						toRtrn[1] = helper[4];
						toRtrn[2] = helper[5];
						toRtrn[3] = helper[7];
					}catch(IndexOutOfBoundsException e){
						toRtrn = returnErrorString("Please enter all the command in the format - display cross section by {X,Y,Z} <index> for <name>" );
					}
				}
				else{
				toRtrn = returnErrorString("Please enter the command in the format - display cross section by {X,Y,Z} <index> for <name>" );	
				}
				break;

			}
			//it is display Solution?let's check...
			else if(helper.length > 1 && helper[1].toLowerCase().equals("solution")){

				try{
					//mayybe it is?
					toRtrn = new String [2];
					toRtrn[0] = "display solution";
					toRtrn[1] = helper[2];
					//if nothing bad happened-it is!
				}catch(IndexOutOfBoundsException e){
					toRtrn = returnErrorString("Please enter all the command in the format - display solution <name>" );
				}
				break;
			}
			//its a nothing command
			else{
				toRtrn = returnErrorString("Could not identigy your displat command-please try again later." );
				break;
			}
			//it saves something-the maze
		case "save":
			helper = strToChck.split(" ", 4);
			try{
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - save maze <name> <file name>" );
				}else{
					//checking if works
					toRtrn = new String[3];
					toRtrn[0] = "save maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
				//error
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - save maze <name> <file name>" );				 
			}
			break;
		case "load":
			helper = strToChck.split(" ", 4);
			try{
				if(!helper[1].toLowerCase().equals("maze")){
					toRtrn = returnErrorString("Please enter all the command in the format - load maze <file name> <name>" );
				}else{
					//checking if works
					toRtrn = new String[3];
					toRtrn[0] = "load maze";
					toRtrn[1] = helper[2];
					toRtrn[2] = helper[3];
				}
			}
			//error
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze and file in the format - load maze <file name> <name>" );				 
			}
			break;
			//it is maze's size
		case "maze":
			helper = strToChck.split(" ", 3);
			try{
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - maze size <name>" );
				}else{
					toRtrn = new String[2];
					toRtrn[0] = "maze size";
					toRtrn[1] = helper[2];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - maze size <name>" );				 
			}
			break;
			//it is file's size
		case "file":
			helper = strToChck.split(" ", 3);
			try{
				if(!helper[1].toLowerCase().equals("size")){
					toRtrn = returnErrorString("Please enter all the command in the format - file size <name>" );
				}else{
					toRtrn = new String[2];
					toRtrn[0] = "file size";
					toRtrn[1] = helper[2];
				}
			}catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the file in the format - file size <name>" );				 
			}
			break;
			//it is solve the maze
		case "solve":
			helper = strToChck.split(" ", 3);
			try{
				//checking if works
				toRtrn = new String[3];
				toRtrn[0] = "solve";
				toRtrn[1] = helper[1];
				toRtrn[2] = helper[2];
				if(!toRtrn[2].toLowerCase().equals("bfs") && !toRtrn[2].toLowerCase().equals("dfs") && !toRtrn[2].toLowerCase().equals("best")){
					toRtrn = returnErrorString("Please enter the name of the algorithem in the format - solve <name> <algorithm>" );
				}
			}
			//error
			catch(IndexOutOfBoundsException e){
				toRtrn = returnErrorString("Please enter the name of the maze in the format - solve <name> <algorithm>" );				 
			}
			break;
			//the user wants out
		case "exit":
			toRtrn = new String[1];
			toRtrn[0] = "exit";
			break;
		default:
			toRtrn = returnErrorString("could not found your command - please try again later." );
		}

		return toRtrn;

	}
	/**
	 * This method returns the Error String in the correct format to be recognized and so the user will know
	 * @param toPrint the mesage of the error String
	 * @return A String[] with error in [0] and toPrint at [1]
	 */
	public String[] returnErrorString(String toPrint) {
		String[] toRtrn = new String[2];
		toRtrn[0] = "error";
		toRtrn[1] = toPrint;
		
		return toRtrn;
	}


}
