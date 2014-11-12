package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * A simple implementation of the {@link kth.game.othello.board.Board}
 * interface.
 *
 * @author Daniel Schlaug
 */
public class SimpleBoard implements Board {
	private final List<Node> nodes;
	private final int boardSide;

	/**
	 * The different type of directions on the board
	 */
	protected enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	/**
	 * Creates a SimpleBoard from the provided nodes. Assumes a square board.
	 *
	 * @param nodes
	 *            the nodes from which to create the board. Must be a full board
	 *            with its nodes in the natural order in x- and then
	 *            y-coordinate of the nodes.
	 */
	protected SimpleBoard(List<Node> nodes) {
		this.nodes = new ArrayList<Node>();
		this.nodes.addAll(nodes);
		this.boardSide = (int) Math.sqrt(nodes.size());
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
	 * Returns the node at the given coordinates. Throws
	 * IllegalArgumentException if the given coordinates are not within the
	 * board.
	 *
	 * @return The node at the given coordinates.
	 */
	protected Node getNodeAtCoordinates(int x, int y) {
		if (!coordinatesAreWithinTheBoard(x, y)) {
			throw new IllegalArgumentException("Tried to get a node outside of the board.");
		}

		int row = (boardSide - 1) - y;
		int column = x;
		int index = boardSide * row + column;
		return this.nodes.get(index);
	}

	/**
     * Returns the node with the given id or null if no such node exists.
     *
	 * @return Returns the node with the given id or null if no such node exists.
	 */
	protected Node getNodeById(String nodeId) {

        Node foundNode = null;

		for (Node node : nodes) {
            if (node.getId() == nodeId) {
                foundNode = node;
            }
		}

		return foundNode;
	}

	/**
	 * Return the next node in the given direction relative to the given node or
	 * null if no such node exists.
	 *
	 * @param startPointNode
	 *            the node from which to get the next.
	 * @param direction
	 *            the direction in which the next node is to be fetched.
	 * @return the next node in the given direction or null if no such node
	 *         exists.
	 */
	protected Node getNextNodeInDirection(Node startPointNode, Direction direction) {

		int x = startPointNode.getXCoordinate();
		int y = startPointNode.getYCoordinate();

		if (!coordinatesAreWithinTheBoard(x, y)) {
			throw new IllegalArgumentException("Used a starting point that was outside of the board: " + x + ", " + y);
		}

		switch (direction) {
		case NORTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case SOUTH:
			y--;
			break;
		case WEST:
			x--;
			break;
		case NORTHEAST:
			y++;
			x++;
			break;
		case SOUTHEAST:
			y--;
			x++;
			break;
		case SOUTHWEST:
			y--;
			x--;
			break;
		case NORTHWEST:
			y++;
			x--;
			break;
		}

        if (!coordinatesAreWithinTheBoard(x, y)) {
            return null;
        } else {
            return getNodeAtCoordinates(x, y);
        }

	}

	private boolean coordinatesAreWithinTheBoard(int x, int y) {
		final int boardSide = (int) Math.sqrt(nodes.size());
		return 0 <= x && x < boardSide && 0 <= y && y < boardSide;
	}
}
