package controller;

public interface Command{
	
	void doCommand();
	
	void setParams(String[] params);

}
