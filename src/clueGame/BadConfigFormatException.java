///Authors: Jhonathan Malagon and Michael Crews
// We did the extra credit that outputs errors to an errorLog
package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception{
	// Default constructor of BadConfigFormatException
	public BadConfigFormatException() {
		super("An error occured with configuration files [BAD FORMAT]  of 'Clue' game.");
		ErrorLog("An error occured with configuration files [BAD FORMAT]  of 'Clue' game.");
	}
	
	// Parameterized constructor of BadConfigFormatException
	public BadConfigFormatException(String error) {
		super(error);
		ErrorLog(error);
	}
	
	
	// Method for writing errors to a file called errorLog.txt
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
	
	
