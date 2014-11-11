package kth.game.othello.board;

import java.util.List;

public class SimpleBoard implements Board {
	private List<Node> nodes;

	/**
	 * Returns an ordered list of rows using the natural order in x- and then
	 * y-coordinate of the nodes.
	 *
	 * @return the nodes of the board
	 */
	@Override
	public List<Node> getNodes() {
		// TODO Must return a copy of the list. (Add a test for this.)
		return null;
	}

	protected SimpleBoard(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return Returns the node at the given coordinates or null if outside
	 *         board.
	 */
	protected Node getNodeAtCoordinates(int x, int y) {
		// TODO Method stub
		return null;
	}
}
