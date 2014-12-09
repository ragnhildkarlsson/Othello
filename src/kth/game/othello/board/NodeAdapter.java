package kth.game.othello.board;

import java.util.Observable;

import kth.game.othello.model.ImmutableNode;

/**
 * This class adapts the {@link kth.game.othello.model.ImmutableNode} to the
 * {@link kth.game.othello.board.Node} API.
 */
public class NodeAdapter extends Observable implements Node {

	private ImmutableNode nodeData;

	public NodeAdapter(ImmutableNode node) {
		this.nodeData = node;
	}

	/**
	 * Set the underlying immutable node.
	 *
	 * @param nodeData
	 *            the node to act as the underlying data.
	 */
	public void setNode(ImmutableNode nodeData) {
		if (!this.nodeData.getOccupantPlayerId().equals(nodeData.getOccupantPlayerId())) {
			this.setChanged();
		}
		String oldPlayerId = this.getOccupantPlayerId();
		this.nodeData = nodeData;
		this.notifyObservers(oldPlayerId);
	}

	/**
	 * The unique identifier of a node
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		int idInt = getXCoordinate();

		// 32-bit idInt:
		// 0000 0000 0000 0000 0000 0000 0000 0000
		// | Y goes here | X goes here |
		// Together they become some cryptic integer that does not tempt ppl to
		// use the id to infer coordinates.
		idInt = idInt | (getYCoordinate() << 16);

		return Integer.toString(idInt);

	}

	/**
	 * Get the id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	@Override
	public String getOccupantPlayerId() {
		if (nodeData.getOccupantPlayerId().isPresent()) {
			return nodeData.getOccupantPlayerId().get();
		}
		return null;
	}

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
		return nodeData.getCoordinates().getX();
	}

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
		return nodeData.getCoordinates().getY();
	}

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	@Override
	public boolean isMarked() {
		return nodeData.isMarked();
	}

}
