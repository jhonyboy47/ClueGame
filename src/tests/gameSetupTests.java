package tests;

import static org.junit.Assert.assertEquals;
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
	
	@BeforeClass
	public static void beforeAll() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.txt");	
		board.setPlayersFile("ClueGamePlayers.csv");
		board.setWeaponsFile("ClueGameWeapons.txt");
		board.initialize();
		
		//Room cards for testins
		PingPongRoom = new Card("Ping Pong Room", CardType.ROOM);
		JesusRoom = new Card("Jesus Room", CardType.ROOM);
		Library = new Card("Library", CardType.ROOM);
		
		BobCard = new Card("Bob", CardType.PERSON);
		SharkishaCard = new Card("Sharkisha", CardType.PERSON);

		Pillow = new Card("Pillow", CardType.WEAPON);
		Car = new Card("Car", CardType.WEAPON);
	}
	

	
	@Test
	public void LoadPeopleTest() {
		Set<Player> players = board.getPlayersSet();
		
		Player Bob = new HumanPlayer("Bob", 12, 14, Board.convertColor("blue"));	
		Player Jared = new ComputerPlayer("Jared", 15, 21, Board.convertColor("red"));	
		Player Shark = new HumanPlayer("Sharkisha", 27, 30, Board.convertColor("purple"));	

		assertTrue(players.contains(Bob));
		assertTrue(players.contains(Jared));
		assertTrue(players.contains(Shark));
		assertEquals(3, players.size());
		
	}
	
	@Test
	public void CreateCardsTest() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(16, deck.size());
		assertTrue(deck.contains(PingPongRoom));
		assertTrue(deck.contains(JesusRoom));
		assertTrue(deck.contains(Library));
		assertTrue(deck.contains(BobCard));
		assertTrue(deck.contains(SharkishaCard));
		assertTrue(deck.contains(Pillow));
		assertTrue(deck.contains(Car));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
