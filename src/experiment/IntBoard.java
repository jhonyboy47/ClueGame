// Authors: Michael Crews and Jhonathan Malagon

package experiment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;


public class IntBoard {
	
	
	// Declare and initialize needed instance variables
	// AdjMtx holds a map that contains a set of boardcell that are adjacent to any given cell inside the grid
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	// The visited set is used while calculating targets and it holds all cells that been visited before
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	// The targets set is used while calculating targets and it holds all cells that are targets
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	// grid is a 2d array that holds every boardcell in the grid
	private BoardCell[][] grid = new BoardCell[4][4];
	
	public void calcAdjacencies() {
		// This is a double for loop to go through the 2d grid to calculate the adjacencies set for every board cell
		// Learned how to loop through a 2D array in Java from this website: 
		// http://ice-web.cc.gatech.edu/ce21/1/static/JavaReview-RU/Array2dBasics/a2dLoop.html
		for (int row = 0; row < grid.length; row++)
		   {
		       for (int col = 0; col < grid[0].length; col++)
		       {	
		    	  // Declare a temp set to hold the the adjacent cells for the given cell we are on in the for loop
		    	  Set<BoardCell> tempSet = new HashSet<BoardCell>();
		    	  BoardCell tempBoardCell = grid[row][col];
		    	  int lastCol = grid[0].length-1;
		    	  int lastRow = grid.length-1;
		    	  
		    	  //0,0 cell
		    	  if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == 0)) {
		    		  
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col+1]);
		    	  }
		    	  
		    	  //Checks for the first rows stuff
		    	  else if ((tempBoardCell.getRow() == 0) && (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) ) {
		    	  	  tempSet.add(grid[row+1][col]);
		    	  	  tempSet.add(grid[row][col+1]);
		    	  	  tempSet.add(grid[row][col-1]);
		    	  }
		       
		    	  //top right corner cell	  
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == 0)) {
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col-1]);
		    		  
		    	  }

		    	  //Checks for the Left Most column's case stuff
		    	  else if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < lastRow)) {
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col+1]);

		    	  }
		    	  
		    	  //Checks for the Right most column's case stuff
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < lastRow)) {
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col-1]);

		    	  }
		    	  
		    	  //bottom left corner
		    	  else if ( (tempBoardCell.getColumn() == 0) && (tempBoardCell.getRow() == lastRow)) {
		    		  
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row][col+1]);
		    	  }
		    	  
		    	  //bottom rows
		    	  else if ((tempBoardCell.getRow() == lastRow) && (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) ) {
		    	  	  tempSet.add(grid[row-1][col]);
		    	  	  tempSet.add(grid[row][col+1]);
		    	  	  tempSet.add(grid[row][col-1]);
		    	  }
		    	  
		    	  //bottom right corner
		    	  else if ( (tempBoardCell.getColumn() == lastCol) && (tempBoardCell.getRow() == lastRow)) {
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row][col-1]);
		    		  
		    	  }
		    	  
		    	  
		    	  else if ( (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < lastCol) && (tempBoardCell.getRow() > 0) && tempBoardCell.getRow() < lastRow ) {
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row-1][col]);
		    	  	  tempSet.add(grid[row][col+1]);
		    	  	  tempSet.add(grid[row][col-1]);
		    		  
		    	  }
		          adjMtx.put(tempBoardCell, tempSet);
		       }
		   }
	}
	
	public IntBoard() {
		super();
		
		// Assigns column and row values to each boardcell
		// Learned how to loop through a 2D array in Java from this website: 
		// http://ice-web.cc.gatech.edu/ce21/1/static/JavaReview-RU/Array2dBasics/a2dLoop.html
		for (int row = 0; row < grid.length; row++)
		   {
		       for (int col = 0; col < grid[0].length; col++)
		       {
		          grid[row][col] = new BoardCell(row,col);
		       }
		   }
		calcAdjacencies();
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	// This a recursive function for calculating all target cells for a given cell and pathlength
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		// This is a for loop to see if adjacent cells could be targets
		for(BoardCell cell : getAdjList(startCell)) {
			// If we have already visited the cell it can't be a target 
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					// Base case
					targets.add(cell);
				} else {
					// Recursive call of the calcTargets because pathlength is not 1 yet
					calcTargets(cell, pathLength - 1);
				}
				
				visited.remove(cell);
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		
		return targets;
	}
	
	public BoardCell getCell(int x, int y) {
		// return null if getCell is called where x either y are out of bounds
		if(x > (grid.length - 1) || x > (grid[0].length - 1) || x < 0 || y < 0) {
			return null;
		}
		return grid[x][y];
		
	}
}


