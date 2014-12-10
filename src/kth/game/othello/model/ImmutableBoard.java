package kth.game.othello.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ImmutableBoard is responsible for keeping track of the nodes in the game
 * state, it is an immutable class.
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
	 * Construct a new ImmutableBoard given all the ImmutableNodes that can ever
	 * be played at on the board.
	 * 
	 * @param nodes
	 *            the nodes which the board should consist of.
	 */
	public ImmutableBoard(Set<ImmutableNode> nodes) {
		this.nodes = new HashMap<>();
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
		return new HashSet<>(this.nodes.values());
	}

	/**
	 * Return the coordinates of the nodes that differ between the two given
	 * boards.
	 * 
	 * @param board1
	 *            a board
	 * @param board2
	 *            another board that you want to compare with board1
	 * @return the coordinates of the nodes that differ between the two nodes.
	 */
	public static Set<Coordinates> compare(ImmutableBoard board1, ImmutableBoard board2) {
		Set<ImmutableNode> board1Nodes = board1.getNodes();
		Set<ImmutableNode> board2Nodes = board2.getNodes();
		board2Nodes.removeAll(board1Nodes);
		return board2Nodes.stream().map(ImmutableNode::getCoordinates).collect(Collectors.toSet());
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
		if (!this.hasCoordinates(coordinates)) {
			throw new IllegalArgumentException("Coordinates does not exist on board");
		}
		return nodes.get(coordinates);
	}

	/**
	 * Return an Optional with the next node in the given direction relative to
	 * the given node or an empty optional if no such node exists.
	 * 
	 * @param originNode
	 *            the node from which to get the next.
	 * @param direction
	 *            the direction in which the next node is to be fetched.
	 * @return Return an Optional with the next node in the given direction
	 *         relative to the given node or an empty optional if no such node
	 *         exists.
	 * @throws IllegalArgumentException
	 *             if the origin node is outside the board.
	 */
	public Optional<ImmutableNode> getNextNodeInDirection(ImmutableNode originNode, Direction direction)
			throws IllegalArgumentException {

		Coordinates originCoordinates = originNode.getCoordinates();
		int x = originCoordinates.getX();
		int y = originCoordinates.getY();
		if (!this.hasCoordinates(originCoordinates)) {
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

		if (!hasCoordinates(newCoordinates)) {
			return Optional.empty();
		} else {
			return Optional.of(getNodeAtCoordinates(newCoordinates));
		}

	}

	/**
	 * Returns true if the given coordinates exist on this board, else false.
	 * 
	 * @param coordinates
	 *            the coordinates to check if they are on the board.
	 * @return true if the coordinates exist on the board, else false.
	 */
	public boolean hasCoordinates(Coordinates coordinates) {
		return nodes.containsKey(coordinates);
	}

	public Set<String> getPlayerIDs() {
		HashSet<String> playerIDs = new HashSet<>();
		for (ImmutableNode node : this.nodes.values()) {
			node.getOccupantPlayerId().ifPresent(playerIDs::add);
		}
		return playerIDs;
	}

	@Override
	public String toString() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (ImmutableNode node : nodes.values()) {
			minX = Math.min(minX, node.getCoordinates().getX());
			maxX = Math.max(maxX, node.getCoordinates().getX());
			minY = Math.min(minY, node.getCoordinates().getY());
			maxY = Math.max(maxY, node.getCoordinates().getY());
		}

		Map<String, Character> playerSymbols = new HashMap<>();
		Set<String> players = getPlayerIDs();
		int minLength = players.stream().map(String::length)
				.reduce(Integer.MAX_VALUE, (min, current) -> Math.min(current, min));
		for (int i = 0; i < minLength; i++) {
			Set<Character> chars = new HashSet<>();
			for (String player : players) {
				chars.add(player.charAt(i));
			}
			if (chars.size() == players.size()) {
				final int goodIndex = i;
				players.stream().forEach(player -> playerSymbols.put(player, player.charAt(goodIndex)));
				break;
			}
		}
		if (playerSymbols.isEmpty()) {
			int i = 1;
			for (String player : players) {
				playerSymbols.put(player, Integer.toString(i++).charAt(0));
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				Coordinates coordinates = new Coordinates(x, y);
				if (nodes.containsKey(coordinates)) {
					Optional<String> occupantPlayerId = nodes.get(coordinates).getOccupantPlayerId();
					sb.append(occupantPlayerId.isPresent() ? playerSymbols.get(occupantPlayerId.get()) : "•");
				} else {
					sb.append(' ');
				}
				sb.append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	/**
	 * Create a new board from this board, with a given subset of the old nodes
	 * to be occupied by a given playerID in the new board. All other nodes
	 * remain in the same state.
	 * 
	 * @param nodesToSwap
	 *            nodes to get occupied by the given playerID.
	 * @param playerId
	 *            the playerID of the player to occupy the swapped nodes.
	 * @return a new board with the given set of nodes now occupied by the
	 *         chosen playerID.
	 */
	public ImmutableBoard swapNodes(Set<ImmutableNode> nodesToSwap, String playerId) {
		Set<ImmutableNode> newNodes = new HashSet<>();
		for (ImmutableNode node : nodesToSwap) {
			ImmutableNode newNode = new ImmutableNode(node.getCoordinates(), Optional.of(playerId));
			newNodes.add(newNode);
		}
		Set<Coordinates> newNodeCoordinates = getCoordinateSet(newNodes);
		// if this node has not been swapped, add it to new nodes
		newNodes.addAll(this.nodes.values().stream()
				.filter(oldNode -> !newNodeCoordinates.contains(oldNode.getCoordinates())).collect(Collectors.toList()));
		return new ImmutableBoard(newNodes);
	}

	private Set<Coordinates> getCoordinateSet(Set<ImmutableNode> nodes) {
		return nodes.stream().map(ImmutableNode::getCoordinates).collect(Collectors.toSet());
	}
}
