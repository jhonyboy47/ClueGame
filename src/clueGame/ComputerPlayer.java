///Authors: Jhonathan Malagon and Michael Crews
package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private Set<Character> visitedSet;


	
	// visited list
	public ComputerPlayer(String playerName, Integer row, Integer column, Color color) {
		super(playerName, row, column, color);
		visitedSet = new HashSet<Character>();
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		boolean roomFound = false;
		
		ArrayList<BoardCell> tempTargetList = new ArrayList<BoardCell>(targets);
		
		System.out.println(tempTargetList);
		
		Collections.shuffle(tempTargetList);
		
		for(BoardCell boardCell: tempTargetList) {
			
			if (boardCell.isRoom() && !(visitedSet.contains(boardCell.getInitial()))) {
				
				return boardCell;
			}
		}
		
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
}
