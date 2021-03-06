package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.model.ImmutableNode;

/**
 * This class adapts the {@link kth.game.othello.model.ImmutableBoard} class to
 * the {@link kth.game.othello.board.Board} API.
 */
public class BoardAdapter implements Board {

	private ImmutableBoard boardState;
	private List<NodeAdapter> nodeAdapters;
    private static boolean printChanges = false;

	/**
	 * Creates a new board adapter. The passed node adapters are assumed to be
	 * pre-configured with exactly every node in the passed starting board.
	 * 
	 * @param startingBoard
	 *            the board containing the initial state of the adapter.
	 * @param nodeAdapters
	 *            the node adapters of the board. Assumed to be pre-configured
	 *            with exactly every node in the passed starting board.
	 */
	public BoardAdapter(ImmutableBoard startingBoard, List<NodeAdapter> nodeAdapters) {
		boardState = startingBoard;
		this.nodeAdapters = nodeAdapters;
		this.nodeAdapters.sort(new NodeComparator());
	}

	@Override
	public int getMaxX() {
		Optional<NodeAdapter> node = nodeAdapters.stream().reduce(
				(n1, n2) -> n1.getXCoordinate() > n2.getXCoordinate() ? n1 : n2);
		if (!node.isPresent()) {
			throw new IllegalStateException();
		}
		return node.get().getXCoordinate();
	}

	@Override
	public int getMaxY() {
		Optional<NodeAdapter> node = nodeAdapters.stream().reduce(
				(n1, n2) -> n1.getYCoordinate() > n2.getYCoordinate() ? n1 : n2);
		if (!node.isPresent()) {
			throw new IllegalStateException();
		}
		return node.get().getYCoordinate();

	}

	@Override
	public boolean hasNode(int x, int y) {
		Optional<NodeAdapter> maybeNode = getNodeAdapter(new Coordinates(x, y));
		if (maybeNode.isPresent()) {
			return true;
		}
		return false;
	}

	private class NodeComparator implements Comparator<Node> {

		/**
		 * Sorts Nodes with respect to y first and then x.. For example (x,y):
		 * (4,3) (5,3) (1,4) (2,4)
		 *
		 * @param node1
		 *            the first object to be compared.
		 * @param node2
		 *            the second object to be compared.
		 * @return a negative integer, zero, or a positive integer as the first
		 *         argument is less than, equal to, or greater than the second.
		 * @throws NullPointerException
		 *             if an argument is null and this comparator does not
		 *             permit null arguments
		 */
		@Override
		public int compare(Node node1, Node node2) {
			if (node1.getYCoordinate() < node2.getYCoordinate()) {
				return -1;
			} else if (node1.getYCoordinate() > node2.getYCoordinate()) {
				return 1;
			} else {
				// Equal y coordinates
				if (node1.getXCoordinate() < node2.getXCoordinate()) {
					return -1;
				} else if (node1.getXCoordinate() > node2.getXCoordinate()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * Returns the node with the given x- and y-coordinate
	 * 
	 * @param x
	 *            the x-coordinate of the node
	 * @param y
	 *            the y-coordinate of the node
	 * @return the node with given x- and y-coordinate
	 * @throws IllegalArgumentException
	 *             if there is no {@link kth.game.othello.board.Node} having the
	 *             specific x- and y-coordinate
	 */
	@Override
	public Node getNode(int x, int y) {
		return getNodeAdapter(x, y).orElseThrow(
				() -> new IllegalArgumentException("There is no node with coordinates (" + x + "," + y + ")"));
	}

	/**
	 * Returns the node with the given x- and y-coordinate
	 *
	 * @param coordinates
	 *            the coordinates of the node
	 * @return the node with given coordinates
	 * @throws IllegalArgumentException
	 *             if there is no {@link kth.game.othello.board.Node} having the
	 *             specific x- and y-coordinate
	 */
	public Node getNode(Coordinates coordinates) {
		return getNode(coordinates.getX(), coordinates.getY());
	}

	private Optional<NodeAdapter> getNodeAdapter(int x, int y) {
		return nodeAdapters.stream().filter(node -> (node.getXCoordinate() == x) && (node.getYCoordinate() == y))
				.findAny();
	}

	private Optional<NodeAdapter> getNodeAdapter(Coordinates coordinates) {
		return getNodeAdapter(coordinates.getX(), coordinates.getY());
	}

	/**
	 * Set the state of the board.
	 *
	 * @param newBoardState
	 *            the new state of the board.
	 * @return Returns the nodes changed in the new board state.
	 */
	public List<Node> setBoardState(ImmutableBoard newBoardState) {

		Set<Coordinates> changedCoordinates = ImmutableBoard.compare(boardState, newBoardState);
		Set<ImmutableNode> newNodes = changedCoordinates.stream().map(newBoardState::getNodeAtCoordinates)
				.collect(Collectors.toSet());

		List<Node> changedNodeAdapters = new ArrayList<>();

		for (ImmutableNode node : newNodes) {
			Optional<NodeAdapter> maybeNodeAdapter = getNodeAdapter(node.getCoordinates());
			maybeNodeAdapter.ifPresent(nodeAdapter -> nodeAdapter.setNode(node));
			maybeNodeAdapter.ifPresent(changedNodeAdapters::add);
		}

		this.boardState = newBoardState;

        if (this.printChanges) {
            System.out.println(newBoardState);
        }

		return changedNodeAdapters;
	}

	/**
	 * Returns an ordered list of rows using the natural order in x- and then
	 * y-coordinate of the nodes.
	 * 
	 * @return the nodes of the board
	 */
	@Override
	public List<Node> getNodes() {
		List<Node> nodesCopy = new ArrayList<>();
		nodesCopy.addAll(nodeAdapters);
		return nodesCopy;
	}

	/**
	 * Returns the distinct Node with the given node id.
	 *
	 * @param nodeId
	 *            the node id of the node to fetch.
	 * @return the Node with the given id.
	 */
	public Node getNodeById(String nodeId) {
		Optional<NodeAdapter> maybeNode = nodeAdapters.stream().filter(node -> node.getId().equals(nodeId)).findAny();
		return maybeNode.orElseThrow(() -> new NoSuchElementException("Node id \"" + nodeId + "\" does not exist."));
	}

	/**
	 * @return an immutable version of the current board.
	 */
	public ImmutableBoard getImmutableBoard() {
		return this.boardState;
	}

    @Override
    public String toString() {
        return boardState.toString();
    }
}
