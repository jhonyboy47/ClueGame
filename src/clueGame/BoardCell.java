// Authors: Michael Crews and Jhonathan Malagon
package clueGame;

public class BoardCell {
	// Declare two instance variables that every boardcell has
	int row, column;
	
	// This is a parameterized constructor for boardcell to pass in the row and column
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
