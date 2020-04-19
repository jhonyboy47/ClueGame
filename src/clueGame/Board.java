///Authors: Jhonathan Malagon and Michael Crews
// We did the extra credit that outputs errors to an errorLog

package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.collections.ObservableList;
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
import javafx.util.Pair;
import clueGame.BoardCell;
import javaFX.InvalidCellSelection;
import javaFX.SuggestionMenu;

public class Board {
	public final static int MAX_BOARD_SIZE = 50;
	private int numRows, numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Set<BoardCell> visited;
	// AdjMtx holds a map that contains a set of boardcell that are adjacent to any
	// given cell inside the board
	private Map<BoardCell, Set<BoardCell>> adjMtx;

	// The targets set is used while calculating targets and it holds all cells that
	// are targets
	private Set<BoardCell> targets;
	private ArrayList<Player> players;
	private ArrayList<String> weapons;
	private ArrayList<String> rooms;

	private String boardConfigFile, roomConfigFile;
	private String playersConfigFile;
	private String weaponsConfigFile;
	private ArrayList<Card> deck;

	private Boolean usingPlayerConfigFile;
	private Boolean usingWeaponsConfigFile;

	private Solution solution;

	private Player nextPlayer;

	private Random random;

	private int dieRoll;

	private GridPane boardGridPane;

	ArrayList<Pair<Node, String>> highlightedNodes;

	ArrayList<Pair<Player, Circle>> playerCircles;

	private Board() {
		legend = new HashMap<Character, String>();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		visited = new HashSet<BoardCell>();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		weapons = new ArrayList<String>();
		rooms = new ArrayList<String>();
		random = new Random();
		playerCircles = new ArrayList<Pair<Player, Circle>>();

		highlightedNodes = new ArrayList<Pair<Node, String>>();
		usingPlayerConfigFile = false;
		usingWeaponsConfigFile = false;

	}

	private static Board theInstance = new Board();

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		// Surround loadRoomConfig, loadBoardConfig, loadPlayersConfig,loadWeapons with
		// a try catch statement because they could throw a FileNotFoundException or a
		// BadConfigFormatException
		try {
			loadRoomConfig();
			loadBoardConfig();
			if (usingPlayerConfigFile)
				loadPlayersConfig();
			if (usingWeaponsConfigFile)
				loadWeapons();

		} catch (Exception e) {
			e.getMessage();

			e.printStackTrace();
		}

		calcAdjacencies();
		if (usingPlayerConfigFile && usingWeaponsConfigFile)
			dealCards();

	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public ArrayList<String> getWeapons() {
		return weapons;
	}

	public Solution getSolution() {
		return this.solution;

	}

	public void dealCards() {
		// Shuffle
		Collections.shuffle(deck);

		// Testing randomness with shuffling players, weapons, and rooms
		// Collections.shuffle(players);
		// Collections.shuffle(weapons);
		// Collections.shuffle(rooms);

		Boolean foundPerson = false;
		Boolean foundRoom = false;
		Boolean foundWeapon = false;

		String personSolutionName = "";
		String weaponSolutionName = "";
		String roomSolutionName = "";

		// Temp deck to iterate through so can remove the solution cards from the deck;
		ArrayList<Card> tempDeck = new ArrayList<Card>(deck);

		for (Card card : tempDeck) {
			if (card.getCardType() == CardType.PERSON && !foundPerson) {
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
		for (int i = 0; i < deck.size(); i++) {
			int k = i % players.size();
			players.get(k).addMyCards(deck.get(i));
		}

	}

	public void calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				// Declare a temp set to hold the the adjacent cells for the given cell we are
				// on in the for loop
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				BoardCell tempBoardCell = board[row][col];
				int lastCol = numColumns - 1;
				int lastRow = numRows - 1;

				// Adds Adjacencies to the top left corner
				if ((tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == 0)) {
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row][col + 1]);
				}

