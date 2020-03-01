
///Authors: Jhonathan Malagon and Michael Crews
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
		
		//Top left corner in ping pong room
		Set<BoardCell> adjList = board.getAdjList(0, 0);
		assertEquals(0, adjList.size());
		
		
		//Jesus room 
		adjList = board.getAdjList(17, 12);
		assertEquals(0, adjList.size());
		
		//Car room 
		adjList = board.getAdjList(18, 20);
		assertEquals(0, adjList.size());
		
		//Hot tub room 
		adjList = board.getAdjList(14, 19);
		assertEquals(0, adjList.size());
	}
	
	// Test Locations that are at each edge of the board
	// These test cells are pink in the spreadsheet
	@Test
	public void testAdjacencyEdgeCells() {
		//Top edge
		Set<BoardCell> adjList = board.getAdjList(0, 6);
		assertEquals(1, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(1,6)));
		
		//bottom edge 
		adjList = board.getAdjList(22, 7);
		assertEquals(3, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(22,6)));
		assertTrue(adjList.contains(board.getCellAt(22,8)));
		assertTrue(adjList.contains(board.getCellAt(21,7)));
		
		//left edge
		adjList = board.getAdjList(17, 0);
		assertEquals(2, adjList.size());
		assertTrue(adjList.contains(board.getCellAt(0,16)));
		assertTrue(adjList.contains(board.getCellAt(1,17)));
		
		//Right edghe
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
	
	// Test Locations that are adjacent to a doorway with needed direction to enter
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
	
	
	// Targets along walkways, various steps away
	// These test cells are grey in the spreadsheet
	@Test
	public void testTargetsVariousSteps() {
		
		//Calculates for one step away
		board.calcTargets(19,15,1);
		Set<BoardCell> targetList = board.getTargets();
		assertEquals(4,targetList.size());
		assertTrue(targetList.contains(board.getCellAt(18, 15)));
		assertTrue(targetList.contains(board.getCellAt(19, 14)));
		assertTrue(targetList.contains(board.getCellAt(20, 15)));
		assertTrue(targetList.contains(board.getCellAt(19, 16)));
		
		
		//Calculates two steps away  
		board.calcTargets(6,21,2);
		targetList = board.getTargets();
		assertEquals(5,targetList.size());
		assertTrue(targetList.contains(board.getCellAt(6, 23)));
		assertTrue(targetList.contains(board.getCellAt(7, 22)));
		assertTrue(targetList.contains(board.getCellAt(8, 21)));
		assertTrue(targetList.contains(board.getCellAt(7, 20)));
		assertTrue(targetList.contains(board.getCellAt(6, 19)));
		
		//Calculates three steps away  
		board.calcTargets(9,6,3);
		targetList = board.getTargets();
		assertEquals(11,targetList.size());
		assertTrue(targetList.contains(board.getCellAt(9, 7)));
		assertTrue(targetList.contains(board.getCellAt(11, 7)));
		assertTrue(targetList.contains(board.getCellAt(12, 6)));
		assertTrue(targetList.contains(board.getCellAt(11, 5)));
		assertTrue(targetList.contains(board.getCellAt(10, 4)));
		assertTrue(targetList.contains(board.getCellAt(9, 3)));
		assertTrue(targetList.contains(board.getCellAt(8, 4)));
		assertTrue(targetList.contains(board.getCellAt(7, 5)));
		assertTrue(targetList.contains(board.getCellAt(6, 6)));
		assertTrue(targetList.contains(board.getCellAt(7, 7)));
		assertTrue(targetList.contains(board.getCellAt(9, 5)));
		
		//Calculate 6 steps away only two targets due to placement of cell in board
		board.calcTargets(0,13,6);
		targetList = board.getTargets();
		assertEquals(2,targetList.size());
		assertTrue(targetList.contains(board.getCellAt(3,10)));
		assertTrue(targetList.contains(board.getCellAt(4,11)));

		
	}
	
	//Targets that allow the user to enter a room 
	//Cells on the spread sheet will be turquoise blue
	@Test 
	public void testTargetsIntoRoom() {
		//Getting into a door when one step away
		board.calcTargets(6,15, 1);
		Set<BoardCell> targetList= board.getTargets();
		assertEquals(4, targetList.size());
		
		assertTrue(targetList.contains(board.getCellAt(5,15)));
		assertTrue(targetList.contains(board.getCellAt(7,15)));
		assertTrue(targetList.contains(board.getCellAt(6,14)));
		assertTrue(targetList.contains(board.getCellAt(6,16)));
		
		
		//Shortcut case to get into a door
		board.calcTargets(1,17, 2);
		targetList= board.getTargets();
		assertEquals(2, targetList.size());
		
		assertTrue(targetList.contains(board.getCellAt(1,16)));
		assertTrue(targetList.contains(board.getCellAt(3,17)));

		
	}
	
	//Targets that allow the user to exit a room 
	//Cells on the spread sheet will be white
	@Test
	public void testRoomExit() {
		//Exit when rolling a one
		board.calcTargets(11,1, 1);
		Set<BoardCell> targetList= board.getTargets();
		assertEquals(1, targetList.size());

		assertTrue(targetList.contains(board.getCellAt(11,2)));
		
		//Exit when rolling a two 
		board.calcTargets(12,19, 2);
		targetList= board.getTargets();
		assertEquals(3, targetList.size());
		
		assertTrue(targetList.contains(board.getCellAt(11,18)));
		assertTrue(targetList.contains(board.getCellAt(12,17)));
		assertTrue(targetList.contains(board.getCellAt(13,18)));


		
	}
	
}
