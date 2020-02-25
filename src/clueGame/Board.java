package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Board {
	public final static int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE] ;
	private Map<Character, String> legend = new HashMap<Character,String>();
	
	// AdjMtx holds a map that contains a set of boardcell that are adjacent to any given cell inside the grid
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	
	// The targets set is used while calculating targets and it holds all cells that are targets
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private String boardConfigFile, roomConfigFile;
	
	public void initialize() {}
	
	public void loadRoomConfig() {}
	
	public void calcAdjacencies() {}
	
	public void calcTargets(BoardCell cell, int pathLlength ) {}
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	
	//GETERS AND SETTERS
	public void setConfigFiles(String layout, String legend) {
		
	}
	
	public Map<Character, String> getLegend(){
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	
	public int getNumColumns() {
		return numColumns;
	}
	
	//FIXME
	public BoardCell getCellAt(int row, int col) {
		
		return null;
	}

	
	
}
