package javaFX;

import java.util.ArrayList;

import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MyCards {
	
	public static VBox DisplayMyCards(Player player) {
		ArrayList<Card> cards = player.getMyCards();
		System.out.println(cards);
		VBox myCardsVbox = new VBox();
		
		ArrayList<Card> peopleCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		for(Card card : cards) {
			if(card.getCardType() == CardType.PERSON) {
				peopleCards.add(card);
			} else if (card.getCardType() == CardType.WEAPON) {
				weaponCards.add(card);
			} else if (card.getCardType() == CardType.ROOM) {
				roomCards.add(card);
			} 
		}
		
		if(!peopleCards.isEmpty()) {
			VBox peopleVbox = DisplayCardsOfSameType(peopleCards, "People");
			myCardsVbox.getChildren().add(peopleVbox);
		} 
		if(!roomCards.isEmpty()) {
			VBox roomVbox = DisplayCardsOfSameType(roomCards, "Rooms");
			myCardsVbox.getChildren().add(roomVbox);
		}
		if(!weaponCards.isEmpty()) {
			VBox weaponVbox = DisplayCardsOfSameType(weaponCards, "Weapons");
			myCardsVbox.getChildren().add(weaponVbox);
		}
		
		
		// myCardsVbox.getChildren().addAll(peopleVbox, roomVbox);
		return myCardsVbox;
		
	}
	
	private static VBox DisplayCardsOfSameType(ArrayList<Card> cards, String type) {
		VBox vbox = new VBox();
		
		Label typeNameLabel = new Label(type);
		
		Pane pane = new Pane();
		// pane.setMinSize(30, 50);
		
		System.out.println(cards.get(0).getCardName());
		Label cardName = new Label(cards.get(0).getCardName());
		// cardName.setMinSize(300, 500);
		cardName.setAlignment(Pos.CENTER_LEFT);
		pane.getChildren().add(cardName);
		
		vbox.getChildren().add(pane);
		return vbox;
		
		
	}
}
