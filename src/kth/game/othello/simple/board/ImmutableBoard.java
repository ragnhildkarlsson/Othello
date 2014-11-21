package kth.game.othello.simple.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * A simple implementation of the {@link kth.game.othello.board.Board}
 * interface, SimpleBoard is an immutable class.
 * 
 * @author Daniel Schlaug
 */
public class ImmutableBoard {
	private final List<Node> nodes;
	private final int boardSide;

	/**
	 * The different type of directions on the board
	 * 
	 * <pre>
	 * 	 0 1 2 3 4 5 6 7 8  
	 * 0
	 * 1
	 * 2        NORTH
	 * 3          ^
	 * 4 WEST <       > EAST
	 * 5          V			
	 * 6        SOUTH
	 * 7
	 * 8
	 * </pre>
	 */
	protected enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	/**
	 * 
	 * Creates a SimpleBoard from the provided nodes. Assumes a square board
	 * with coordinates in the following order of (x, y):
	 * 
	 * <pre>
	 *  (0,0) (1,0) (2,0)
	 *  (0,1) (1,1) (2,1)
	 *  (0,2) (1,2) (2,2)
	 * </pre>
	 * 
	 * @param nodes
	 *            the nodes from which to create the board. Must be a full board
	 *            with its nodes in rising order of x- and then y-coordinates of
	 *            the nodes.
	 */
	protected ImmutableBoard(List<Node> nodes) {
		this.nodes = new ArrayList<>();
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
		ArrayList<Node> nodesClone = new ArrayList<>();
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

		int row = y;
		int column = x;
		int index = boardSide * row + column;
		Node result = this.nodes.get(index);

		return result;
	}

	/**
	 * Returns the node with the given id or null if no such node exists.
	 * 
	 * @return Returns the node with the given id or null if no such node
	 *         exists.
	 */
	protected Node getNodeById(String nodeId) {

		Node foundNode = null;

		for (Node node : nodes) {
			if (node.getId().equals(nodeId)) {
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
			y--;
			break;
		case EAST:
			x++;
			break;
		case SOUTH:
			y++;
			break;
		case WEST:
			x--;
			break;
		case NORTHEAST:
			y--;
			x++;
			break;
		case SOUTHEAST:
			y++;
			x++;
			break;
		case SOUTHWEST:
			y++;
			x--;
			break;
		case NORTHWEST:
			y--;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\n');
        stringBuilder.append("Board with players: ");
        for (String player : getPlayerIDs()) {
            stringBuilder.append(player + ", ");
        }
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        stringBuilder.append('\n');
        for (int x = 0; x < boardSide; x++) {
            for (int y = 0; y < boardSide; y++) {
                Node node = getNodeAtCoordinates(x,y);
                if (node.isMarked()) {
                    String playerInitial = node.getOccupantPlayerId().substring(0,1);
                    stringBuilder.append(playerInitial);
                } else {
                    stringBuilder.append('•');
                }
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private List<String> getPlayerIDs() {
        HashSet<String> playerIDs = new HashSet<>();
        for (Node node : nodes) {
            String playerID = node.getOccupantPlayerId();
            if (playerID != null) {
                playerIDs.add(playerID);
            }
        }
        ArrayList<String> playerIDList = new ArrayList<>();
        playerIDList.addAll(playerIDs);
        return playerIDList;
    }
}
