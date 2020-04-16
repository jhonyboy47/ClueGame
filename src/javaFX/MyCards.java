package javaFX;

import java.util.ArrayList;

import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MyCards {
	public static double largestCardWidth = 0;
	
	public static VBox DisplayMyCards(Player player) {
		ArrayList<Card> cards = player.getMyCards();
		
	
		VBox myCardsVbox = new VBox();
		
		// Set an a border around all the player's cards
		myCardsVbox.setStyle("-fx-border-color: gray");
		myCardsVbox.setSpacing(20);
		
		// Align all the cards and labels nicely
		myCardsVbox.setAlignment(Pos.TOP_CENTER);
		
		// Label to say my cards 
		Label myCardsLabel = new Label("My Cards");
		
		// Set padding so the label doesn't look weird
		myCardsLabel.setPadding(new Insets(20,20,0,20));
		myCardsLabel.setFont(new Font(30));
		
		// First addition to myCardsVbox
		myCardsVbox.getChildren().add(myCardsLabel);
		
		// ArrayList to holds all types of player cards
		ArrayList<Card> peopleCards = new ArrayList<Card>();
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		
		// Sort the player's cards by type
		for(Card card : cards) {
			if(card.getCardType() == CardType.PERSON) {
				peopleCards.add(card);
			} else if (card.getCardType() == CardType.WEAPON) {
				weaponCards.add(card);
			} else if (card.getCardType() == CardType.ROOM) {
				roomCards.add(card);
			} 
		}
		
		// Three if statements to check what the widest card is
		if(!peopleCards.isEmpty()) {
			VBox peopleVbox = DisplayCardsOfSameType(peopleCards, "People",false);
		} 
		if(!roomCards.isEmpty()) {
			VBox roomVbox = DisplayCardsOfSameType(roomCards, "Rooms",false);
		}
		
		
		if(!weaponCards.isEmpty()) {
			VBox weaponVbox = DisplayCardsOfSameType(weaponCards, "Weapons",false);
		}
		
		
		// Set the main vbox to the largest width plus some extra margin so it looks nice
		myCardsVbox.setMinWidth(largestCardWidth + 20);
		
		// Add the cards to myCardsVbox
		if(!peopleCards.isEmpty()) {
			VBox peopleVbox = DisplayCardsOfSameType(peopleCards, "People",true);
			myCardsVbox.getChildren().add(peopleVbox);
		} 
		if(!roomCards.isEmpty()) {
			VBox roomVbox = DisplayCardsOfSameType(roomCards, "Rooms",true);
			myCardsVbox.getChildren().add(roomVbox);
		}
		if(!weaponCards.isEmpty()) {
			VBox weaponVbox = DisplayCardsOfSameType(weaponCards, "Weapons",true);
			myCardsVbox.getChildren().add(weaponVbox);
		}
		
		return myCardsVbox;
		
	}
	
	private static VBox DisplayCardsOfSameType(ArrayList<Card> cards, String type, Boolean knowLargestWidth) {
		// Main vbox for a given type of card
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		
		// Add a label to denote which type the vbox is
		Label typeNameLabel = new Label(type);
		typeNameLabel.setFont(new Font(20));
	
		
		// Add label to vbox
		vbox.getChildren().add(typeNameLabel);
		
		// For loop to add cards to vbox
		for(Card card : cards) {
			// Label for each card
			Label cardName = new Label(card.getCardName());
			cardName.setFont(new Font(20));

			// Set the size of the cardName accordingly if we already know the largestCardWidth
			if(knowLargestWidth) {
				cardName.setMinSize(largestCardWidth, 120);
			}else {
				cardName.setMinSize(60, 120);
			}
			
			cardName.setPadding(new Insets(10));
			cardName.setAlignment(Pos.CENTER);
			cardName.setStyle("-fx-background-color: lightgrey");
			
			// Add card to vbox
			vbox.getChildren().add(cardName);
			
			// Calculate the width of the card and set it to largesCardWidth if its larger than largestCardWidth
			if(!knowLargestWidth) {
				
				// Make a test vbox and test scene to avoid generating errors later on
				VBox tempVbox = new VBox(vbox);
				Scene testScene = new Scene(tempVbox, 1000, 1000);
				
				// Have the testVbox virtually layout itself so we know the cards width
				tempVbox.applyCss();
				tempVbox.layout();
				
				// Set the cardName.getWidth() to largesCardWidth if its larger than largestCardWidth
				if(cardName.getWidth() > largestCardWidth) {
					largestCardWidth = cardName.getWidth();
				}
			} 
			
		}
	
		
		return vbox;
		
	}
}
