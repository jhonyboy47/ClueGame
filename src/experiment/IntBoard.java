package experiment;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	public void calcAdjacencies() {
		
		
	}
	
	public IntBoard() {
		super();
		calcAdjacencies();
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		
		return null;
	}
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
		
	}
	
	public Set<BoardCell> getTargets(){
		
		return null;
	}
	
	public BoardCell getCell(int x, int y) {
		
		return null;
	}
}
