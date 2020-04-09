///Authors: Jhonathan Malagon and Michael Crews
// We did the extra credit that outputs errors to an errorLog

package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;


import clueGame.BoardCell;

public class Board {
	public final static int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;	
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Set<BoardCell> visited;
	// AdjMtx holds a map that contains a set of boardcell that are adjacent to any given cell inside the board
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	// The targets set is used while calculating targets and it holds all cells that are targets
	private Set<BoardCell> targets;
	private ArrayList<Player> players;
	
	private String boardConfigFile, roomConfigFile;
	private String playersConfigFile;
	private String weaponsConfigFile;
	private ArrayList<Card> deck;
	
	private Boolean usingPlayerConfigFile;
	private Boolean usingWeaponsConfigFile;
	
	private Solution solution;
	
	
	private Board() {
		legend = new HashMap<Character,String>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		visited = new HashSet<BoardCell>();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		usingPlayerConfigFile = false;
		usingWeaponsConfigFile = false;
		
	}
	

	private static Board theInstance = new Board();
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		// Surround loadRoomConfig, loadBoardConfig, loadPlayersConfig,loadWeapons with a try catch statement because they could throw a FileNotFoundException or a BadConfigFormatException
		try {
			loadRoomConfig();
			loadBoardConfig();
			if(usingPlayerConfigFile) loadPlayersConfig();
			if(usingWeaponsConfigFile) loadWeapons();


		} catch (Exception e) {
			e.getMessage();
			
			e.printStackTrace();
		}
		
		calcAdjacencies();
		if(usingPlayerConfigFile && usingWeaponsConfigFile) dealCards();
		
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Solution getSolution() {
		return this.solution;
		
	}
	public void dealCards() {
		// Shuffle deck and players
		Collections.shuffle(deck);
		Collections.shuffle(players);
		
		Boolean foundPerson = false;
		Boolean foundRoom = false;
		Boolean foundWeapon = false;
		
		String personSolutionName = "";
		String weaponSolutionName = "";
		String roomSolutionName = "";
		
		// Temp deck to iterate through so can remove the solution cards from the deck;
		ArrayList<Card> tempDeck = new ArrayList<Card>(deck);
		
 		for(Card card : tempDeck) {
			if(card.getCardType() == CardType.PERSON && !foundPerson) { 
				foundPerson = true;
				personSolutionName = card.getCardName();
				deck.remove(card);
			} else if (card.getCardType() == CardType.WEAPON && !foundWeapon) {
				foundWeapon = true;
				weaponSolutionName = card.getCardName();
				deck.remove(card);
			} else if (card.getCardType() == CardType.ROOM && !foundRoom) { 
				foundRoom = true;
				roomSolutionName = card.getCardName();
				deck.remove(card);
			} 
		}
		solution = new Solution(personSolutionName, weaponSolutionName, roomSolutionName);
		
		
		// For loop for dealing cards to players
		for(int i = 0; i < deck.size(); i++) {
			int k = i % players.size();
			players.get(k).addMyCards(deck.get(i));
		}
		
	}
	
