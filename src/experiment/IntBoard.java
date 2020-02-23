package experiment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;


public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid = new BoardCell[4][4];
	
	public void calcAdjacencies() {
		for (int row = 0; row < grid.length; row++)
		   {
		       for (int col = 0; col < grid[0].length; col++)
		       {
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
		
		//Assigns columb and row values to each boardcell
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
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		for(BoardCell cell : getAdjList(startCell)) {
			Boolean visitedBool = false;
			for(BoardCell tempCell : visited) {
				if(tempCell.getColumn() == cell.getColumn() && tempCell.getRow() == cell.getRow() ) {
					visitedBool = true;
				}
			}
			if(!visitedBool) {
				visited.add(cell);
				if(pathLength == 1) {
					targets.add(cell);
				} else {
					calcTargets(cell, pathLength - 1);
				}
				
				visited.remove(cell);
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		
		return null;
	}
	
	public BoardCell getCell(int x, int y) {
		// return null if getCell is called where x either y are out of bounds
		if(x > (grid.length - 1) || x > (grid[0].length - 1) || x < 0 || y < 0) {
			return null;
		}
		return grid[x][y];
		
	}
	
	public static void main(String [] args) {
		IntBoard board;
		
	    board = new IntBoard();
	    BoardCell cell = board.getCell(2, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();
	}
}


