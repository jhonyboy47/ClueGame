package tests;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
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
		
	}
	
	
	
}
