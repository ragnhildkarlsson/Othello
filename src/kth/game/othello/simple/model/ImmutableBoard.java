package kth.game.othello.simple.model;

import java.util.HashMap;
import java.util.Set;

import kth.game.othello.simple.BoardObserver;

/**
 * TODO add more doc here ImmutabelBoard is an immutable class.
 * 
 */
public class ImmutableBoard {
	private final HashMap<Coordinates, ImmutableNode> nodes;
	private List<BoardObserver> boardObservers;

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
	public enum Direction {
		NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
	}

	/**
	 * TODO
	 */
	public ImmutableBoard(Set<ImmutableNode> nodes) {
		this.nodes = new HashMap<Coordinates, ImmutableNode>();
		for (ImmutableNode immutableNode : nodes) {
			this.nodes.put(immutableNode.getCoordinates(), immutableNode);
		}
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public Set<ImmutableNode> getNodes() {
		// TODO implement return copy
		return null;
	}

	/**
	 * Returns the node at the given coordinates. Throws
	 * IllegalArgumentException if the given coordinates are not within the
	 * board.
	 * 
	 * @return The node at the given coordinates.
	 */
	public ImmutableNode getNodeAtCoordinates(Coordinates coordinates) {
		// TODO Implement
		return null;
	}

	/**
	 * Return the next node in the given direction relative to the given node or
	 * null if no such node exists.
	 * 
	 * @param originNode
	 *            the node from which to get the next.
	 * @param direction
	 *            the direction in which the next node is to be fetched.
	 * @return the next node in the given direction or null if no such node
	 *         exists.
	 */
	public ImmutableNode getNextNodeInDirection(ImmutableNode originNode, Direction direction) {
		// TODO implement;
		return null;
		// OLD IMPLEMENTATION
		// int x = startPointNode.getXCoordinate();
		// int y = startPointNode.getYCoordinate();
		//
		// if (!coordinatesAreWithinTheBoard(x, y)) {
		// throw new
		// IllegalArgumentException("Used a starting point that was outside of the board: "
		// + x + ", " + y);
		// }
		//
		// switch (direction) {
		// case NORTH:
		// y--;
		// break;
		// case EAST:
		// x++;
		// break;
		// case SOUTH:
		// y++;
		// break;
		// case WEST:
		// x--;
		// break;
		// case NORTHEAST:
		// y--;
		// x++;
		// break;
		// case SOUTHEAST:
		// y++;
		// x++;
		// break;
		// case SOUTHWEST:
		// y++;
		// x--;
		// break;
		// case NORTHWEST:
		// y--;
		// x--;
		// break;
		// }
		//
		// if (!coordinatesAreWithinTheBoard(x, y)) {
		// return null;
		// } else {
		// return getNodeAtCoordinates(x, y);
		// }

	}

	private boolean coordinatesAreOnBoard(Coordinates coordinates) {
		// TODO reimplement;
		return false;
	}

	@Override
	public String toString() {
		// TODO Implement;
		return null;
		// OLD IMPLEMTATITION
		// StringBuilder stringBuilder = new StringBuilder();
		// stringBuilder.append('\n');
		// stringBuilder.append("Board with players: ");
		// for (String player : getPlayerIDs()) {
		// stringBuilder.append(player + ", ");
		// }
		// stringBuilder.delete(stringBuilder.length() - 2,
		// stringBuilder.length());
		// stringBuilder.append('\n');
		// for (int x = 0; x < boardSide; x++) {
		// for (int y = 0; y < boardSide; y++) {
		// Node node = getNodeAtCoordinates(x, y);
		// if (node.isMarked()) {
		// String playerInitial = node.getOccupantPlayerId().substring(0, 1);
		// stringBuilder.append(playerInitial);
		// } else {
		// stringBuilder.append('•');
		// }
		// stringBuilder.append(' ');
		// }
		// stringBuilder.append('\n');
		// }
		// return stringBuilder.toString();
	}

	// private List<String> getPlayerIDs() {
	// HashSet<String> playerIDs = new HashSet<>();
	// for (Node node : nodes) {
	// String playerID = node.getOccupantPlayerId();
	// if (playerID != null) {
	// playerIDs.add(playerID);
	// }
	// }
	// ArrayList<String> playerIDList = new ArrayList<>();
	// playerIDList.addAll(playerIDs);
	// return playerIDList;
	// }
}
