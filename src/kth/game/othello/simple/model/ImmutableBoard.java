package kth.game.othello.simple.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * ImmutableBoard is responsible for keeping track of the nodes in the game state, it is an
 * immutable class.
 * 
 */
public class ImmutableBoard {
	private final HashMap<Coordinates, ImmutableNode> nodes;

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
	 * Construct a new ImmutableBoard given a set of ImmutableNodes.
	 * 
	 * @param nodes
	 *            the nodes which the board should consist of.
	 */
	public ImmutableBoard(Set<ImmutableNode> nodes) {
		this.nodes = new HashMap<Coordinates, ImmutableNode>();
		for (ImmutableNode immutableNode : nodes) {
			this.nodes.put(immutableNode.getCoordinates(), immutableNode);
		}
	}

	/**
	 * Returns the nodes of this board.
	 * 
	 * @return the nodes of this board.
	 */
	public Set<ImmutableNode> getNodes() {
		HashSet<ImmutableNode> nodesCopy = new HashSet<>(this.nodes.values());
		return nodesCopy;
	}

	/**
	 * Returns the node at the given coordinates. Throws
	 * IllegalArgumentException if the given coordinates are not within the
	 * board.
	 * 
	 * @param coordinates
	 *            the coordinates of the node that is to be returned.
	 * 
	 * @return The node at the given coordinates.
	 * 
	 * @throws IllegalArgumentException
	 *             if the coordinates does not exist on the board.
	 */
	public ImmutableNode getNodeAtCoordinates(Coordinates coordinates) throws IllegalArgumentException {
		if (!this.coordinatesAreOnBoard(coordinates)) {
			throw new IllegalArgumentException("Coordinates does not exist on board");
		}
		return nodes.get(coordinates);
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
	 * @throws IllegalArgumentException
	 *             if the origin node is outside the board.
	 */
	public ImmutableNode getNextNodeInDirection(ImmutableNode originNode, Direction direction)
			throws IllegalArgumentException {

		Coordinates originCoord = originNode.getCoordinates();
		int x = originCoord.getXCoordinate();
		int y = originCoord.getYCoordinate();
		if (!this.coordinatesAreOnBoard(originCoord)) {
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
		Coordinates newCoordinates = new Coordinates(x, y);

		if (!coordinatesAreOnBoard(newCoordinates)) {
			return null;
		} else {
			return getNodeAtCoordinates(newCoordinates);
		}

	}

	private boolean coordinatesAreOnBoard(Coordinates coordinates) {
		if (nodes.containsKey(coordinates))
			return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ImmutableNode node : this.nodes.values()) {
			sb.append(node.toString());
		}
		return sb.toString();
	}

	/**
	 * Create a new board from this board, with a given subset of the old nodes
	 * to be occupied by a given playerID in the new board. All other nodes remain in the
	 * same state.
	 * 
	 * @param nodesToSwap
	 *            nodes to get occupied by the given playerID.
	 * @param playerId
	 *            the playerID of the player to occupy the swapped nodes.
	 * @return a new board with the given set of nodes now occupied by the
	 *         chosen playerID.
	 */
	public ImmutableBoard getCopyWithNodeSwapped(Set<ImmutableNode> nodesToSwap, String playerId) {
		Set<ImmutableNode> newNodes = new HashSet<>();
		for (ImmutableNode node : nodesToSwap) {
			ImmutableNode newNode = new ImmutableNode(node.getCoordinates(), playerId);
			newNodes.add(newNode);
		}
		// Add all the old nodes
		newNodes.addAll(this.getNodes());
		return new ImmutableBoard(newNodes);
	}
}
