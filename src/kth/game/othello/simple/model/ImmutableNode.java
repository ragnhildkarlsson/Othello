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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableNode other = (ImmutableNode) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		return true;
	}

}
