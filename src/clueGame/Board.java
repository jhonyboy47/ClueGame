package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	public final static int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE] ;
	private Map<Character, String> legend = new HashMap<Character,String>();
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

}
