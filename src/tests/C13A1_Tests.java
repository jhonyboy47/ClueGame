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
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoardSpreadsheet.xlsx", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	// Checks that that we read in the legend file correctly
	@Test
	public void testLegendFile() {
		Map<Character, String> legend = board.getLegend();
		// Checks if we have imported all legends
		assertEquals(LEGEND_SIZE, legend.size());
		// Checks that the initials are correct for a few legends
		assertEquals("Movie Theater Room", legend.get('M'));
		assertEquals("Ping Pong Room", legend.get('P'));
		assertEquals("Dinning Room", legend.get('D'));
		assertEquals("Game Room", legend.get('G'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Closet", legend.get('X'));
		
	}
	
	
	//Makes sure we have the correct number of columns and rows that was read in from the csv file
	@Test
	public void checkColAndRows() {
		
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	//Verify at least one doorway in each direction. Also verify cells that don't contain doorways return false for isDoorway.
	@Test
	public void testDoors() {
		// Tests down direction
		BoardCell block = board.getCellAt(5, 15);
		assertTrue(block.isDoorway());
		assertEquals(DoorDirection.DOWN, block.getDoorDirection());
		
		// Tests left direction
		block = board.getCellAt(12, 19);
		assertTrue(block.isDoorway());
		assertEquals(DoorDirection.LEFT, block.getDoorDirection());
		
		// Tests right direction
		block = board.getCellAt(20, 5);
		assertTrue(block.isDoorway());
		assertEquals(DoorDirection.RIGHT, block.getDoorDirection());
		
		// Tests up direction
		block = board.getCellAt(18, 17);
		assertTrue(block.isDoorway());
		assertEquals(DoorDirection.UP, block.getDoorDirection());
		
		//Two locations that should not be a door
		block = board.getCellAt(15, 15);
		assertTrue(!block.isDoorway());
		
		block = board.getCellAt(0, 0);
		assertTrue(!block.isDoorway());
	}
	
	@Test
	public void testNumberDoors() {
		// Have a double while loop to check if the total number of doors is correct
		int Doors = 0;
		int row=0;
		while( row<board.getNumRows()) {
			int col=0;
			while (col<board.getNumColumns()) {
				BoardCell block = board.getCellAt(row, col);
				if (block.isDoorway()) Doors++;
				
				col++;
			}
			row++;
		}
		Assert.assertEquals(13, Doors);
	}
	
	// Test to see if we the cells we read in have the correct initials
	@Test 
	public void testCellInitials() {
		// Tests various cells to see if there initials are correct
		assertEquals(board.getCellAt(0, 0).getInitial(), 'P');
		assertEquals(board.getCellAt(15, 15).getInitial(), 'W');
		assertEquals(board.getCellAt(16, 23).getInitial(), 'W');
		assertEquals(board.getCellAt(12, 22).getInitial(), 'H');
		assertEquals(board.getCellAt(4, 2).getInitial(), 'P');
		assertEquals(board.getCellAt(22, 0).getInitial(), 'D');
		assertEquals(board.getCellAt(10, 11).getInitial(), 'X');
		
		// Test if there are the correct number of J, M, and C cells in the board
		int jCells = 0;
		int mCells = 0;
		int cCells = 0;
		int row=0;
		while( row<board.getNumRows()) {
			int col=0;
			while (col<board.getNumColumns()) {
				BoardCell block = board.getCellAt(row, col);
				if (board.getCellAt(row, col).getInitial() == 'J') jCells++;
				if (board.getCellAt(row, col).getInitial() == 'M') mCells++;
				if (board.getCellAt(row, col).getInitial() == 'C') cCells++;
				col++;
			}
			row++;
		}
		Assert.assertEquals(26, jCells);
		Assert.assertEquals(24, cCells);
		Assert.assertEquals(30, jCells);
		
	}

}
