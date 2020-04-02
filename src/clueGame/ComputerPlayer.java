///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private Set<Character> visitedRooms = new HashSet<Character>();;

	public ComputerPlayer() {
		
	}
	
	// visited list
	public ComputerPlayer(String playerName, Integer row, Integer column, Color color) {
		super(playerName, row, column, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		System.out.println(visitedRooms);
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
	
	public void createSuggestion() {
		
	}
}
