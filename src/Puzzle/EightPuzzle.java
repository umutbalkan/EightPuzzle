package Puzzle;
import java.util.Arrays;
import java.util.Random;

public class EightPuzzle {
	
	// Properties
	private final int side = 3;
    private final int numTiles = side * side - 1;
    private final Random rand = new Random();
    private int[] puzzle;
    
    public final int[] GOAL_STATE = { 0, 1, 2, 3, 4, 5, 6, 7, 8};
    
    private final int[][] MANHATTAN_DIST = {
    		// This allows O(1) lookup of all distances.
    		{0, 1, 2, 1, 2, 3, 2, 3, 4}, // 0
    		{1, 0, 1, 2, 1, 2, 3, 2, 3}, // 1
    		{2, 1, 0, 3, 2, 1, 4, 3, 2}, // 2
    		{1, 2, 3, 0, 1, 2, 1, 2, 3}, // 3
    		{2, 1, 2, 1, 0, 1, 2, 1, 2}, // 4
    		{3, 2, 1, 2, 1, 0, 3, 2, 1}, // 5
    		{2, 3, 4, 1, 2, 3, 0, 1, 2}, // 6
    		{3, 2, 3, 2, 1, 2, 1, 0, 1}, // 7
    		{4, 3, 2, 3, 2, 1, 2, 1, 0}  // 8
    	};
    
    EightPuzzle(){
    		puzzle = new int[numTiles + 1];
    		
    		// initialize puzzle with GOAL_STATE
    		for(int i = 0; i < puzzle.length;i++) {
    			puzzle[i] = GOAL_STATE[i];
    		}
    		
    		generate(); // shuffles goal_state & generates a solvable 8-puzzle
    }
    
    // FUNCTIONS
    
    // Heuristic which returns the sum of the Manhattan distances
    public int heuristic(){
		int distance = 0;
		for (int j = 0; j < puzzle.length; j++) {
			if (puzzle[j] == 0) continue; // Ignore blank tile
			
			distance += MANHATTAN_DIST[j][puzzle[j]];
		}
		return distance;
	}
    
    
    
    public void generate() {
    		shuffle();	
    		if(!isSolvable()) { // shuffle until puzzle is solvable
    			generate();
    		}
    }
    
    /*  Only half the permutations of the puzzle are solvable.
 
        Whenever a tile is preceded by a tile with higher value it counts
        as an inversion. The number of inversions must be even for the puzzle
        to be solvable.
 
        See also:
        www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     */
    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (puzzle[j] > puzzle[i]) {
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }
    
    private void shuffle() {
        int n = numTiles;
        while (n > 0) {
            int r = rand.nextInt(n--);
            int tmp = puzzle[r];
            puzzle[r] = puzzle[n];
            puzzle[n] = tmp;
        }
    }
    
    //  Iterates through a given puzzle and returns the location of the blank tile
    public int findBlankTile() {
    		for(int i: puzzle) {
    			if(puzzle[i] == 0) 
    				return i;
    		}
    		
    		return -1; // error
    }
    
    public int[] getPuzzle() {
    		return puzzle;
    }
    
    public void setPuzzle(int[] puzz) {
    		for(int i : puzz)
    			puzzle[i] = puzz[i];
    	}
    
    public void setBlankTile(int i) {
    		int blankTile = findBlankTile();
    		puzzle[blankTile] = i;
    }
    
    // MAIN METHOD
    public static void main(String[] args) {
    	
    	
		EightPuzzle e = new EightPuzzle();
		
		for(int i = 0; i < e.puzzle.length; i++) {
			if(i % 3 == 0 & i != 0) System.out.print("\n");
			System.out.print(e.puzzle[i] +" ");
		}
		System.out.println("\nDoes generated Eight Puzzle solvable?: " + e.isSolvable());
		System.out.println("Heuristic value: " + e.heuristic());
		System.out.println("Index of the blank tile: " + e.findBlankTile());
		
		System.out.println("\ndone");
    }// end_main()

    
}// end_class
