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
	private BoardCell currentCell;
	
	protected ArrayList<Card> myCards = new ArrayList<Card>();
	
	
	public Player() {
		
	}
	public void addMyCards(Card card) {
		myCards.add(card);
	}
	
	// This function is for testing
	public ArrayList<Card> getMyCards() {
		return myCards;
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
		this.color = color;
	}

	public Card disproveSuggestion(Suggestion suggestion, Board board) {
		ArrayList<Card> tempCardList = new ArrayList<Card>();
		Map<Character, String> legend = board.getLegend();
		for(Card card : myCards) {
			String cardName = card.getCardName();
			if(legend.get(suggestion.room).equals(cardName) || cardName.equals(suggestion.person) || cardName.equals(suggestion.weapon)) {
				tempCardList.add(card);
			}
			
		}
		if(tempCardList.size() == 0) {
			return null;
		}
		Collections.shuffle(tempCardList);
		System.out.println(tempCardList.get(0));
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

	public Color getColor() {
		return color;
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
