// Authors: Michael Crews and Jhonathan Malagon
// We did the extra credit that outputs errors to an errorLog
package clueGame;

public class BoardCell {
	// Declare two instance variables that every boardcell has
	int row, column;
	char initial = ' ';
	boolean walkway = false, room = false, doorWay = false;
	DoorDirection direction = DoorDirection.NONE;
	// This is a parameterized constructor for boardcell to pass in the row and column
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	

	public void setDoorWay(boolean doorWay) {
		this.doorWay = doorWay;
	}

	public void setWalkway(boolean walkway) {
		this.walkway = walkway;
	}

	public void setRoom(boolean room) {
		this.room = room;
	}
	public boolean isWalkway() {
		return walkway;
	}
	
	public boolean isRoom() {
		return room;
	}
	public boolean isDoorway() {
		
		return doorWay;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", walkway=" + walkway
				+ ", room=" + room + ", doorWay=" + doorWay + ", direction=" + direction + "]";
	}


	public DoorDirection getDoorDirection() {
		
		return direction;
	}

	public void setDirection(DoorDirection direction) {
		this.direction = direction;
	}


	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public char getInitial() {
		
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	
	
	
}
