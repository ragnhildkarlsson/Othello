package kth.game.othello.simple;

import java.util.*;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.simple.model.Coordinates;

/**
 * This class provides an outer view of the current state of an Othello board.
 */
public class BoardAPIView implements Board {

    private final Map<String, String> startingPlayerIdForNodeId = new HashMap<>();
	private List<NodeAPIView> nodeAPIViews;

	protected BoardAPIView(List<NodeAPIView> nodeAPIViews) {
        this.nodeAPIViews = nodeAPIViews;
        this.nodeAPIViews.sort(new NodeComparator());
        nodeAPIViews.stream()
            .filter(node -> node.getOccupantPlayerId() != null)
            .forEach(node -> startingPlayerIdForNodeId.put(node.getId(), node.getOccupantPlayerId()));
	}

    private class NodeComparator implements Comparator<Node> {

        /**
         * TODO
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
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
        return getMutableNode(x,y).orElse(null);
	}

    private Optional<NodeAPIView> getMutableNode(int x, int y) {
        Optional<NodeAPIView> maybeNode = nodeAPIViews.stream().filter(
            node -> (node.getXCoordinate() == x) && (node.getYCoordinate() == y)
        ).findAny();
        return maybeNode;
    }
    private Optional<NodeAPIView> getMutableNode(Coordinates coordinates) {
        return getMutableNode(coordinates.getXCoordinate(), coordinates.getYCoordinate());
    }

    /**
     * TODO
     * @param coordinateses
     * @param playerId
     */
    public List<Node> swapNodes(Collection<Coordinates> coordinateses, String playerId) {
        for (NodeAPIView  : )
        List<NodeAPIView> nodes = coordinateses.stream().map(coordinates -> getMutableNode(coordinates)).collect()
        coordinateses.stream().
            forEach(coordinates -> getMutableNode(coordinates).ifPresent(node -> node.setOccupantPlayerId(playerId)));
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
		Optional<NodeAPIView> maybeNode = nodeAPIViews.stream().filter(node -> node.getId().equals(nodeId)).findAny();
		return maybeNode.orElse(null);
	}

    /**
     * TODO
     */
    public void reset() {
        nodeAPIViews.stream()
            .forEach(node -> node.setOccupantPlayerId(startingPlayerIdForNodeId.get(node.getId())));
    }

}
