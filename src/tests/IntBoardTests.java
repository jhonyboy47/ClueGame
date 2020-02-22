package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	IntBoard board;
	
	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }
	
	
	/*
	 * Test adjacencies for top left corner
	 */
	@Test
	public void testAdjacency0()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	
	/*
	 * Test adjacencies for bottom left corner
	 */
	@Test
	public void testAdjacency1()
	{
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for right edge
	 */
	@Test
	public void testAdjacency2()
	{
		BoardCell cell = board.getCell(1,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for left edge
	 */
	@Test
	public void testAdjacency3()
	{
		BoardCell cell = board.getCell(3,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(4, 0)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for left edge
	 */
	@Test
	public void testAdjacency4()
	{
		BoardCell cell = board.getCell(1,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(3, testList.size());
	}
	
	

}
