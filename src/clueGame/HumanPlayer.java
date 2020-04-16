///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{
	private Boolean justMoved = false;
	
	public Boolean getJustMoved() {
		return justMoved;
	}
	
	public void setJustMoved(Boolean justMoved) {
		this.justMoved = justMoved;
	}
	
	public HumanPlayer() {
		
	}
	public HumanPlayer(String playerName, BoardCell startingCell, Color color) {
		super(playerName,startingCell, color);
	}

}
