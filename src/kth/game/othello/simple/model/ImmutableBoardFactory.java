package kth.game.othello.simple.model;

import java.util.Set;

/**
 * BoardFactory is responsible for generating new {@link ImmutableBoard}.
 * 
 */
public class ImmutableBoardFactory {

	/**
	 * Create a ImmutableBoardFactory.
	 */
	public ImmutableBoardFactory() {
	}

	/**
	 * Return a ImmutableBoard with the given nodes.
	 * 
	 * @param nodes
	 * @return a ImmutableBoard with the given nodes
	 */
	public ImmutableBoard boardFromNodes(Set<ImmutableNode> nodes) {
		// TODO Implement
		return null;
	}

	/**
	 * //TODO
	 * 
	 * @return
	 */
	public ImmutableBoard newDefaultStartingBoard() {
		// TODO implement

		// Old Implementation
		// List<Node> nodes = new ArrayList<Node>();
		// int higherMiddlePosition = DEFAULT_BOARD_SIZE / 2;
		// int lowerMiddlePosition = higherMiddlePosition - 1;
		//
		// for (int y = 0; y < DEFAULT_BOARD_SIZE; y++) {
		// for (int x = 0; x < DEFAULT_BOARD_SIZE; x++) {
		// ImmutableNode node;
		// // If we are in the middle of the board, set nodes to occupied
		// if ((y == higherMiddlePosition || y == lowerMiddlePosition)
		// && (x == higherMiddlePosition || x == lowerMiddlePosition)) {
		// if (y == x) { // set equally colored nodes diagonally to
		// // each other
		// node = new ImmutableNode(x, y, DEFAULT_WHITE_PLAYER_ID);
		// } else {
		// node = new ImmutableNode(x, y, DEFAULT_BLACK_PLAYER_ID);
		// }
		// } else {// else the node should be unmarked
		// node = new ImmutableNode(x, y, null);
		// }
		// nodes.add(node);
		// }
		// }
		// ImmutableBoard board = new ImmutableBoard(nodes);
		return null;
	}
	//
	// //
	// // private Node findNodeByCoordinates(List<Node> nodesToSearch, int
	// // xCoordinate, int yCoordinate) {
	// // for (Node node : nodesToSearch) {
	// // if (node.getXCoordinate() == xCoordinate && node.getYCoordinate() ==
	// // yCoordinate)
	// // return node;
	// // }
	// // return null;
	// // }
}
