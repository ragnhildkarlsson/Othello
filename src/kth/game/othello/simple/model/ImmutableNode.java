package kth.game.othello.simple.model;


/**
 * TODO write class doc here
 * 
 */
public final class ImmutableNode {

	private final String occupantPlayerId;
	private final Coordinates coordinates;

	/**
	 * Construct an immutable node given its coordinates and an optional player
	 * ID. TODO correct
	 * 
	 * @param playerId
	 *            optional, set to null if the node is unmarked.
	 */
	public ImmutableNode(Coordinates coordinates, String playerId) {
		this.coordinates = coordinates;
		this.occupantPlayerId = playerId;
	}

	/**
	 * To get the player id of the occupant player
	 * 
	 * @return the id of the occupant player or null if the node is not marked
	 */
	public String getOccupantPlayerId() {
		return this.occupantPlayerId;
	}

	/**
	 * Returns the coordinates of the node
	 * 
	 * @return Returns the coordinates of the node
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Determines of the node is occupied by any player
	 * 
	 * @return true if the node is occupied by any player
	 */
	public boolean isMarked() {
		if (occupantPlayerId != null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ImmutableNode{" + ", occupantPlayerId='" + occupantPlayerId + '\'' + ", x="
				+ coordinates.getXCoordinate() + ", y=" + coordinates.getYCoordinate() + '}';
	}
}
