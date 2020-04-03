package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;

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
	public void TestCheckAccusation() {
			 
		
			 //Tests solution that is correct
		     Solution solution = board.getSolution();
		     Solution testSolution = new Solution(solution.getPerson(),solution.getWeapon(),solution.getRoom());
		     
		     assertTrue(board.checkAccusation(testSolution));
		     
		     
		     //Tests solution with wrong person
		     testSolution = new Solution("wrongPerson",solution.getWeapon(),solution.getRoom());
		     assertTrue(!board.checkAccusation(testSolution));
		     
		     //Test solution with a wrong weapon
		     testSolution = new Solution(solution.getPerson(),"wrongWeapon",solution.getRoom());
		     assertTrue(!board.checkAccusation(testSolution));


		     //Test solution with a wrong room
		     testSolution = new Solution(solution.getPerson(),solution.getWeapon(),"wrongRoom");
		     assertTrue(!board.checkAccusation(testSolution));
		     
		
	}
	
	@Test
	public void CreateSuggestionTest() {
		ComputerPlayer player = new ComputerPlayer();
		player.changeMyCards(new Card("Jared", CardType.PERSON));
		
		ArrayList<String> unSeenPersonCards = new ArrayList<String>();
		unSeenPersonCards.add("Bob");
		unSeenPersonCards.add("Shark");
		unSeenPersonCards.add("Jared");
		player.setSeenPersonCards(unSeenPersonCards);
		
		ArrayList<String> unSeenWeaponCards = new ArrayList<String>();
		unSeenWeaponCards.add("Pillow");
		unSeenWeaponCards.add("Car");
		player.setSeenWeaponsCards(unSeenWeaponCards);
		
		Suggestion suggestion = player.createSuggestion(board.getCellAt(5, 18));
		
		assertEquals('M', suggestion.room);
		
		int bobCounter = 0;
		int sharkCounter = 0;
		int pillowCounter = 0;
		int carCounter = 0;
		for(int i = 0; i < 500; i++) {
			suggestion = player.createSuggestion(board.getCellAt(5, 18));
			if(suggestion.person.equals("Bob")) {
				bobCounter++;
			} else if (suggestion.person.equals("Shark")) {
				sharkCounter++;
			}
			if(suggestion.weapon.equals("Pillow")) {
				pillowCounter++;
			} else if(suggestion.weapon.equals("Car")) {
				carCounter++;
			}
		}
		
		assertEquals(bobCounter, 250, 50);
		assertEquals(sharkCounter, 250, 50);
		assertEquals(pillowCounter, 250, 50);
		assertEquals(carCounter, 250, 50);
		
		
		unSeenPersonCards.remove("Shark");
		unSeenWeaponCards.remove("Car");
		
		player.setSeenPersonCards(unSeenPersonCards);
		player.setSeenWeaponsCards(unSeenWeaponCards);
		
		suggestion = player.createSuggestion(board.getCellAt(5, 18));
		
		assertEquals("Bob", suggestion.person);
		assertEquals("Pillow", suggestion.weapon);
	}
	
	@Test
	public void TestDisproveSuggestion() {
		
		//If player has only one matching card it should be returned
		Player testPlayer = new Player();
		Card weaponCard = new Card("Gummy Bears", CardType.WEAPON);
		Card roomCard = new Card("Jesus Room", CardType.ROOM);
		testPlayer.addMyCards(roomCard);
		testPlayer.addMyCards(weaponCard);
		
		Suggestion testSuggestion = new Suggestion('L',"Gummy Bears", "Shark");
		
		assertEquals(weaponCard, testPlayer.disproveSuggestion(testSuggestion, board));
		
		//If players has >1 matching card, returned card should be chosen randomly
		testSuggestion = new Suggestion('J',"Gummy Bears", "Bob");
		
		int gummyCounter = 0, jesusCounter = 0;
		// System.out.println(testPlayer.getMyCards());
		for (int i = 0; i < 500; i++) {
			Card disproveCard = testPlayer.disproveSuggestion(testSuggestion, board);
			if (disproveCard.equals(weaponCard) ) {
				gummyCounter++;
			}
			else if (disproveCard.equals(roomCard) ) {
				jesusCounter++;
			}
			
		}
		
		assertEquals(250,gummyCounter, 50);
		assertEquals(250, jesusCounter, 50);
		
		
		//If player has no matching cards, null is returned
		testSuggestion = new Suggestion('K',"Pillow", "Bob");
		
		assertEquals(null, testPlayer.disproveSuggestion(testSuggestion, board));
		
		
			
	}
	
}
