package experiment;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
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
		    	  
		    	  //0,0 cell
		    	  if ( tempBoardCell.getColumn() == 0 && tempBoardCell.getRow() == 0) {
		    		  
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col+1]);
		    	  }
		    	  
		    	  //Checks for the first rows stuff
		    	  if ((tempBoardCell.getRow() == 0) && (tempBoardCell.getColumn() > 0) && (tempBoardCell.getColumn() < grid[0].length - 1) )
		    	  	  tempSet.add(grid[row+1][col]);
		    	  	  tempSet.add(grid[row][col+1]);
		    	  	  tempSet.add(grid[row][col-1]);
		    	  	  
		    	  //top right corner cell	  
		    	  if ( tempBoardCell.getColumn() == grid[0].length-1 && tempBoardCell.getRow() == 0)

		    	  //Checks for the first column's case stuff
		    	  if ( (tempBoardCell.getColumn() == 0 && tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < grid.length - 1)) {
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col+1]);

		    	  }
		    	  
		    	  //Checks for the Last column's case stuff
		    	  if ( (tempBoardCell.getColumn() == grid[0].length-1 && tempBoardCell.getRow() > 0) && (tempBoardCell.getRow() < grid.length - 1)) {
		    		  tempSet.add(grid[row-1][col]);
		    		  tempSet.add(grid[row+1][col]);
		    		  tempSet.add(grid[row][col-1]);

		    	  }
		          
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
		
		
	}
	
	public Set<BoardCell> getTargets(){
		
		return null;
	}
	
	public BoardCell getCell(int x, int y) {
		if(x > (grid.length - 1) || x > (grid[0].length - 1) || x < 0 || y < 0) {
			return null;
		}
		return grid[x][y];
		
	}
}
