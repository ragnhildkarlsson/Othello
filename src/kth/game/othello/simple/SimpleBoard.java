package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * @author Daniel Schlaug
 */
public final class SimpleBoard implements Board {
	private final List<Node> nodes;

	/**
	 * The different type of directions on the board
	 */
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

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

	/**
	 * @return Returns the node with the given id or null if no node with given
	 *         id exist on board
	 */
	protected Node getNodeById(String NodeId) {

		// TODO Method stub
		return null;
	}

	/**
	 * Return the next node in the given direction relative to the node with the
	 * given id. If no node with the given id exists on board of no next node in
	 * the given direction exist null will be returned
	 * 
	 * @param board
	 * @param nodeId
	 * @param direction
	 * @return
	 */
	protected Node getNextNodeInDirection(String nodeId, Direction direction) {
		// TODO Method-stub
		return null;
	}
}
