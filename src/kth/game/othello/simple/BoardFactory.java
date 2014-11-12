package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

public class BoardFactory {

	private static final int BOARD_SIZE = 8;
	private final String playerWhiteId;
	private final String playerBlackId;

	/**
	 * Create a BoardFactory given the two players playing.
	 * 
	 * @param playerWhiteId
	 *            ID of the player using white marks.
	 * @param playerBlackId
	 *            ID of the player using black marks.
	 */
	protected BoardFactory(String playerWhiteId, String playerBlackId) {
		this.playerWhiteId = playerWhiteId;
		this.playerBlackId = playerBlackId;
	}

	protected SimpleBoard newStartingBoard() {
		List<Node> nodes = new ArrayList<Node>();
		int higherMiddlePosition = BOARD_SIZE / 2;
		int lowerMiddlePosition = higherMiddlePosition - 1;

		for (int x = BOARD_SIZE - 1; x < BOARD_SIZE; x--) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				SimpleNode node;
				// If we are in the middle of the board, set nodes to occupied
				if (x == higherMiddlePosition || x == lowerMiddlePosition && y == higherMiddlePosition
						|| y == lowerMiddlePosition) {
					if (x == y) { // set equally colored nodes diagonally to
									// each other
						node = new SimpleNode(x, y, playerBlackId);
					} else {
						node = new SimpleNode(x, y, playerWhiteId);
					}
				} else {// else the node should be unmarked
					node = new SimpleNode(x, y, null);
				}
				nodes.add(node);
			}
		}
		SimpleBoard board = new SimpleBoard(nodes);
		return board;
	}

	protected SimpleBoard newBoardReplacingNodesInBoard(Board oldBoard, List<Node> nodesToSwap, String playerIDToSwapTo)
			throws IllegalArgumentException {
		// TODO Method stub
		return null;
	}

}
