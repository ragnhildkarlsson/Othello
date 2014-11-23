package kth.game.othello.simple.model;


//TODO Doc
public class Coordinates {
	private final int xCoordinate;
	private final int yCoordinate;

	public Coordinates(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public int getXCoordinate() {
		return xCoordinate;
	}

	public int getYCoordinate() {
		return xCoordinate;
	}

	@Override
	public boolean equals(Object otherXYCoord) {
		// TODO Implement
		return false;
	}

	@Override
	public int hashCode() {
		// TODO implement
		// Maybe helpful old implementation of id
		int idCode = xCoordinate << 16;
		idCode = idCode | yCoordinate;
		// this.nodeId = Integer.toString(idCode);

		return 0;
	}

	@Override
	public String toString() {
		return "{x=" + xCoordinate + ", y=" + yCoordinate + "}";

	}

}
