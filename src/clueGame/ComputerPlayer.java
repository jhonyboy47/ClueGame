///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	//Holds rooms that have been visited by each instance of ComputerPlayer
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
		
		//Error checking if this is called and the player is not in a room
		try {
			if(!currentCell.isRoom()) {
				throw new Exception("The input boardCell is not a room which is not valid for making a suggestion");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Lists that computerPlayers have not seen
		ArrayList<String> tempUnSeenPersonCards = new ArrayList(unSeenPersonCards);
		ArrayList<String> tempUnSeenWeaponCards = new ArrayList(unSeenWeaponCards);
		
		//Removes cards a computerPlayer has in hand from possible suggestions
		for(Card card : myCards) {
			tempUnSeenPersonCards.remove(card.getCardName());
			tempUnSeenWeaponCards.remove(card.getCardName());
		}
		
		//Used to help generate random suggestion
		Collections.shuffle(tempUnSeenPersonCards);
		Collections.shuffle(tempUnSeenWeaponCards);
		
		//Suggestion is then made with current room a player is in as one of the suggestions
		Suggestion suggestion = new Suggestion(currentCell.getInitial(), tempUnSeenWeaponCards.get(0), tempUnSeenPersonCards.get(0));
		

		return suggestion;
		
		
		
	}
	
	
	
	

}
