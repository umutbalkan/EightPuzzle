package Puzzle;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Node {
	
	// Puzzle this node represents
	final int[] PUZZLE;
	// Index of the blank tile
	final int BLANK_INDEX;
	// Parent of this node
	Node parent;
	// Cost thus far to reach this tile.
	int actualCost;	
	// Actions available at this state
	int actions;

	// Constant which represents the no-op bit.
	private static final byte ACTION_NOOP	= 0;
	// Constant which represents the "move up" bit.
	private static final byte ACTION_UP		= 1<<0;	// 1	
	// Constant which represents the "move down" bit.
	private static final byte ACTION_DOWN	= 1<<1; // 10
	 // Constant which represents the "move left" bit.
	private static final byte ACTION_LEFT	= 1<<2; // 100
	 // Constant which represents the "move right" bit.
	private static final byte ACTION_RIGHT	= 1<<3; // 1000

	 /* Table which keeps bitsums for each location within the puzzle for the
	 *  actions available at those locations.
	 */
	public final byte[] AVAIL_ACTIONS = {
		ACTION_DOWN|ACTION_RIGHT,			ACTION_LEFT|ACTION_DOWN|ACTION_RIGHT,			ACTION_LEFT|ACTION_DOWN,
		ACTION_UP|ACTION_DOWN|ACTION_RIGHT,		ACTION_LEFT|ACTION_UP|ACTION_DOWN|ACTION_RIGHT,		ACTION_LEFT|ACTION_UP|ACTION_DOWN,
		ACTION_UP|ACTION_RIGHT,				ACTION_LEFT|ACTION_UP|ACTION_RIGHT,				ACTION_LEFT|ACTION_UP
	};

	/**
	 * {@code {@link #actualCost} + estimated remaining cost to the goal
	 * state}
	 */
	int heuristicCost;
	
	
	Node(EightPuzzle Epuzzle){
		BLANK_INDEX = Epuzzle.findBlankTile();
		PUZZLE = Epuzzle.getPuzzle();
		
		parent = null;
		actualCost = Integer.MAX_VALUE;
		heuristicCost = Integer.MAX_VALUE;
		actions = AVAIL_ACTIONS[BLANK_INDEX];
	}
	
	
	 // Swaps two positions within an array.
	private static void swap(int[] array, int i, int j) {
		if (i == j) return; // if i and j are the same do nothing.

		int temp = array[j];
		array[j] = array[i];
		array[i] = temp;
	}
	
	/*
	 * Generates a collection of successors from this state.
	 * @return the child successors from this state
	 */
	Collection<Node> generateSuccessors() {
		int newBlankIndex;
		Collection<Node> successors = new LinkedList<>();

		if ((actions&ACTION_UP) != 0) {
			newBlankIndex = BLANK_INDEX-3;
			Node successorNode = createSuccessorNode(newBlankIndex);
			successors.add(successorNode);
		}

		if ((actions&ACTION_DOWN) != 0) {
			newBlankIndex = BLANK_INDEX+3;
			Node successorNode = createSuccessorNode(newBlankIndex);
			successors.add(successorNode);
		}

		if ((actions&ACTION_LEFT) != 0) {
			newBlankIndex = BLANK_INDEX-1;
			Node successorNode = createSuccessorNode(newBlankIndex);
			successors.add(successorNode);
		}


		if ((actions&ACTION_RIGHT) != 0) {
			newBlankIndex = BLANK_INDEX+1;
			Node successorNode = createSuccessorNode(newBlankIndex);
			successors.add(successorNode);
		}

		return successors;
	}
	
	// Creates and initialize new successor Node
	public Node createSuccessorNode(int newBlankInd) {
		int[] successor = Arrays.copyOf(PUZZLE, PUZZLE.length);
		swap(successor, BLANK_INDEX, newBlankInd);
		EightPuzzle newPuzzle = new EightPuzzle();
		newPuzzle.setPuzzle(successor);
		newPuzzle.setBlankTile(newBlankInd);
		return new Node(newPuzzle);
	}

}
