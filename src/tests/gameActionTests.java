package tests;

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
		board.calcTargets(19,15,3);
		Set<BoardCell> targetSet = board.getTargets();
		System.out.println(targetSet);
		ComputerPlayer testComputerPlayer = new ComputerPlayer();
		BoardCell location = testComputerPlayer.pickLocation(targetSet);
		System.out.println(location);
		// ArrayList<BoardCell> targetList = new ArrayList<BoardCell>(targetSet);
		
	}
	
}
