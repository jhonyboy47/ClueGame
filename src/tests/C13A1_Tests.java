package tests;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
public class C13A1_Tests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 24;
	
	private static Board board;
	
	@Before
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoardSpreadsheet.xlsx", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public static void testLegendFile() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Movie Theater Room", legend.get('M'));
		assertEquals("Ping Pong Room", legend.get('P'));
		assertEquals("Dinning Room", legend.get('D'));
		assertEquals("Game Room", legend.get('G'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Closet", legend.get('X'));
		
	}
	

}
