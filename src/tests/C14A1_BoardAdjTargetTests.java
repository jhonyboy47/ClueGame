package tests;

import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class C14A1_BoardAdjTargetTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	
	// Test locations with only walkways as adjacent locations
	// These cells are yellow in the spreadsheet
	@Test
	public void testAdjacencyWalkwayFourSides() {
		Set<BoardCell> adjList = board.getAdjList(16, 6);
		
		// Test that the size of adjacency list is 4
		assertEquals(4,adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(15,6)));
		assertTrue(adjList.contains(board.getCellAt(17,6)));
		
		assertTrue(adjList.contains(board.getCellAt(16,7)));
		assertTrue(adjList.contains(board.getCellAt(16,4)));
		
		
		
		adjList = board.getAdjList(9, 17);
		
		assertEquals(4,adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(8,17)));
		assertTrue(adjList.contains(board.getCellAt(10,17)));
		
		assertTrue(adjList.contains(board.getCellAt(16,9)));
		assertTrue(adjList.contains(board.getCellAt(18,9)));
		
		
	}
	
	
	// Test Locations within rooms (should have empty adjacency list)
	// These test cells are purple in the spreadsheet
	@Test
	public void testAdjacencyCellsInRooms() {
		Set<BoardCell> adjList = board.getAdjList(0, 0);
		assertEquals(0, adjList.size());
		
		adjList = board.getAdjList(17, 12);
		assertEquals(0, adjList.size());
		
		adjList = board.getAdjList(18, 20);
		assertEquals(0, adjList.size());
		
		adjList = board.getAdjList(14, 19);
		assertEquals(0, adjList.size());
	}
	
	// Test Locations that are at each edge of the board
	// These test cells are pink in the spreadsheet
	@Test
	public void testAdjacencyEdgeCells() {
		Set<BoardCell> adjList = board.getAdjList(0, 6);
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(1,6)));
		
		adjList = board.getAdjList(22, 7);
		assertEquals(3, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(22,6)));
		assertTrue(adjList.contains(board.getCellAt(22,8)));
		assertTrue(adjList.contains(board.getCellAt(21,7)));
		
		adjList = board.getAdjList(0, 17);
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(0,16)));
		assertTrue(adjList.contains(board.getCellAt(1,17)));
		
		adjList = board.getAdjList(7, 23);
		assertEquals(3, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(6,23)));
		assertTrue(adjList.contains(board.getCellAt(8,23)));
		assertTrue(adjList.contains(board.getCellAt(7,22)));
		
	}
	
	// Test Locations that are beside a room cell that is not a doorway
	// These test cells are red in the spreadsheet
	@Test
	public void testAdjacenyCellsBesideRooms() {
		Set<BoardCell> adjList = board.getAdjList(7, 0);
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(7,1)));
		assertTrue(adjList.contains(board.getCellAt(8,0)));
		
		adjList = board.getAdjList(3, 13);
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(2,13)));
		assertTrue(adjList.contains(board.getCellAt(3,12)));
		
	}
	
	// Test Locations that are adjacent to a doorway with needed direction
	// These test cells are light pink in the spreadsheet
	@Test
	public void testAdjacenyCellsAdjancentToDoorway() {
		Set<BoardCell> adjList = board.getAdjList(15, 4);
		assertEquals(4, adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(14,4)));
		assertTrue(adjList.contains(board.getCellAt(15,3)));
		assertTrue(adjList.contains(board.getCellAt(16,4)));
		assertTrue(adjList.contains(board.getCellAt(15,5)));
		
		adjList = board.getAdjList(7, 3);
		assertEquals(4, adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(6,3)));
		assertTrue(adjList.contains(board.getCellAt(8,3)));
		assertTrue(adjList.contains(board.getCellAt(7,2)));
		assertTrue(adjList.contains(board.getCellAt(7,4)));
		
		
		
		adjList = board.getAdjList(1, 6);
		assertEquals(3, adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(0,6)));
		assertTrue(adjList.contains(board.getCellAt(2,6)));
		assertTrue(adjList.contains(board.getCellAt(1,7)));
		
		
		
		adjList = board.getAdjList(17, 17);
		assertEquals(4, adjList.size());
		
		assertTrue(adjList.contains(board.getCellAt(18,17)));
		assertTrue(adjList.contains(board.getCellAt(16,17)));
		assertTrue(adjList.contains(board.getCellAt(17,18)));
		assertTrue(adjList.contains(board.getCellAt(17,16)));
		
	}
	
	// Test Locations that are doorways (should have only one adjacent cell)
	// These test cells are bright green in the spreadsheet
	@Test
	public void testAdjacenyCellsThatAreDoorways() {
		Set<BoardCell> adjList = board.getAdjList(19, 12);
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(19,13)));
		
		adjList = board.getAdjList(21, 11);
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(21,10)));
		
		adjList = board.getAdjList(5, 18);
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(6,18)));
	}
	
	
	
	
	
	
	
}
