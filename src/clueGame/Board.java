///Authors: Jhonathan Malagon and Michael Crews

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
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
	
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + roomConfigFile);
		Scanner in = new Scanner(reader);
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if(lineArray.length != 3) {
				throw new BadConfigFormatException("Error reading in legend file");
			} 
			String initialString = lineArray[0];
			Character initial;
			if(initialString.length() != 1) {
				throw new BadConfigFormatException("Error reading in legend file");
			} else {
				initial = new Character(initialString.charAt(0));
			}
			String name = lineArray[1];
			if(name.length() == 0) {
				throw new BadConfigFormatException("Error reading in legend file");
			}
			String type = lineArray[2];
			type = type.substring(1);
			if(!type.equals("Card") && !type.equals("Other")) {
				throw new BadConfigFormatException("Error reading in legend file");
			}
			name = name.substring(1);
			legend.put(initial, name);
		}
	}
	
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
		boardConfigFile = layout;
		roomConfigFile = legend;
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
	
	public BoardCell getCellAt(int row, int col) {
		
		return board[row][col];
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + boardConfigFile);
		Scanner in = new Scanner(reader);
		
		int rows = 0;
		int cols = 0;
		Boolean firstFinished = false;
		while(in.hasNextLine()) {
			

			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if(firstFinished == true) {
				if(cols != lineArray.length) {
					throw new BadConfigFormatException("Wrong number of cols in file");
				}
			}
			firstFinished = true;
			cols = lineArray.length;
			
			int localCols = 0;
			for(String initial : lineArray) {
				Character directionChar = ' ';
				
				if(initial.length() == 2) {
					directionChar = initial.charAt(1);
				}
				
				if (initial.length() > 2 || initial.length() == 0) {
					throw new BadConfigFormatException("ERROR each board space can only contain 1-2 characters, board contains: " + initial );
				}
				else if (initial.length() == 2) {
					if ( !(directionChar == 'U' || directionChar == 'D' || directionChar == 'L' || directionChar == 'R' || directionChar == 'N') ) {
						throw new BadConfigFormatException("Improper direction for board space: " + directionChar);
					}
				}
				if( !legend.containsKey(initial.charAt(0)) ) {
					throw new BadConfigFormatException("ERROR Board has a room not included in the legend file.");
					
				}
				
				// Once we have confirmed there are no config errors 
				
				BoardCell tempBoardCell = new BoardCell(rows, localCols);
				
				if ( initial.length() == 2) {
					if(directionChar != 'N') {
						tempBoardCell.setDoorWay(true);
					}
					tempBoardCell.setRoom(true);
					switch(directionChar) {
					case 'U':
						tempBoardCell.setDirection(DoorDirection.UP);
						break;
					case 'D':
						tempBoardCell.setDirection(DoorDirection.DOWN);
						break;
					case 'L':
						tempBoardCell.setDirection(DoorDirection.LEFT);
						break;
					case 'R':
						tempBoardCell.setDirection(DoorDirection.RIGHT);
						break;
					}	
				} else {tempBoardCell.setDoorWay(false);}
				if(initial.length() == 1 && initial.charAt(0) != 'W') {
					tempBoardCell.setRoom(true);
				} else if (initial.length() == 1) {
					tempBoardCell.setWalkway(true);
				}
				tempBoardCell.setInitial(initial.charAt(0));
				board[rows][localCols] = tempBoardCell;
				localCols++;
			}
			rows++;
		}
		numColumns = cols; 
		numRows = rows;
				
	}

	
	
}
