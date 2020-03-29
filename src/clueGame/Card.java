package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}


	public Boolean equals() {
		// TODO remove null later
		return null;
		
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardName == null) ? 0 : cardName.hashCode());
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]" + "\n";
	}
	
	
}
