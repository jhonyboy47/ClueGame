package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private Integer row;
	private Integer column;
	private Color color;
	
	
	
	public Player(String playerName, Integer row, Integer column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}

	public Card disproveSuggestion(Solution suggestion) {
		return null;
		
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", row=" + row + ", column=" + column + ", color=" + color + "]";
	}
	
	
	
}
