package kth.game.othello.simple;

import java.util.*;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.simple.model.Coordinates;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableNode;

/**
 * This class provides an outer view of the current state of an Othello board.
 */
public class BoardAdapter implements Board {

	private ImmutableBoard boardState;
	private final ImmutableBoard startingBoard;
	private List<NodeAdapter> nodeAPIViews;

	protected BoardAdapter(ImmutableBoard startingBoard, List<NodeAdapter> nodeAPIViews) {
		this.startingBoard = startingBoard;
		this.nodeAPIViews = nodeAPIViews;

		assert nodeAPIViews.size() == startingBoard.getNodes().size() : "Wrong number of NodeAPIViews: "
				+ nodeAPIViews.size() + " != " + startingBoard.getNodes().size();
		Iterator<ImmutableNode> nodeIterator = startingBoard.getNodes().iterator();
		Iterator<NodeAdapter> nodeViewIterator = nodeAPIViews.iterator();
		while (nodeIterator.hasNext() && nodeViewIterator.hasNext()) {
			nodeViewIterator.next().setNode(nodeIterator.next());
		}
		this.nodeAPIViews.sort(new NodeComparator());
		boardState = startingBoard;
	}

	private class NodeComparator implements Comparator<Node> {

		/**
		 * TODO
		 *
		 * @param o1
		 *            the first object to be compared.
		 * @param o2
		 *            the second object to be compared.
		 * @return a negative integer, zero, or a positive integer as the first
		 *         argument is less than, equal to, or greater than the second.
		 * @throws NullPointerException
		 *             if an argument is null and this comparator does not
		 *             permit null arguments
		 * @throws ClassCastException
		 *             if the arguments' types prevent them from being compared
		 *             by this comparator.
		 */
		@Override
		public int compare(Node o1, Node o2) {
			if (o1.getYCoordinate() < o2.getYCoordinate()) {
				return -1;
			} else if (o1.getYCoordinate() > o2.getYCoordinate()) {
				return 1;
			} else {
				// Equal y coordinates
				if (o1.getXCoordinate() < o2.getXCoordinate()) {
					return -1;
				} else if (o1.getXCoordinate() > o2.getXCoordinate()) {
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
		return getMutableNode(x, y).orElse(null);
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
		return getNode(coordinates.getXCoordinate(), coordinates.getYCoordinate());
	}

	private Optional<NodeAdapter> getMutableNode(int x, int y) {
		Optional<NodeAdapter> maybeNode = nodeAPIViews.stream()
				.filter(node -> (node.getXCoordinate() == x) && (node.getYCoordinate() == y)).findAny();
		return maybeNode;
	}

	private Optional<NodeAdapter> getMutableNode(Coordinates coordinates) {
		return getMutableNode(coordinates.getXCoordinate(), coordinates.getYCoordinate());
	}

	/**
	 * Set the state of the board.
	 *
	 * @param newBoardState
	 *            the new state of the board.
	 * @return Returns the nodes changed in the new board state.
	 */
	public List<Node> setBoardState(ImmutableBoard newBoardState) {

		Set<ImmutableNode> oldNodes = boardState.getNodes();
		Set<ImmutableNode> updatedNodes = newBoardState.getNodes();
		updatedNodes.removeAll(oldNodes);

		List<Node> changedNodeAdapters = new ArrayList<>();

		for (ImmutableNode node : updatedNodes) {
			Optional<NodeAdapter> maybeNodeAdapter = getMutableNode(node.getCoordinates());
			maybeNodeAdapter.ifPresent(nodeAdapter -> nodeAdapter.setNode(node));
			maybeNodeAdapter.ifPresent(nodeAdapter -> changedNodeAdapters.add(nodeAdapter));
		}

		this.boardState = newBoardState;
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
		nodesCopy.addAll(nodeAPIViews);
		return nodesCopy;
	}

	/**
	 * TODO return null if node with given Id does not exist on board;
	 *
	 * @param nodeId
	 * @return
	 */
	public Node getNodeById(String nodeId) {
		Optional<NodeAdapter> maybeNode = nodeAPIViews.stream().filter(node -> node.getId().equals(nodeId)).findAny();
		return maybeNode.orElse(null);
	}

}
