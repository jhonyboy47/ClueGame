///Authors: Jhonathan Malagon and Michael Crews
// We did the extra credit that outputs errors to an errorLog

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
		// Surround loadRoomConfig and loadBoardConfig with a try catch statement because they could throw a FileNotFoundException or a BadConfigFormatException
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void calcAdjacencies() {}
		
	private static Board theInstance = new Board();
	
	private Board() {}
	
	public static Board getInstance() {
		return theInstance;
	}
	
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
	
	public Set<BoardCell> getAdjList(int row, int col){
		return null;
	}
	
	public void calcTargets(int row, int col, int pathLength) {}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + roomConfigFile);
		Scanner in = new Scanner(reader);
		
		
		//This while loop makes sure our code does not have any errors in set up of files being read in
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if(lineArray.length != 3) {
				throw new BadConfigFormatException("Error reading in legend file:  must have three inputs per row [initial (char), Roomname (String), Card or Other (String)] ");
			} 
			String initialString = lineArray[0];
			Character initial;
			if(initialString.length() != 1) {
				throw new BadConfigFormatException("Error reading in legend file: First input per row must be one character ");
			} else {
				initial = new Character(initialString.charAt(0));
			}
			String name = lineArray[1];
			if(name.length() == 0) {
				throw new BadConfigFormatException("Error reading in legend file: Second input per row must be a String");
			}
			String type = lineArray[2];
			type = type.substring(1);
			if(!type.equals("Card") && !type.equals("Other")) {
				throw new BadConfigFormatException("Error reading in legend file: Third input per row must be a String ('Other' or 'Card')");
			}
			name = name.substring(1);
			//No errors were found in 'legend' file format thus, 'initial' key is mapped to name in legend map
			legend.put(initial, name);
		}
	}
	
	
	//Makes sure there are no errors in the csv file that has the actual board
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		
		//Basic file reading 
		FileReader reader = new FileReader("./data/" + boardConfigFile);
		Scanner in = new Scanner(reader);
		
		//Declare row and col counter
		int rows = 0;
		int cols = 0;
		
		//Once first loop is finished this is set to true
		Boolean firstFinished = false;
		while(in.hasNextLine()) {
			
			
			String line = in.nextLine();
			
			// Makes a list of each row
			String[] lineArray = line.split(",");
			
			//Checks that all rows have equal number of entries
			if(firstFinished == true) {
				if(cols != lineArray.length) {
					throw new BadConfigFormatException("Wrong number of cols in file");
				}
			}
			
			
			cols = lineArray.length;
			
			//Used to hold colomn  index that we are currently at on our board
			int localCols = 0;
			for(String initial : lineArray) {
				Character directionChar = ' ';
				
				//Used to avoid error of index out of range for single char entries
				if(initial.length() == 2) {
					directionChar = initial.charAt(1);
				}
				
				//ERROR CHECKING 
				if (initial.length() > 2 || initial.length() == 0) {
					throw new BadConfigFormatException("ERROR each board space can only contain 1-2 characters, board contains: " + initial );
				}
				else if (initial.length() == 2) {
					if ( !(directionChar == 'U' || directionChar == 'D' || directionChar == 'L' || directionChar == 'R' || directionChar == 'N') ) {
						throw new BadConfigFormatException("Improper direction for board space: " + directionChar);
					}
				}
				
				if( !legend.containsKey(initial.charAt(0)) ) {
					throw new BadConfigFormatException("ERROR Board has a room that is not included in the legend file.");
					
				}
				
				// Once we have confirmed there are no config errors it is safe to create a new BoardCell
				BoardCell tempBoardCell = new BoardCell(rows, localCols);
				
				
				//If we have a cell that has two characters it could be a door but it is definetly a room
				if ( initial.length() == 2) {
					tempBoardCell.setRoom(true);
					
					//N is the only invalid direction for a door thus anything else makes it a door
					if(directionChar != 'N') {
						tempBoardCell.setDoorWay(true);
					}
					
					// Sets direction of door in tempBoardCell created
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
				} 
				
				//Case for single char cells that are rooms
				if(initial.length() == 1 && initial.charAt(0) != 'W') {
					tempBoardCell.setRoom(true);
				} 
				//Else it is a walkway
				else if (initial.length() == 1) {
					tempBoardCell.setWalkway(true);
				}
				
				//Sets the initial for tempBoardCell
				tempBoardCell.setInitial(initial.charAt(0));
				
				//Sets tempBoardCell to the right location in board
				board[rows][localCols] = tempBoardCell;
				
				localCols++;
			}
			rows++;
			firstFinished = true;
		}
		
		// Sets numColumns and numRows
		numColumns = cols; 
		numRows = rows;
				
	}

	
	
}
