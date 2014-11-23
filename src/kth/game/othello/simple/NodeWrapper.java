package kth.game.othello.simple;

import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;

/**
 * // TODO
 */
public class NodeWrapper extends Observable implements Node {

	private String occupantPlayerId;
	private int x;
	private int y;
	private final String id;

	public NodeWrapper(int x, int y, String occupantPlayerId) {
		// TODO Implement
		id = null;
	}

	/**
	 * 
	 * @param id
	 */
	protected void setOccupantPlayerId(String id) {
		occupantPlayerId = id;
		// TODO notify
	}

	/**
	 * Adds an observer to this node.
	 * 
	 * @param observer
	 *            an observer of this node
	 */
	@Override
	public void addObserver(Observer observer) {

	}

	/**
	 * The unique identifier of a node
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return null;
	}

	/**
	 * To get the player id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	@Override
	public String getOccupantPlayerId() {
		return null;
	}

	/**
	 * The x-coordinate of this node
	 * 
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
		return 0;
	}

	/**
	 * The y-coordinate of this node
	 * 
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
		return 0;
	}

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	@Override
	public boolean isMarked() {
		return false;
	}

}
