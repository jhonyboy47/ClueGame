package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
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
		
		HumanPlayer Bob = new HumanPlayer("Bob", 12, 14, Board.convertColor("blue"));	
		assertTrue(players.contains(Bob));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
