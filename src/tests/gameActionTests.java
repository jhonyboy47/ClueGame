package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;

public class gameActionTests {
	
	
	
	private static Board board;
	@BeforeClass
	public static void BeforeAlL() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		
		// Set all the needed config files
		board.setConfigFiles("ClueGameBoardCSV.csv", "ClueRooms.csv");	
		board.setPlayersFile("ClueGamePlayers.csv");
		board.setWeaponsFile("ClueGameWeapons.csv");
		board.initialize();
	}
	
	
	@Test
	public void TestSelectingTargets() {
		//Testing if room in list that was not just visited, must select it
		board.calcTargets(19,15,3);
		Set<BoardCell> targetSet = board.getTargets();
		ComputerPlayer testComputerPlayer = new ComputerPlayer();
		BoardCell location = testComputerPlayer.pickLocation(targetSet);
		
		assertEquals(19, location.getRow());
		assertEquals(12, location.getColumn());
		assertTrue(targetSet.contains(location));
		
		//Testing if no rooms in list, one room is selected randomly
		board.calcTargets(19,12,2);
		targetSet = board.getTargets();
		location = testComputerPlayer.pickLocation(targetSet);
		
		assertTrue(targetSet.contains(location));
		
		
		//Testing if room just visited is in list, each target (including room) selected randomly
		board.calcTargets(19,14,2);
		targetSet = board.getTargets();
		location = testComputerPlayer.pickLocation(targetSet);
		
		assertTrue(targetSet.contains(location));
	}
	
	@Test
	public void TestMakeAccusation() {
		assertEquals(1, 2);
	}
	
}
