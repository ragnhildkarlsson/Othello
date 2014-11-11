package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Schlaug
 */
public final class SimpleBoard implements Board {
	private final List<Node> nodes;

	/**
	 * Creates a SimpleBoard from the provided nodes. Assumes a square board.
	 *
	 * @param nodes
	 *            the nodes from which to create the board. Must be a full
	 *            board.
	 */
	protected SimpleBoard(List<Node> nodes) {
		this.nodes = new ArrayList<Node>();
		this.nodes.addAll(nodes);
	}

	/**
	 * Returns an ordered list of rows using the natural order in x- and then
	 * y-coordinate of the nodes.
	 *
	 * @return the nodes of the board
	 */
	@Override
	public List<Node> getNodes() {
		ArrayList<Node> nodesClone = new ArrayList<Node>();
		nodesClone.addAll(nodes);
		return nodesClone;
	}

	/**
	 * @return Returns the node at the given coordinates or null if outside
	 *         board.
	 */
	protected Node getNodeAtCoordinates(int x, int y) {
		int boardSide = (int) Math.sqrt(nodes.size());
        int index = boardSide * y + x;
		return this.nodes.get(index);
	}
}
