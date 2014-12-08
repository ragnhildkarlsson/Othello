package kth.game.othello.model;

/**
 * Holds a set of immutable x and y coordinates in a cartesian system.
 */
public final class Coordinates {
	private final int xCoordinate;
	private final int yCoordinate;

	/**
	 * Create a new pair of coordinates.
	 * 
	 * @param xCoordinate
	 *            the x coordinate.
	 * @param yCoordinate
	 *            the y coordinate.
	 */
	public Coordinates(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @return the x coordinate.
	 */
	public int getX() {
		return xCoordinate;
	}

	/**
	 * @return the y coordinate.
	 */
	public int getY() {
		return yCoordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
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
		Coordinates other = (Coordinates) obj;
		if (xCoordinate != other.xCoordinate)
			return false;
		if (yCoordinate != other.yCoordinate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{x=" + xCoordinate + ", y=" + yCoordinate + "}";

	}

}
