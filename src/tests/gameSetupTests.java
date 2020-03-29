package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import jdk.internal.net.http.frame.PingFrame;


public class gameSetupTests {

	private static Board board;
	Card PingPongRoom, JesusRoom, Library, Bob, Sharkisha, Pillow, Car;
	
	@Before
	public void beforeAll() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.txt");	
		board.setPlayersFile("ClueGamePlayers.txt");
		board.setWeaponsFile("ClueGameWeapons.txt");
		board.initialize();
		
		//Room cards for testins
		Card PingPongRoom = new Card("Ping Pong Room", CardType.ROOM);
		Card JesusRoom = new Card("Jesus Room", CardType.ROOM);
		Card Library = new Card("Library", CardType.ROOM);
		
		Card Bob = new Card("Bob", CardType.PERSON);
		Card Sharkisha = new Card("Sharkisha", CardType.PERSON);

		Card Pillow = new Card("Pillow", CardType.WEAPON);
		Card Car = new Card("Car", CardType.WEAPON);
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
		System.out.println(deck);

		assertTrue(deck.contains(PingPongRoom));
		assertTrue(deck.contains(JesusRoom));
		assertTrue(deck.contains(Library));
		assertTrue(deck.contains(Bob));
		assertTrue(deck.contains(Sharkisha));
		assertTrue(deck.contains(Pillow));
		assertTrue(deck.contains(Car));


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
