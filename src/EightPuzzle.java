import java.util.Random;

public class EightPuzzle {
	
	// Properties
	private final int side = 3;
    private final int numTiles = side * side - 1;
    private final Random rand = new Random();
    private int[] puzzle = new int[numTiles + 1];
    
    private static final int[] GOAL_STATE = { 0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final byte[][] MANHATTAN_DIST = {
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
    
    // Heuristic which returns the sum of the Manhattan distances
    private int heuristic(){
		int cumulativeDistance = 0;
		for (int j = 0; j < puzzle.length; j++) {
			if (puzzle[j] == 0) {
				continue;
			}

			cumulativeDistance += MANHATTAN_DIST[j][puzzle[j]];
		}

		return cumulativeDistance;
	};
    
    
    
    private int[] generate() {
    	
    		//puzzle = GOAL_STATE;
    		shuffle();
    		
    		if(!isSolvable()) return GOAL_STATE;
    		
    		return puzzle;
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
    
    
    
    public static void main(String[] args) {
    	
    	
    		EightPuzzle e = new EightPuzzle();
    		e.puzzle = GOAL_STATE;
    		for(int i = 0; i < e.puzzle.length; i++) {
    			System.out.print(e.puzzle[i]);
    		}
    		System.out.println("\n" + e.isSolvable());
    		
    		
    		e.generate();
    		
    		System.out.println();
    		
    		for(int i: e.puzzle) {
    			System.out.print(i);
    		}
    		
    		System.out.println("\n" + e.isSolvable());
    		
    		System.out.println("\ndone");
    		
    }

}
