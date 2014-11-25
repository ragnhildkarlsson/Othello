package kth.game.othello.simple;

import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;

/**
 * This class implements an outer, mutable view of the current state of a node on an Othello board.
 */
public class NodeAPIView extends Observable implements Node {

	private String occupantPlayerId;
	private int x;
	private int y;

	public NodeAPIView(int x, int y, String occupantPlayerId) {
		this.x = x;
        this.y = y;
		this.occupantPlayerId = occupantPlayerId;
	}

	/**
	 * TODO
     *
	 * @param id
	 */
	protected void setOccupantPlayerId(String id) {
		occupantPlayerId = id;
        this.setChanged();
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
		return occupantPlayerId;
	}

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
		return x;
	}

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
		return y;
	}

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	@Override
	public boolean isMarked() {
		return occupantPlayerId != null;
	}

}
