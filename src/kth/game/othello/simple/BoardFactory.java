package kth.game.othello.simple;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

import java.util.List;

public class BoardFactory {

	private static final int BOARD_SIZE = 8;

	protected BoardFactory() {

	}

	protected SimpleBoard newStartingBoard() {
		// TODO Method stub
		return null;
	}

	protected SimpleBoard newBoardReplacingNodesInBoard(Board oldBoard, List<Node> nodesToSwap, String playerIDToSwapTo)
			throws IllegalArgumentException {
		// TODO Method stub
		return null;
	}

}
