///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException(int rows, int columns) {
		super("Incorrect format with " + rows + " rows " + columns + "columns.");
	}
}