				// Adds Adjacencies for the first row
				else if ((tempBoardCell.getRow() == 0) && (tempBoardCell.getColumn() > 0)
						&& (tempBoardCell.getColumn() < lastCol)) {
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row][col + 1]);
					tempSet.add(board[row][col - 1]);
				}

				// Adds Adjacencies to the top right corner cell
				else if ((tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == 0)) {
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row][col - 1]);

				}

				// Gives Adjacencies to the left most colomn's that are not corners
				else if ((tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() > 0)
						&& (tempBoardCell.getRow() < lastRow)) {
					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row][col + 1]);

				}

				// Gives Adjacencies to the right most colomn's that are not corners
				else if ((tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() > 0)
						&& (tempBoardCell.getRow() < lastRow)) {
					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row][col - 1]);

				}

				// Adds Adjacencies to the bottom right corner cell
				else if ((tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == lastRow)) {

					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row][col + 1]);
				}

				// Adds adjacencies to the bottom row cells
				else if ((tempBoardCell.getRow() == lastRow) && (tempBoardCell.getColumn() > 0)
						&& (tempBoardCell.getColumn() < lastCol)) {
					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row][col + 1]);
					tempSet.add(board[row][col - 1]);
				}

				// Adds adjacencies to the bottom right corner cell
				else if ((tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == lastRow)) {
					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row][col - 1]);

				}

				// Adds adacencies to cells that are not on a corner or edge
				else if ((tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol)
						&& (tempBoardCell.getRow() > 0) && tempBoardCell.getRow() < lastRow) {
					tempSet.add(board[row + 1][col]);
					tempSet.add(board[row - 1][col]);
					tempSet.add(board[row][col + 1]);
					tempSet.add(board[row][col - 1]);

				}

				// If there are adjacency's
				if (!tempSet.isEmpty()) {
					Set<BoardCell> tempSetCopy = new HashSet<BoardCell>(tempSet);

					// If it is a room it can't have adjacency's
					if (board[row][col].isRoom() && !board[row][col].isDoorway()) {
						tempSet.clear();

					}

					// If the cell is a doorway we must only have one adjacency
					else if (board[row][col].isDoorway()) {
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

						// Removes all other "adjacent" cells besides the one the room can exit to
						// Using a copy set of tempCell because you can't itterate through a set while
						// deleting items in it
						for (BoardCell tempCell : tempSetCopy) {
							if (!tempCell.equals(keepCell)) {
								tempSet.remove(tempCell);
							}
						}
					}

					// If the cell is a walkway we must only add walkways and doors to "adjancecy's"
					else if (board[row][col].isWalkway()) {

						// Makes sure we don't put door way as a "adjacency" for a cell that is not in
						// the correct direction
						for (BoardCell tempCell : tempSetCopy) {
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

								if (row != newRow || col != newCol) {

									tempSet.remove(tempCell);
								}
							}

							// Because doors are rooms also and we don't want to remove doors from adjacency
							else if (tempCell.isRoom()) {
								tempSet.remove(tempCell);
							}

						}
					}

				}

				// Put adjacency set in map for each cell
				adjMtx.put(tempBoardCell, tempSet);
			}
		}
	}

	public void loadWeapons() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + weaponsConfigFile);
		Scanner in = new Scanner(reader);

		// Read in all weapons from file and load the weapons into the deck
		while (in.hasNextLine()) {
			String line = in.nextLine();

			// Adding weapons to the deck
			deck.add(new Card(line, CardType.WEAPON));

			weapons.add(line);
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

	public Map<Character, String> getLegend() {
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

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(board[row][col]);
	}

	public ArrayList<Player> getPlayersSet() {
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
		if (!firstCall && board[row][col].isDoorway()) {
			targets.add(board[row][col]);
			return;
		}

		for (BoardCell cell : getAdjList(row, col)) {
			// If we have already visited the cell it can't be a target
			if (!visited.contains(cell)) {
				visited.add(cell);
				if (pathLength == 1) {
					
					Boolean moveOnPlayerCheck = false;
					for(Player player : players) {
						if(player != nextPlayer && player.getRow() == cell.getRow() && player.getColumn() == cell.getColumn() && cell.isWalkway()) {
							moveOnPlayerCheck = true;
						}
					}
					
					if(moveOnPlayerCheck == false) {
						// Base case
						targets.add(cell);
					}
					
					

				} else {
					// Set firstCall to false because the first call of calculate targets has been
					// completed
					firstCall = false;
					
					Boolean moveOnPlayerCheck = false;
					for(Player player : players) {
						if(player != nextPlayer && player.getRow() == cell.getRow() && player.getColumn() == cell.getColumn() && cell.isWalkway()) {
							moveOnPlayerCheck = true;
						}
					}
					
					if(moveOnPlayerCheck == false) {
						// Recursive call of the calcTargets because pathlength is not 1 yet
						findAllTargets(cell.getRow(), cell.getColumn(), pathLength - 1);
					}

					
				}
				visited.remove(cell);

			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;

	}

	public void loadPlayersConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + playersConfigFile);
		Scanner in = new Scanner(reader);

		// While loop to read in everything from file
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if (lineArray.length != 5) {
				throw new BadConfigFormatException(
						"Error reading in players file: must have name, color, row, column, human/computer player each line");
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

			if (humanOrComputer.equals("Human")) {
				tempPlayer = new HumanPlayer(name, board[row][col], color);
				players.add(tempPlayer);

			} else if (humanOrComputer.equals("Computer")) {
				tempPlayer = new ComputerPlayer(name, board[row][col], color);
				players.add(tempPlayer);
			}

			// Error if humanOrComputer value is not "Human" or "Computer"
			else {
				throw new BadConfigFormatException(
						"Error reading in player file:  must specify if player is computer or human ");
			}

			tempPlayer.setStringColor(stringColor);

			// Adding person to deck
			deck.add(new Card(name, CardType.PERSON));

		}

	}

	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader("./data/" + roomConfigFile);
		Scanner in = new Scanner(reader);

		// This while loop makes sure our code does not have any errors in set up of
		// files being read in
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split(",");
			if (lineArray.length != 3) {
				throw new BadConfigFormatException(
						"Error reading in legend file:  must have three inputs per row [initial (char), Roomname (String), Card or Other (String)] ");
			}
			String initialString = lineArray[0];
			Character initial;
			if (initialString.length() != 1) {
				throw new BadConfigFormatException(
						"Error reading in legend file: First input per row must be one character ");
			} else {
				initial = initialString.charAt(0);
			}
			String name = lineArray[1];
			if (name.length() == 0) {
				throw new BadConfigFormatException(
						"Error reading in legend file: Second input per row must be a String");
			}
			String type = lineArray[2];
			type = type.substring(1);
			if (!type.equals("Card") && !type.equals("Other")) {
				throw new BadConfigFormatException(
						"Error reading in legend file: Third input per row must be a String ('Other' or 'Card')");
			}
			name = name.substring(1);
			// No errors were found in 'legend' file format thus, 'initial' key is mapped to
			// name in legend map
			legend.put(initial, name);

			// Adds room cards
			if (type.equals("Card")) {
				Card tempCard = new Card(name, CardType.ROOM);
				deck.add(tempCard);

				rooms.add(name);
			}

		}
	}

	// Makes sure there are no errors in the csv file that has the actual board
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {

		// Basic file reading
		FileReader reader = new FileReader("./data/" + boardConfigFile);
		Scanner in = new Scanner(reader);

		// Declare row and col counter
		int rows = 0;
		int cols = 0;

		// Once first loop is finished this is set to true
		Boolean firstFinished = false;
		while (in.hasNextLine()) {

			String line = in.nextLine();

			// Makes a list of each row
			String[] lineArray = line.split(",");

			// Checks that all rows have equal number of entries
			if (firstFinished == true) {
				if (cols != lineArray.length) {
					throw new BadConfigFormatException("Wrong number of cols in file");
				}
			}

			cols = lineArray.length;

			// Used to hold colomn index that we are currently at on our board
			int localCols = 0;
			for (String initial : lineArray) {
				Character directionChar = ' ';

				// Used to avoid error of index out of range for single char entries
				if (initial.length() == 2) {
					directionChar = initial.charAt(1);
				}

				// ERROR CHECKING
				if (initial.length() > 2 || initial.length() == 0) {
					throw new BadConfigFormatException(
							"ERROR each board space can only contain 1-2 characters, board contains: " + initial);
				} else if (initial.length() == 2) {
					if (!(directionChar == 'U' || directionChar == 'D' || directionChar == 'L' || directionChar == 'R'
							|| directionChar == 'N')) {
						throw new BadConfigFormatException("Improper direction for board space: " + directionChar);
					}
				}

				if (!legend.containsKey(initial.charAt(0))) {
					throw new BadConfigFormatException(
							"ERROR Board has a room that is not included in the legend file.");

				}

				// Once we have confirmed there are no config errors it is safe to create a new
				// BoardCell
				BoardCell tempBoardCell = new BoardCell(rows, localCols);

				// If we have a cell that has two characters it could be a door but it is
				// definetly a room
				if (initial.length() == 2) {

					tempBoardCell.setRoom(true);

					// N is the only invalid direction for a door thus anything else makes it a door
					if (directionChar != 'N') {
						tempBoardCell.setDoorWay(true);
					} else {
						tempBoardCell.setDisplayNameHere(true);
					}

					// Sets direction of door in tempBoardCell created
					switch (directionChar) {
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

				// Case for single char cells that are rooms
				if (initial.length() == 1 && initial.charAt(0) != 'W') {
					tempBoardCell.setRoom(true);
				}
				// Else it is a walkway
				else if (initial.length() == 1) {
					tempBoardCell.setWalkway(true);
				}

				// Sets the initial for tempBoardCell
				tempBoardCell.setInitial(initial.charAt(0));

				// Sets tempBoardCell to the right location in board
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
		// Checks if accusation is valid
		if (!accusation.equals(solution)) {
			return false;
		}
		return true;
	}

	public Card handleSuggestion(ArrayList<Player> queryPlayers, Suggestion suggestion) {

		Card disproveCard;

		// Goes through players except accuser to check if they can disprove the
		// suggestion
		for (Player player : queryPlayers) {
			disproveCard = player.disproveSuggestion(suggestion, theInstance);

			// If 'disproveCard' is null current player can't disprove
			if (disproveCard != null) {
				return disproveCard;
			}
		}

		return null;
	}

	public void drawBoard(BorderPane bigPane) {

		// Grid Pane for holding e
		GridPane gridPane = new GridPane();

		// Double for loop to load in cells into gridPane
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				// Current cell
				BoardCell cell = getCellAt(row, col);

				// region that will contain the background color and borders for the cell
				Region cellRegion = new Region();

				// String to contain which sides should have borders
				String doorwayInsets = "";

				// Set the doorwayInsets and style background for non doorway rooms and non room
				// cells
				if (!cell.isRoom()) {
					cellRegion.setStyle(
							"-fx-background-color: black, royalblue; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;");
				} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.LEFT) {
					doorwayInsets = "0 0 0 3";
				} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.RIGHT) {
					doorwayInsets = "0 3 0 0";
				} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.UP) {
					doorwayInsets = "3 0 0 0";
				} else if (cell.isDoorway() && cell.getDoorDirection() == DoorDirection.DOWN) {
					doorwayInsets = "0 0 3 0";
				} else {
					cellRegion.setStyle(
							"-fx-background-color: yellow, grey; -fx-background-insets: 0, 0 0 0 0; -fx-min-width: 25; -fx-min-height:25;");
				}

				// Set the style for the doorways
				if (cell.isDoorway()) {
					cellRegion.setStyle("-fx-background-color: yellow, grey; -fx-background-insets: 0, " + doorwayInsets
							+ "; -fx-min-width: 25; -fx-min-height:25;");
				}

				// Add event listeners for highlighting the walkway the mouse is currently over
				if (!cell.isRoom() || cell.isDoorway()) {
					cellRegion.setOnMouseEntered(e -> {
						String oldStyle = cellRegion.getStyle();

						if (cellRegion
								.getStyle() == "-fx-background-color: black, seagreen; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;") {
							cellRegion.setStyle(
									"-fx-background-color: black, lime; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;");
						} else {
							cellRegion.setStyle(
									"-fx-background-color: black, navy; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;");
						}

						cellRegion.setOnMouseExited(e1 -> {
							cellRegion.setStyle(oldStyle);
						});

					});

					//Listener for valid locations to move to on a board
					cellRegion.setOnMouseClicked(e -> {
						if (nextPlayer instanceof HumanPlayer) {
							if (cellRegion.getStyle() == "-fx-background-color: black, lime; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;") {
								Integer newRow = GridPane.getRowIndex(cellRegion);
								Integer newCol = GridPane.getColumnIndex(cellRegion);
								
								// If a player is already in a room, they should be moved
								checkIfPlayerAlreadyInRoom(newRow, newCol);
								
								nextPlayer.setNewLocation(newRow, newCol);
								
								
								//Where we change boolean to true (indicates to program that player has moved)
								((HumanPlayer) nextPlayer).setJustMoved(true);
								
								//Refactored code to adjust were next player is drawn
								drawPlayer();
								
								if(board[newRow][newCol].isRoom()) {
									SuggestionMenu.makeSuggestionMenu(legend.get(board[newRow][newCol].getInitial()));
								}
								
							} else {
								InvalidCellSelection.displayInvalidSelection();
							}
						}
					});
				}

				// Add the region to gridpane in the correct spot
				gridPane.add(cellRegion, col, row);
			}
		}

		boardGridPane = gridPane;
		// Set the grid pane to the center of the big pane
		bigPane.setCenter(gridPane);

	}

	public GridPane getBoardGridPane() {
		return boardGridPane;
	}

	public void checkIfPlayerAlreadyInRoom(int row, int col) {
		
		// Loop through the list of players
		for(Player player : players) {
			// If a player's row and col matches the input row and col then the player needs to be moved
			if(player.getRow() == row && player.getColumn() == col) {
				// Make a list of potential locations to move the player and call movePlayerAlreadyInRoom to the potential locations
				ArrayList<Pair<Integer, Integer>> potentialMoveLocations = movePlayerAlreadyInRoom(board[row][col].getInitial());
				
				// Get a random number to pick from the potential move locations
				int randomNumber = random.nextInt(potentialMoveLocations.size());
				
				// Using the random number, pick a potential move location
				Pair<Integer, Integer> moveLocation = potentialMoveLocations.get(randomNumber);
				
				player.setNewDisplayLocation(moveLocation.getKey(), moveLocation.getValue());
				updatePlayerCircle(player);
			}
		}
	}
	
	public ArrayList<Pair<Integer, Integer>> movePlayerAlreadyInRoom(char initial) {
		// Make a list of potential locations to move the player
		ArrayList<Pair<Integer, Integer>> potentialMoveLocations = new ArrayList<Pair<Integer, Integer>>();
		
		// Loop through the board cells to find potential locations
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numColumns; col++) {
				
				// Don't check the cell if isn't not in the inputed room
				if(board[row][col].getInitial() == initial) {
					
					// Boolean to keep track if a player has been found for that given row and col
					Boolean playerFound = false;
					
					for(Player player : players) {
						if(player.getDisplayRow() == row && player.getDisplayColumn() == col) {
							playerFound = true;
						}
					}
					
					// If no players is on the row and col, then add the row and col to the potential move locations
					if(playerFound == false) {
						potentialMoveLocations.add(new Pair(row, col));
					}
				}
			}
		}
		return potentialMoveLocations;
	}
	
	public void highlightTargetsIfHuman() {
		// Get every grid cell from boardGridPane
		ObservableList<Node> childrens = boardGridPane.getChildren();
		
		int nextPlayerRow = nextPlayer.getRow();
		int nextPlayerCol = nextPlayer.getColumn();
		
		Set<BoardCell> targets = new HashSet<BoardCell>();
		
		
		// If the player is going into a room there could be multiple doors to exit through
		if(board[nextPlayerRow][nextPlayerCol].isRoom()) {
			char roomInitial = board[nextPlayerRow][nextPlayerCol].getInitial();
			
			// Go through all the room cells to see if their are multiple rooms
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numColumns; col++) {
					BoardCell tempBoardCell = board[row][col];
					
					// If there is a doorway in the correct room, do calcTargets and merge the resulting targets to targets
					if(tempBoardCell.isDoorway() && tempBoardCell.getInitial() == roomInitial) {
						calcTargets(row, col, dieRoll);
						targets.addAll(getTargets());
					}
				}
			}
		} else {
			calcTargets(nextPlayerRow, nextPlayerCol, dieRoll);
			targets = getTargets();
		}
		
		
		
		
		//Making sure there is not a null pointer exception when getting the target list
		if(targets.size() != 0) {
		
			ArrayList<Pair<Integer, Integer>> targetPairs = new ArrayList<Pair<Integer, Integer>>();
			
			//Pairing columns and rows of target into a pair that represents a individual cell
			for (BoardCell target : targets) {
				targetPairs.add(new Pair(target.getRow(), target.getColumn()));
			}
			
			
			//Goes through the cells and highlights them 
			for (Node node : childrens) {
				if (targetPairs.contains(new Pair(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)))) {
					//Used to keep track of highlited cells to unhighlight later 
					highlightedNodes.add(new Pair(node, node.getStyle()));
					node.setStyle(
							"-fx-background-color: black, seagreen; -fx-background-insets: 0, 1 1 1 1; -fx-min-width: 25; -fx-min-height:25;");

				}
			}
			
			// Used to ensure a human player moves and accounts for the situation where player has nowhere to move
			((HumanPlayer) nextPlayer).setJustMoved(false);

		}
	
	}

	// (5) Computer player selects a valid target and moves to it

	public void makeComputerPlayerMove() {

		int nextPlayerRow = nextPlayer.getRow();
		int nextPlayerCol = nextPlayer.getColumn();

		calcTargets(nextPlayerRow, nextPlayerCol, dieRoll);
		
		// Make a tempTargets so that multiple targets lists can be merged together 
		Set<BoardCell> tempTargets = new HashSet<BoardCell>();
		
		// If the computer player is going into a room there could be multiple doors to exit through
		if(board[nextPlayerRow][nextPlayerCol].isRoom()) {
			char roomInitial = board[nextPlayerRow][nextPlayerCol].getInitial();
			
			// Go through all the room cells to see if their are multiple rooms
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numColumns; col++) {
					
					// If there is a doorway in the correct room, do calcTargets and merge the resulting targets to tempTargets
					BoardCell tempBoardCell = board[row][col];
					if(tempBoardCell.isDoorway() && tempBoardCell.getInitial() == roomInitial) {
						calcTargets(row, col, dieRoll);
						tempTargets.addAll(getTargets());
					}
				}
			}
		} else {
			calcTargets(nextPlayerRow, nextPlayerCol, dieRoll);
			tempTargets = getTargets();
		}
		
		targets = tempTargets;
		
		//Makes sure there is not a null pointer error when accessing the targets list
		if(getTargets().size() != 0) {
			ComputerPlayer computerPlayer = (ComputerPlayer) nextPlayer;
			
			BoardCell targetCell = computerPlayer.pickLocation(targets);
			
			checkIfPlayerAlreadyInRoom(targetCell.getRow(), targetCell.getColumn());
			nextPlayer.setNewLocation(targetCell.getRow(), targetCell.getColumn());
			drawPlayer();
		}


	}
	
	//Draws the player on the grid
	public void drawPlayer() {
		
		for (Pair<Player, Circle> pair : playerCircles) {
			if (pair.getKey() == nextPlayer) {
				pair.getValue().setCenterX(nextPlayer.getColumn() * 25 + 12.5);
				pair.getValue().setCenterY(nextPlayer.getRow() * 25 + 12.5);

			}
		
		}
	}
	
	
	public void updatePlayerCircle(Player player) {
		for (Pair<Player, Circle> pair : playerCircles) {
			if (pair.getKey() == player) {
				pair.getValue().setCenterX(player.getDisplayColumn() * 25 + 12.5);
				pair.getValue().setCenterY(player.getDisplayRow() * 25 + 12.5);

			}
		}
	}
		
	
	

	public void unHighlightTargets() {

		if (!highlightedNodes.isEmpty()) {
			for (Pair<Node, String> pair : highlightedNodes) {
				pair.getKey().setStyle(pair.getValue());
			}
			highlightedNodes.clear();
		}

	}

	public BorderPane drawRoomNames(BorderPane pane) {

		// Make a new pane to avoid errors
		BorderPane newPane = new BorderPane();

		// Make a group so text can be overlayed
		Group group = new Group();

		// Add the old pane to the group
		group.getChildren().add(pane);

		// Go through every cell to determine if a room name should be displayed there
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {

				// Get the current cell
				BoardCell cell = getCellAt(row, col);

				// Add the room name where appropriate to the group
				if (cell.displayNameHere()) {
					String nameRoom = legend.get(cell.getInitial());
					Text text = new Text(25 * col + 7, 25 * (row + 1), nameRoom);
					text.setFont(new Font(16));
					group.getChildren().add(text);
				}

			}
		}
		// Set the center of the new pane to group
		newPane.setCenter(group);

		return newPane;
	}

	public BorderPane drawPlayers(BorderPane pane) {

		// Make another new Pane to avoid errors
		BorderPane newPane = new BorderPane();

		// Make a group so you can overlay players
		Group group = new Group();

		// Add pane to group
		group.getChildren().add(pane);

		// Add each player to the group
		for (Player player : players) {
			Circle circle = new Circle(player.getColumn() * 25 + 12.5, player.getRow() * 25 + 12.5, 10.5);
			circle.setStyle(player.getColorStyle());
			playerCircles.add(new Pair(player, circle));
			group.getChildren().add(circle);
		}

		// Set the group to the left side of the pane because this will be its final position
		newPane.setLeft(group);
		return newPane;
	}

	// Modified to fit any human player
	public Player getHumanPlayer() {
		for (Player player : players) {
			if (player instanceof HumanPlayer) {
				return player;
			}
		}
		return null;
	}

	public void setNextPlayer() {
		if (nextPlayer == null) {
			nextPlayer = getHumanPlayer();
		} else if (players.indexOf(nextPlayer) < (players.size() - 1)) {
			nextPlayer = players.get(players.indexOf(nextPlayer) + 1);
		} else {
			nextPlayer = getHumanPlayer();
		}
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextDieRoll() {
		dieRoll = random.nextInt(6) + 1;
	}

	public String getDieRollString() {
		return String.valueOf(dieRoll);
	}

	public static Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}

}
