package kth.game.othello.simple;

import java.util.Observable;

import kth.game.othello.board.Node;
import kth.game.othello.simple.model.ImmutableNode;

/**
 * This class implements an outer, mutable view of the current state of a node on an Othello board.
 */
public class NodeAdapter extends Observable implements Node {

	private ImmutableNode nodeData;

	public NodeAdapter(ImmutableNode node) {
		this.nodeData = node;
	}

	/**
	 * Set the underlying immutable node.
     *
	 * @param nodeData the node to act as the underlying data.
	 */
	protected void setNode(ImmutableNode nodeData) {
		if (this.nodeData.getOccupantPlayerId() != nodeData.getOccupantPlayerId()) {
            this.setChanged();
        }
        this.nodeData = nodeData;
        this.notifyObservers();
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
        // | Y goes here      | X goes here      |
        // Together they become some cryptic integer that does not tempt ppl to use the id to infer coordinates.
        idInt = idInt | (getYCoordinate() << 16);

        String id = Integer.toString(idInt);

		return id;

	}

	/**
	 * To get the player id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	@Override
	public String getOccupantPlayerId() {
		return nodeData.getOccupantPlayerId();
	}

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
		return nodeData.getCoordinates().getXCoordinate();
	}

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
		return nodeData.getCoordinates().getYCoordinate();
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
