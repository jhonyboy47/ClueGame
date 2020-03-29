package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;


public class gameSetupTests {

	private static Board board;
	
	@Before
	public void beforeAll() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.txt");	
		board.setPlayersFile("ClueGamePlayers.txt");
		board.initialize();
	}
	

	
	@Test
	public void Test() {
		Set<Player> players = board.getPlayersSet();
		
		Player Bob = new HumanPlayer("Bob", 12, 14, Board.convertColor("blue"));	
		Player Jared = new ComputerPlayer("Jared", 15, 21, Board.convertColor("red"));	
		Player Shark = new HumanPlayer("Sharkisha", 27, 30, Board.convertColor("purple"));	

		assertTrue(players.contains(Bob));
		assertTrue(players.contains(Jared));
		assertTrue(players.contains(Shark));
		assertEquals(3, players.size());
		

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
