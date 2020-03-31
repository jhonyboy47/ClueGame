// Authors: Michael Crews and Jhon Malagon
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;


public class gameSetupTests {

	private static Board board;
	static Card PingPongRoom, JesusRoom, Library, BobCard, SharkishaCard, Pillow, Car;
	static Player Bob, Jared, Shark;
	
	@BeforeClass
	public static void beforeAll() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		
		// Set all the needed config files
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.csv");	
		board.setPlayersFile("ClueGamePlayers.csv");
		board.setWeaponsFile("ClueGameWeapons.csv");
		board.initialize();
		
		// Players for testing 
		Bob = new HumanPlayer("Bob", 12, 14, Board.convertColor("blue"));	
		Jared = new ComputerPlayer("Jared", 15, 21, Board.convertColor("red"));	
		Shark = new HumanPlayer("Sharkisha", 27, 30, Board.convertColor("yellow"));	
		
		//Room cards for testins
		PingPongRoom = new Card("Ping Pong Room", CardType.ROOM);
		JesusRoom = new Card("Jesus Room", CardType.ROOM);
		Library = new Card("Library", CardType.ROOM);
		
		//People cards for testing
		BobCard = new Card("Bob", CardType.PERSON);
		SharkishaCard = new Card("Sharkisha", CardType.PERSON);
		
		//Weapon cards for testing
		Pillow = new Card("Pillow", CardType.WEAPON);
		Car = new Card("Car", CardType.WEAPON);
	}
	

	
	@Test
	public void LoadPeopleTest() {
		// player set for testing
		ArrayList<Player> players = board.getPlayersSet();
		
		// Test if the players set has all players from config file
		assertTrue(players.contains(Bob));
		assertTrue(players.contains(Jared));
		assertTrue(players.contains(Shark));
		
		// Test if the players set is the correct size
		assertEquals(3, players.size());
		
	}
	
	@Test
	public void CreateCardsTest() {
		// Get the deck for testing
		ArrayList<Card> deck = board.getDeck();
		
		// Test if the deck size is the correct size
		assertEquals(16, deck.size());
		
		//Variables for counting right amount of each type of card are generated
		int personCount = 0, weaponCount = 0 , roomCount = 0;
		for (Card card: deck) {
			
			
			if (card.getCardType() == CardType.PERSON) {
				personCount++;
			}
			
			else if (card.getCardType() == CardType.WEAPON) {
				weaponCount++;
			}
			
			else if (card.getCardType() == CardType.ROOM) {
				roomCount++;
			}
		}
		
		assertEquals(3, personCount);
		assertEquals(4, weaponCount);
		assertEquals(9, roomCount);
		
		
		// Test if the deck contains some of the correct cards
		assertTrue(deck.contains(PingPongRoom));
		assertTrue(deck.contains(JesusRoom));
		assertTrue(deck.contains(Library));
		assertTrue(deck.contains(BobCard));
		assertTrue(deck.contains(SharkishaCard));
		assertTrue(deck.contains(Pillow));
		assertTrue(deck.contains(Car));
		
		
	}
	
	@Test
	public void DealCardsTest() {
		// player array list for testing
		ArrayList<Player> players = board.getPlayersSet();
		
		// Counter to count how many cards have been dealt to players
		int overallCardCount = 0;
		
		int cardCountForFirstPlayer = 0;
		
		// Variable to hold a the first card from the array list of players
		Card firstCard = new Card();
		
		// Counter for how many times the first card is delt to players
		int firstCardCount = 0;
		
		// Boolean to hold a true or false value if the first pass has happened in the for loop below
		Boolean first = false;
		
		for(Player player : players) {
			ArrayList<Card> playerCards = player.getMyCards();
			
			// Set the first player's card count and first card
			if(first == false) {
				cardCountForFirstPlayer = playerCards.size();
				firstCard = playerCards.get(0);
				first = true;
			} 
			
			// If any of the playerCards are the first card, increment the firstCardCounter by 1 
			for(Card card : playerCards) {
				if(card.equals(firstCard)) {
					firstCardCount++;
				}
			}
			
			// The first card should only be delt once
			assertEquals(1, firstCardCount);
			
			overallCardCount = overallCardCount + playerCards.size();
			
			// Test if a player's card count is within 1 of the first player's card count 
			assertEquals(cardCountForFirstPlayer, playerCards.size(), 1);
		}
		
		// There should be 16 cards delt to players
		assertEquals(16, overallCardCount);
		
	}
}
