package clueGame;
///Authors: Jhonathan Malagon and Michael Crews
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player {
	private String playerName;
	private Integer row;
	private Integer column;
	private Color color;
	private String colorStyle;
	private Integer displayRow;
	private Integer displayColumn;
	
	
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	
	
	public Player() {
		
	}
	public void addMyCards(Card card) {
		myCards.add(card);
	}
	
	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	
	public ArrayList<String> getMyCardsAsStrings(){
		ArrayList<String> tempArrayList = new ArrayList<String>();
		
		for(Card card : myCards) {
			tempArrayList.add(card.getCardName());
		}
		
		return tempArrayList;
		
	}
	
	public void changeMyCards(Card card) {
		myCards.clear();
		myCards.add(card);
	}
	
	public Player(String playerName, BoardCell startingCell, Color color) {
		super();
		this.playerName = playerName;
		this.row = startingCell.getRow();
		this.column = startingCell.getColumn();
		this.displayRow = startingCell.getRow();
		this.displayColumn = startingCell.getColumn();
		this.color = color;
		
	}
	
	public void setStringColor(String colorName) {
		this.colorStyle = "-fx-fill: " + colorName + "; -fx-stroke: black; -fx-stroke-width: 2;";
	}
	
	public String getColorStyle() {
		return colorStyle;
	}
	
	
	

	
	public Card disproveSuggestion(Suggestion suggestion, Board board) {
		ArrayList<Card> tempCardList = new ArrayList<Card>();
		
		//Legend is used to convert back and forth from a character 
		//that maps to a room name 
		Map<Character, String> legend = board.getLegend();
		
		//Here player checks their cards with the suggestion made and a list
		//Is populated based off of what a player may show to disprove suggestion
		for(Card card : myCards) {
			String cardName = card.getCardName();
			
			if(legend.get(suggestion.room).equals(cardName) || cardName.equals(suggestion.person) || cardName.equals(suggestion.weapon)) {
				tempCardList.add(card);
			}
			
		}
		
		//If player can't disprove we return null
		if(tempCardList.size() == 0) {
			return null;
		}
		
		//picks a random card from "disprovable" list
		Collections.shuffle(tempCardList);
		return tempCardList.get(0);
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
	
	public int getDisplayRow() {
		return displayRow;
	}

	public int getDisplayColumn() {
		return displayColumn;
	}
	
	public void setNewDisplayLocation(Integer row, Integer col) {
		this.displayRow = row;
		this.displayColumn = col;
	}

	public Color getColor() {
		return color;
	}
	
	public void setNewLocation(Integer row, Integer col) {
		this.row = row;
		this.column = col;
		this.displayRow = row;
		this.displayColumn = col;
	}
	
	
	// This function is needed for testing
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		return result;
	}
	
	// This function is needed for testing
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}
	
	
	// This function is helpful for testing
	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", row=" + row + ", column=" + column + ", color=" + color + "] " + myCards + "\n";
	}
	
	
	
}
