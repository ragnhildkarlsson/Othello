package kth.game.othello.simple.model;

import java.util.Set;

/**
 * This class is responsible for ... TODO
 * 
 */
public class GameModel {

	private MoveMaker moveMaker;

	/**
	 * TODO
	 */
	protected GameModel(MoveMaker moveMaker) {
		this.moveMaker = moveMaker;
	}

	/**
	 * Returns a set with nodes that will be swapped to belong to the player
	 * with the given id if he/she play at a node with the given coordinates;
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeCoordinates
	 *            the coordinates of the node where the move is made
	 * @return Returns a set with nodes that will be swapped if the player with
	 *         the given id play at a node with the given coordinates.
	 */
	public Set<ImmutableNode> getNodesToSwap(String playerId, Coordinates nodeCoordinates) {
		return moveMaker.getNodesToSwap(nodeCoordinates, playerId);
	}

	/**
	 * Get the id of the player in turn or null if no player can move
	 * 
	 * @return the id of the player in turn
	 */
	public String getPlayerInTurn() {
		return moveMaker.getPlayerInTurn();
	}

	/**
	 * Determines if the player with given id has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player with the given id has a valid move
	 */
	public boolean hasValidMove(String playerId) {
		return moveMaker.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over
	 * 
	 * @return false if the game is over
	 */
	public boolean isActive() {
		return moveMaker.isActive();
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeCoordinates
	 *            the coordinates of the node where the player wants to play
	 * @return true if the move is valid
	 */
	public boolean isMoveValid(String playerId, Coordinates nodeCoordinates) {
		return moveMaker.isMoveValid(playerId, nodeCoordinates);
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so,
	 * then the move is made which updates the board and the player in turn.
	 * 
	 * @param playerId
	 *            the id of the player that makes the move
	 * @param nodeId
	 *            the id of the node where the player wants to move
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public Set<ImmutableNode> move(String playerId, Coordinates nodeCoordinates) throws IllegalArgumentException {
		return moveMaker.makeMove(playerId, nodeCoordinates);
	}
}
