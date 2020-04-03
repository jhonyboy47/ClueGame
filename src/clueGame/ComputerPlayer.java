///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private Set<Character> visitedRooms = new HashSet<Character>();
	private static ArrayList<String> unSeenWeaponCards;
	private static ArrayList<String> unSeenPersonCards;

	public ComputerPlayer() {
		
	}
	
	
	
	// For testing
	public void setSeenWeaponsCards(ArrayList<String> seenWeapons) {
		unSeenWeaponCards = seenWeapons;
	}
	public void setSeenPersonCards(ArrayList<String> seenPeople) {
		unSeenPersonCards = seenPeople;
	}
	
	
	
	// visited list
	public ComputerPlayer(String playerName, BoardCell startingCell, Color color) {
		super(playerName, startingCell, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// Array List to hold target list so that the target list can be shuffled
		ArrayList<BoardCell> tempTargetList = new ArrayList<BoardCell>(targets);
		
		// Shuffle target list
		Collections.shuffle(tempTargetList);
		
		// for loop to find if there is a room to pick that has not been visited
		for(BoardCell boardCell: tempTargetList) {

			if (boardCell.isRoom() && !(visitedRooms.contains(boardCell.getInitial()))) {
				
				visitedRooms.add(boardCell.getInitial());
				return boardCell;
			}
		}
		
		//Add to visited room list
		visitedRooms.add(tempTargetList.get(0).getInitial());

		// Return random boardcell
		return tempTargetList.get(0);
	}
	
	public void makeAccusation() {
		
	}
	
	public Suggestion createSuggestion(BoardCell currentCell){
		try {
			if(!currentCell.isRoom()) {
				throw new Exception("The input boardCell is not a room which is not valid for making a suggestion");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Collections.shuffle(unSeenPersonCards);
		Collections.shuffle(unSeenWeaponCards);
		
		Suggestion suggestion = new Suggestion(currentCell.getInitial(), unSeenWeaponCards.get(0), unSeenPersonCards.get(0));
		

		return suggestion;
		
		
		
	}
}
