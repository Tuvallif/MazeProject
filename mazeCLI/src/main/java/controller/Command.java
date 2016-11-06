package controller;
/**
 * This interface allows every implementation to do the command according to the way the choose to do it
 * Also allows to add other commands in the future
 * @author Tuval Lifshitz
 *
 */
public interface Command{
	/**
	 * This is the method that does the command and activates whatever needs to be made in order to do everything
	 */	
	void doCommand();
	/**
	 * This method gives all the required data to the class before activating doCommand, that way do command can stay generic as possible
	 * @param params a string[] that contains all the information needed for each command(according to specific one needs)
	 * params[0] is the type of the command
	 */
	void setParams(String[] params);

}
