package kth.game.othello.board;

public class SimpleNode implements Node {
	/**
	 * The unique identifier of a node. A node should be identified uniquely
	 * given the x- and y-coordinate
	 *
	 * @return the id
	 */
	@Override
	public String getId() {
	    // TODO Method stub
        return null;
	}

	/**
	 * To get the player id of the occupant player
	 *
	 * @return the id of the occupant player or null if the node is not marked
	 */
	@Override
	public String getOccupantPlayerId() {
        // TODO Method stub
        return null;
    }

	/**
	 * The x-coordinate of this node
	 *
	 * @return the x-coordinate
	 */
	@Override
	public int getXCoordinate() {
        // TODO Method stub
        return null;
    }

	/**
	 * The y-coordinate of this node
	 *
	 * @return the y-coordinate
	 */
	@Override
	public int getYCoordinate() {
        // TODO Method stub
        return null;
    }

	/**
	 * Determines of the node is occupied by any player
	 *
	 * @return true if the node is occupied by any player
	 */
	@Override
	public boolean isMarked() {
        // TODO Method stub
        return null;
    }
}
