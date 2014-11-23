package kth.game.othello.simple.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kth.game.othello.simple.BoardObserver;

/**
 * BoardFactory is responsible for generating new {@link ImmutableBoard}. and
 * notify the NodeObserver of the node that have been replaced when it creates a
 * new board from an existing board TODO see if this doc can be improves
 * 
 */
public class ImmutableBoardFactory {

	private List<BoardObserver> observers;

	/**
	 * Create a BoardFactory. That will notify the observer about the nodes that
	 * are replaced when it creates a new board from an an existing board TODO
	 * TODO describe better
	 */
	public ImmutableBoardFactory() {
		observers = new ArrayList<BoardObserver>();
	}

	public void addNodeObserver(BoardObserver observer) {
		observers.add(observer);
	}

	/**
	 * Return a board with the give Node.
	 * 
	 * @param nodes
	 * @return
	 */
	public ImmutableBoard boardFromNodes(Set<ImmutableNode> nodes) {
		// TODO Implement
		return null;
	}

	/**
	 * Generate a new board given an old Board, given a set of nodes from the
	 * old board to swap, and the player ID to swap the nodes to. Notifies the
	 * nodeObserver of this factory about which nodes that are replaced on the
	 * new Board.
	 * 
	 * @param oldBoard
	 *            The old board to swap nodes on
	 * @param nodesToSwap
	 *            Which nodes on the old board that should be swapped
	 * @param playerIDToSwapTo
	 *            player ID to set for the nodes that will be swapped
	 * @return a board with nodes swapped according to nodesToSwap TODO discuss
	 *         should we throw expection id nodes on to swap does not exist on
	 *         board?
	 */
	public ImmutableBoard newBoardReplacingNodesInBoard(ImmutableBoard oldBoard, Set<ImmutableNode> nodesToSwap,
			String playerIDToSwapTo) throws IllegalArgumentException {
		// TODO
		// Remember Notifications do
		// observer.nodeUpdated(lastValue, newValue);
		// for each node replaced;

		// Old implementation

		// if (oldBoard.getNodes().size() != DEFAULT_BOARD_SIZE *
		// DEFAULT_BOARD_SIZE) {
		// // TODO Remove dependency on board size
		// throw new
		// IllegalArgumentException("Cannot generate a new board based on differently sized old board.");
		// }
		// //
		// List<Node> nodes = new ArrayList<Node>();
		// for (int y = 0; y < DEFAULT_BOARD_SIZE; y++) {
		// for (int x = 0; x < DEFAULT_BOARD_SIZE; x++) {
		// // TODO do not create new nodes
		// // See if this node needs to be swapped
		// Node newNode;
		// Node tempNode = findNodeByCoordinates(nodesToSwap, x, y);
		// if (tempNode != null) { // this node should be swapped
		// newNode = new ImmutableNode(x, y, playerIDToSwapTo);
		// } else { // this node should remain the same as in old board
		// tempNode = findNodeByCoordinates(oldBoard.getNodes(), x, y);
		// newNode = new ImmutableNode(x, y, tempNode.getOccupantPlayerId());
		// }
		// nodes.add(newNode);
		// }
		// }
		// ImmutableBoard newBoard = new ImmutableBoard(nodes);

		return null;
	}

	// /**
	// * //TODO
	// *
	// * @return
	// */
	// public ImmutableBoard newDefaultStartingBoard() {
	// // TODO implement
	// // Old Implementation
	// // List<Node> nodes = new ArrayList<Node>();
	// // int higherMiddlePosition = DEFAULT_BOARD_SIZE / 2;
	// // int lowerMiddlePosition = higherMiddlePosition - 1;
	// //
	// // for (int y = 0; y < DEFAULT_BOARD_SIZE; y++) {
	// // for (int x = 0; x < DEFAULT_BOARD_SIZE; x++) {
	// // ImmutableNode node;
	// // // If we are in the middle of the board, set nodes to occupied
	// // if ((y == higherMiddlePosition || y == lowerMiddlePosition)
	// // && (x == higherMiddlePosition || x == lowerMiddlePosition)) {
	// // if (y == x) { // set equally colored nodes diagonally to
	// // // each other
	// // node = new ImmutableNode(x, y, DEFAULT_WHITE_PLAYER_ID);
	// // } else {
	// // node = new ImmutableNode(x, y, DEFAULT_BLACK_PLAYER_ID);
	// // }
	// // } else {// else the node should be unmarked
	// // node = new ImmutableNode(x, y, null);
	// // }
	// // nodes.add(node);
	// // }
	// // }
	// // ImmutableBoard board = new ImmutableBoard(nodes);
	// return null;
	// }
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
