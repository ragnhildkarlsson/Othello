package kth.game.othello.model;

import java.util.Optional;

/**
 * Holds information about the position and occupant player of a node on a
 * board.
 */
public final class ImmutableNode {

	private final Optional<String> occupantPlayerId;
	private final Coordinates coordinates;

	/**
	 * Construct an immutable node given its coordinates and occupying player
	 * ID.
	 * 
	 * @param playerId
	 *            the player occupying the node. Empty optional if the node is
	 *            unmarked.
	 */
	public ImmutableNode(Coordinates coordinates, Optional<String> playerId) {
		this.coordinates = coordinates;
		this.occupantPlayerId = playerId;
	}

	/**
	 * Get the player id of the occupant player
	 * 
	 * @return An optional with the id of the occupant player or an empty
	 *         optional if the node is not marked
	 */
	public Optional<String> getOccupantPlayerId() {
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
		return occupantPlayerId.isPresent();
	}

	@Override
	public String toString() {
		return "ImmutableNode{" + ", occupantPlayerId='" + occupantPlayerId + '\'' + ", x=" + coordinates.getX()
				+ ", y=" + coordinates.getY() + '}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result + ((occupantPlayerId == null) ? 0 : occupantPlayerId.hashCode());
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
		if (occupantPlayerId == null) {
			if (other.occupantPlayerId != null)
				return false;
		} else if (!occupantPlayerId.equals(other.occupantPlayerId))
			return false;
		return true;
	}

}