	public void calcAdjacencies() {
		for (int row = 0; row < numRows; row++)
		   {
		       for (int col = 0; col < numColumns; col++)
		       {	
		    	  
		    	  // Declare a temp set to hold the the adjacent cells for the given cell we are on in the for loop
		    	  Set<BoardCell> tempSet = new HashSet<BoardCell>();
		    	  BoardCell tempBoardCell = board[row][col];
		    	  int lastCol = numColumns - 1;
		    	  int lastRow = numRows - 1;
		    	 
		    	  //Adds Adjacencies to the top left corner
		    	  if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == 0)) {
		    		  tempSet.add(board[row+1][col]);
		    		  tempSet.add(board[row][col+1]);
		    	  }
		    	  
		    	  //Adds Adjacencies for the first row
		    	  else if ((tempBoardCell.getRow() == 0) && (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) ) {
		    	  	  tempSet.add(board[row+1][col]);
		    	  	  tempSet.add(board[row][col+1]);
		    	  	  tempSet.add(board[row][col-1]);
		    	  }
		       
		    	  //Adds Adjacencies  to the top right corner cell
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == 0)) {
		    		  tempSet.add(board[row+1][col]);
		    		  tempSet.add(board[row][col-1]);
		    		  
		    	  }

		    	  //Gives Adjacencies to the left most colomn's that are not corners
		    	  else if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < lastRow)) {
		    		  tempSet.add(board[row-1][col]);
		    		  tempSet.add(board[row+1][col]);
		    		  tempSet.add(board[row][col+1]);

		    	  }
		    	  
		    	  //Gives Adjacencies to the right most colomn's that are not corners
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < lastRow)) {
		    		  tempSet.add(board[row-1][col]);
		    		  tempSet.add(board[row+1][col]);
		    		  tempSet.add(board[row][col-1]);

		    	  }
		    	  
		    	  //Adds Adjacencies  to the bottom right corner cell
		    	  else if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == lastRow)) {
		    		  
		    		  tempSet.add(board[row-1][col]);
		    		  tempSet.add(board[row][col+1]);
		    	  }
		    	  
		    	  //Adds adjacencies to the bottom row cells
		    	  else if ((tempBoardCell.getRow() == lastRow) && (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) ) {
		    	  	  tempSet.add(board[row-1][col]);
		    	  	  tempSet.add(board[row][col+1]);
		    	  	  tempSet.add(board[row][col-1]);
		    	  }
		    	  
		    	  //Adds adjacencies to the bottom right corner cell
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == lastRow)) {
		    		  tempSet.add(board[row-1][col]);
		    		  tempSet.add(board[row][col-1]);
		    		  
		    	  }
		    	  
		    	  //Adds adacencies to cells that are not on a corner or edge 
		    	  else if ( (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) && (tempBoardCell.getRow() > 0) && tempBoardCell.getRow() < lastRow ) {
		    		  tempSet.add(board[row+1][col]);
		    		  tempSet.add(board[row-1][col]);
		    	  	  tempSet.add(board[row][col+1]);
		    	  	  tempSet.add(board[row][col-1]);
		    		  
		    	  }
		    	  
		    	  
		    	  // If there are adjacency's
		    	  if(!tempSet.isEmpty()) {
		    		  Set<BoardCell> tempSetCopy = new HashSet<BoardCell>(tempSet);
			    	  
		    		  
		    		  //If it is a room it can't have adjacency's
		    		  if(board[row][col].isRoom() && !board[row][col].isDoorway()){
		    			  tempSet.clear();
		    	
		    		  } 
		    		  
		    		  //If the cell is a doorway we must only have one adjacency
		    		  else if(board[row][col].isDoorway()){
		    			  DoorDirection direction = board[row][col].getDoorDirection();
			    		  BoardCell keepCell = null;
			    		  switch (direction) {
				    		  case DOWN:
			    				  keepCell = board[row + 1][col];
			    				  break;
				    		  case UP:
				    			  keepCell = board[row - 1][col];
			    				  break;
				    		  case LEFT:
			    				  keepCell = board[row][col - 1];
			    				  break;
				    		  case RIGHT:
				    			  keepCell = board[row][col + 1];
			    				  break;
			    		  }
			    		  
			    		  //Removes all other "adjacent" cells besides the one the room can exit to
			    		  //Using a copy set of tempCell because you can't itterate through a set while deleting items in it
			    		  for(BoardCell tempCell : tempSetCopy) {
			    			  if(!tempCell.equals(keepCell)) {
			    				  tempSet.remove(tempCell);
			    			  } 
			    		  }
		    		  } 
		    		  
		    		  //If the cell is a walkway we must only add walkways and doors to "adjancecy's"
		    		  else if (board[row][col].isWalkway()) {
		    			  
		    			  //Makes sure we don't put door way as a "adjacency" for a cell that is not in the correct direction
		    			  for (BoardCell tempCell : tempSetCopy){
		    				  if (tempCell.isDoorway()) {
				    			  DoorDirection direction = tempCell.getDoorDirection();
				    			  int newRow = tempCell.getRow();
				    			  int newCol = tempCell.getColumn();
		    					  switch (direction) {
					    		  case DOWN:
				    				  newRow++;
				    				  break;
					    		  case UP:
					    			  newRow--;
				    				  break;
					    		  case LEFT:
				    				  newCol--;
				    				  break;
					    		  case RIGHT:
					    			  newCol++;
				    				  break;
				    				  
		    					  }
		    					  
				    			if ( row != newRow || col != newCol) {
				    				
				    				tempSet.remove(tempCell);
				    			}
				    		  }
		    				  
		    				  //Because doors are rooms also and we don't want to remove doors from adjacency
		    				  else if (tempCell.isRoom()) {
			    					 tempSet.remove(tempCell);
		    				  }
		    					    
		    			  }
		    		 }
		    		  
		    	  }
		    	  
		    	  
		    	  //Put adjacency set in map for each cell
		          adjMtx.put(tempBoardCell, tempSet);
		       }
		   }
	}
	
	public void loadWeapons() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + weaponsConfigFile);
		Scanner in = new Scanner(reader);
		
		
		// Read in all weapons from file and load the weapons into the deck
		while(in.hasNextLine()) {
			String line = in.nextLine();
			
			//Adding weapons to the deck
			deck.add(new Card(line, CardType.WEAPON));
		}
	}
	
	public void setWeaponsFile(String file) {
		usingWeaponsConfigFile = true;
		weaponsConfigFile = file;
	}
	public void setConfigFiles(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
	}
	public void setPlayersFile(String playerFile) {
		usingPlayerConfigFile = true;
		playersConfigFile = playerFile;
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
		return adjMtx.get(board[row][col]);
	}
	
	public ArrayList<Player> getPlayersSet(){
		return players;
	}
	
	// Boolean to describe if there has been more than one call to calcTargets
	private Boolean firstCall = true;

	
	public void calcTargets(int row, int col, int pathLength) {
		visited.clear();
		targets.clear();
		firstCall = true;
		visited.add(board[row][col]);
		
		findAllTargets(row, col, pathLength);
	}
	// Function to calculate targets for a given row, column, and path length
	public void findAllTargets(int row, int col, int pathLength) {
		// Declare start cell use the row and column that was passed in
		BoardCell startCell = board[row][col];
	
		visited.add(startCell);
	
		// This is a for loop to see if adjacent cells could be targets
		if(!firstCall && board[row][col].isDoorway()) {
			targets.add(board[row][col]);
			return;
		}
		
		for(BoardCell cell : getAdjList(row, col)) {
			// If we have already visited the cell it can't be a target 
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					
					// Base case
					targets.add(cell);
				
				} else {
					// Set firstCall to false because the first call of calculate targets has been completed
					firstCall = false;
					
					// Recursive call of the calcTargets because pathlength is not 1 yet
					findAllTargets(cell.getRow(), cell.getColumn(), pathLength - 1);
				}
				visited.remove(cell);
				
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
		
	}
	
	public void loadPlayersConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + playersConfigFile);
		Scanner in = new Scanner(reader);
		
		// While loop to read in everything from file
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if(lineArray.length != 5) {
				throw new BadConfigFormatException("Error reading in players file: must have name, color, row, column, human/computer player each line");
			}
			// Order is name,row,col,stringColor, human/computer inside text file
			String name = lineArray[0];
			Integer row = Integer.parseInt(lineArray[1]);
			Integer col = Integer.parseInt(lineArray[2]);
			String stringColor = lineArray[3];
			
			// Convert color from name of color to RGB
			Color color = convertColor(stringColor);
			
			String humanOrComputer = lineArray[4];
			
			// Declare tempPlayer which will be added to players set
			Player tempPlayer;
			
			if(humanOrComputer.equals("Human")) {
				tempPlayer = new HumanPlayer(name,board[row][col],color);
				players.add(tempPlayer);
				
			} else if (humanOrComputer.equals("Computer")) {
				tempPlayer = new ComputerPlayer(name, board[row][col],color);
				players.add(tempPlayer);
			}
			
			// Error if humanOrComputer value is not "Human" or "Computer"
			else {
				throw new BadConfigFormatException("Error reading in player file:  must specify if player is computer or human ");
			}
			
			//Adding person to deck
			deck.add(new Card(name,CardType.PERSON));
			
			
		}
		
		
		
		
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
				initial = initialString.charAt(0);
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
			
			
			//Adds room cards
			if (type.equals("Card")) {
			Card tempCard = new Card(name,CardType.ROOM);
			deck.add(tempCard);
			}
			
			
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
					} else {
						tempBoardCell.setDisplayNameHere(true);
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

	
	public boolean checkAccusation(Solution accusation) {
		//Checks if accusation is valid
		if(!accusation.equals(solution)) {
			return false;
		}
		return true;
	}
	
	
	public Card handleSuggestion(ArrayList<Player> queryPlayers, Suggestion suggestion) {
		
		Card disproveCard;
		
		//Goes through players except accuser to check if they can disprove the suggestion
		for(Player player : queryPlayers) {
			disproveCard = player.disproveSuggestion(suggestion, theInstance);
			
			//If 'disproveCard' is null current player can't disprove
			if(disproveCard != null) {
				return disproveCard;
			}
		}
		
		
		return null;
	}
	
	
	public void drawBoard(BorderPane bigPane) {
	    GridPane boardPane = new GridPane();
	    // boardPane.setHgap(100);
	    // boardPane.setVgap(100);
	    // boardPane.setPadding(new Insets(10, 10, 0, 10));
	    
	    for (int row = 0; row < numRows; row++) {
	    	for (int col = 0; col < numColumns;col++ ) {
	    		BoardCell cell = getCellAt(row, col);
	    		
	    	    // System.out.println(cell.isRoom());
	    	    // Rectangle box2 = new Rectangle(1000,100);
	    		// System.out.println(row);
	    	    Region testRegion = new Region();
	    	   
	    	    
	    	    String doorwayInsets = "";
	    	    
	    	    if(cell.isDoorway() && cell.getDoorDirection() == DoorDirection.LEFT) {
	    	    	doorwayInsets = "0 0 0 3";	
	    		} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.RIGHT) {
	    			doorwayInsets = "0 3 0 0";
	    		} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.UP) {
	    			doorwayInsets = "3 0 0 0";
	    		} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.DOWN) {
	    			doorwayInsets = "0 0 3 0";
	    		} else if (cell.isRoom()) {
	    			testRegion.setStyle("-fx-background-color: yellow, grey; -fx-background-insets: 0, 0 0 0 0; -fx-min-width: 25; -fx-min-height:25;");
	    		} else {
	    			testRegion.setStyle("-fx-background-color: black, royalblue; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;");
	    		}
	    	    
	    	    if(cell.isDoorway()) {
	    	    	testRegion.setStyle("-fx-background-color: yellow, grey; -fx-background-insets: 0, "+ doorwayInsets + "; -fx-min-width: 25; -fx-min-height:25;");
	    	    }
	    	    boardPane.add(testRegion, col, row);
	    	    
	    	    

	    		
	    	}
	    }
	    
	    
	    bigPane.setTop(boardPane);
	    
	   
	}
	
	public BorderPane drawRoomNames(BorderPane pane) {
		BorderPane pane2 = new BorderPane();
	    
	    Group group = new Group();
	   
	    group.getChildren().add(pane);
	    
	    
	    for (int row = 0; row < numRows; row++) {
	    	for (int col = 0; col < numColumns;col++ ) {
	    		BoardCell cell = getCellAt(row, col);
	    		if(cell.displayNameHere()) {
	    			
	    			String nameRoom = legend.get(cell.getInitial());
	    			Text text = new Text(25*col + 7,25*(row+1),nameRoom);
	    			text.setFont(new Font(16));
	    			group.getChildren().add(text);
	    		}
	    		
	    	}
	    }
	    pane2.setTop(group);
	    
	    return pane2;
	    
		
	}
	public static Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
	}
	
}
