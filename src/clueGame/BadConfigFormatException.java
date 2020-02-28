///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception{
	
	
//	public BadConfigFormatException(int rows, int columns) {
//		super("Incorrect format with " + rows + " rows " + columns + "columns.");
//	}
	public BadConfigFormatException(String error) {
		super(error);
		ErrorLog(error);
	}
	
	public BadConfigFormatException() {
		super("An error occured with configuration files [BAD FORMAT]  of 'Clue' game.");
		
		ErrorLog("An error occured with configuration files [BAD FORMAT]  of 'Clue' game.");
		
		
	}
	
	
	private void ErrorLog(String errorMessage) {
	
		FileWriter myWriter;
		try {
		      myWriter = new FileWriter("errorLog.txt", true);
		      myWriter.append(errorMessage);
		      myWriter.append("\n");
			  myWriter.close();

		    } 
		
		catch (Exception e) {
		      System.out.println("An error occurred while writing to errorLog.txt");
		      e.printStackTrace();
		    }	
	}

}
	
	
