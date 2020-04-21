///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;
import java.util.*;

import javaFX.ControlGUI;

public class ComputerPlayer extends Player{
	//Holds rooms that have been visited by each instance of ComputerPlayer
	private Set<Character> visitedRooms = new HashSet<Character>();
	private static ArrayList<String> unseenWeaponCards = new ArrayList<String>();
	private static ArrayList<String> unseenPersonCards = new ArrayList<String>();
	
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	private BoardCell lastRoomVisited = null;
	
	private static Boolean makeAccusation = false;
	private static Suggestion accusation;
	
	public ComputerPlayer() {
		
	}
	
	public static void setUnseenWeaponsCards(ArrayList<String> unseenWeapons) {
		unseenWeaponCards = unseenWeapons;
	}
	public static void setUnseenPersonCards(ArrayList<String> unseenPeople) {
		unseenPersonCards = unseenPeople;
	}
	
	public void addAdditionalSeenCard(Card card){
		seenCards.add(card);
	}
	
	public static void setAccusation(Suggestion inputAccusation) {
		accusation = inputAccusation;
	}
	
	public static Suggestion getAccusation() {
		return accusation;
	}
	
	public static void removeSeenCard(Card card) {
		if(card.getCardType() == CardType.WEAPON) {
			unseenWeaponCards.remove(card.getCardName());
		} else if(card.getCardType() == CardType.PERSON) {
			unseenPersonCards.remove(card.getCardName());
		}
	}
	
	public void setLasVisitedRoom(BoardCell cell) {
		lastRoomVisited = cell;
	}
	public BoardCell getLastVisitedCell() {
		return lastRoomVisited;
	}
	
	public static void setMakeAccusation(Boolean bool) {
		makeAccusation = bool;
	}
	
	public static Boolean getMakeAccusation() {
		return makeAccusation;
	}
	
	public ComputerPlayer(String playerName, BoardCell startingCell, Color color) {
		super(playerName, startingCell, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// Array List to hold target list so that the target list can be shuffled
		ArrayList<BoardCell> tempTargetList = new ArrayList<BoardCell>(targets);
		
		tempTargetList.remove(lastRoomVisited);
		
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
		ArrayList<String> tempUnSeenPersonCards = new ArrayList(unseenPersonCards);
		ArrayList<String> tempUnSeenWeaponCards = new ArrayList(unseenWeaponCards);
		
		
		//Removes cards a computerPlayer has in hand from possible suggestions
		for(Card card : myCards) {
			tempUnSeenPersonCards.remove(card.getCardName());
			tempUnSeenWeaponCards.remove(card.getCardName());
		}
		
		// Remove cards shown to player from the human player
		for(Card card : seenCards) {
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
