// Authors: Michael Crews and Jhon Malagon

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
import clueGame.HumanPlayer;
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
		
		//Room matches current location test
		assertEquals('M', suggestion.room);
		

		int bobCounter = 0;
		int sharkCounter = 0;
		int pillowCounter = 0;
		int carCounter = 0;
		
		//Used to ensured there was an even randomization of results for suggestions
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
		
		//If multiple persons not seen, one of them is randomly selected
		assertEquals(bobCounter, 250, 50);
		assertEquals(sharkCounter, 250, 50);
		
		//If multiple weapons not seen, one of them is randomly selected
		assertEquals(pillowCounter, 250, 50);
		assertEquals(carCounter, 250, 50);
		
		
		unSeenPersonCards.remove("Shark");
		unSeenWeaponCards.remove("Car");
		
		player.setSeenPersonCards(unSeenPersonCards);
		player.setSeenWeaponsCards(unSeenWeaponCards);
		
		suggestion = player.createSuggestion(board.getCellAt(5, 18));
		
		//One person not seen, it's selected test
		assertEquals("Bob", suggestion.person);

		//If only one weapon not seen, it's selected test
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
	
	@Test
	public void handleSuggestionTest() {
		
		//Test setup 
		Card sharkishaCard = new Card("Sharkisha", CardType.PERSON);
		Card bobCard = new Card("Bob", CardType.PERSON);
		Card gameRoomCard = new Card("Game Room", CardType.ROOM);
		Card pingPongRoomCard = new Card("Ping Pong Room", CardType.ROOM);
		Card libraryRoom = new Card("Library", CardType.ROOM);
		Card carCard = new Card("Car", CardType.WEAPON);
		Card pillowCard = new Card("Pillow", CardType.WEAPON);
		Card gummyBearsCard = new Card("Gummy Bears", CardType.WEAPON);
		
		ComputerPlayer computerPlayer1 = new ComputerPlayer();
		ComputerPlayer computerPlayer2 = new ComputerPlayer();
		HumanPlayer humanPlayer = new HumanPlayer();
		
		computerPlayer1.addMyCards(sharkishaCard);
		computerPlayer1.addMyCards(gameRoomCard);
		computerPlayer1.addMyCards(carCard);
		
		computerPlayer2.addMyCards(bobCard);
		computerPlayer2.addMyCards(pingPongRoomCard);
		computerPlayer2.addMyCards(pillowCard);
		
		humanPlayer.addMyCards(libraryRoom);
		humanPlayer.addMyCards(gummyBearsCard);
		//End of test set up
		
		
		// Suggestion no one can disprove returns null
		// Computer Player 1 is the accuser
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(humanPlayer);
		playerList.add(computerPlayer2);
		
		Suggestion suggestion = new Suggestion('J', "Marshmellow", "Jared");
		
		Card responseCard = board.handleSuggestion(playerList,suggestion);
		
		assertEquals(null, responseCard);
		
		// Suggestion only accusing player can disprove returns null
		// Computer Player 1 is the accuser
		suggestion = new Suggestion('G', "Marshmellow", "Jared");
		
		responseCard = board.handleSuggestion(playerList,suggestion);
		
		assertEquals(null, responseCard);
		
		
		// Suggestion only human can disprove, but human is accuser, returns null
		// human player is the accuser
		playerList.clear();
		playerList.add(computerPlayer1);
		playerList.add(computerPlayer2);
		suggestion = new Suggestion('L', "Marshmellow", "Jared");
	
		responseCard = board.handleSuggestion(playerList,suggestion);
		
		assertEquals(null, responseCard);
		
		// Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		// Computer player 1 is the accuser
		playerList.clear();
		playerList.add(computerPlayer2);
		playerList.add(humanPlayer);
		
		responseCard = board.handleSuggestion(playerList,suggestion);
		
		assertEquals(libraryRoom, responseCard);
		
		// Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
		// Computer player 1 is the accuser
		suggestion = new Suggestion('L', "Pillow", "Jared");
		
		responseCard = board.handleSuggestion(playerList, suggestion);
		
		assertEquals(pillowCard, responseCard);
		
		// Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		// Human player is the accuser
		playerList.clear();
		playerList.add(computerPlayer1);
		playerList.add(computerPlayer2);
		suggestion = new Suggestion('G', "Pillow", "Jared");
		responseCard = board.handleSuggestion(playerList, suggestion);
		assertEquals(gameRoomCard, responseCard);
	
	}
	
}
