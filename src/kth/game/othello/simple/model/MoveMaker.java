package kth.game.othello.simple.model;

import java.util.List;
import java.util.Set;

/**
 * TODO
 */
public class MoveMaker {

	private final List<String> playerIds;
	private Rules rules;
	private int playerInTurn;
	private ImmutableBoard board;

	public MoveMaker(ImmutableBoard startBoard, String startPlayerId, List<String> playerIds, Rules rules) {
		this.playerIds = playerIds;
		this.rules = rules;
		this.board = startBoard;
		// TODO set start player index
		// this.playerInTurn = startPlayerId;
	}

	/**
	 * Get the id of the player in turn or null if no player can move
	 * 
	 * @return the id of the player in turn or null if no player can move;
	 */
	public String getPlayerInTurn() {
		// TODO implement or move this functionality to a turnhandler;
		return null;
	}

	/**
	 * Determines if a player has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId) {
		// TODO implement
		return false;
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param node
	 *            the coordinates of the node where the player wants to play
	 * @return true if the move is valid
	 */
	public boolean isMoveValid(String playerId, Coordinates nodeCoordinates) {
		ImmutableNode playAtNode = board.getNodeAtCoordinates(nodeCoordinates);
		return rules.validMove(board, playAtNode, playerId);

	}

	/**
	 * Returns a set with Nodes that will be swapped to belong to the player
	 * with the given id if he/she play at a node with the given coordinates
	 * 
	 * @param nodeCoordinates
	 *            coordinates of the node where the player wants to play
	 * @param playerId
	 *            the id of the player that want to play
	 * @return Returns a set with Nodes that will be swapped to belong to the
	 *         player with the given id if he/she play at a node with the given
	 *         coordinates
	 */
	public Set<ImmutableNode> getNodesToSwap(Coordinates nodeCoordinates, String playerId) {
		// TODO implement
		return null;
	}

	/**
	 * Determines if the game is active or over
	 * 
	 * @return false if the game is over
	 */
	public boolean isActive() {
		// TODO Implement check if any of the players can make a move and return
		// result;
		return false;
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so,
	 * then the move is made which updates the board and the player in turn.
	 * 
	 * @param playerId
	 *            the id of the player that makes the move
	 * @param nodeCoordinates
	 *            the coordinates of the node where the player wants to move
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public Set<ImmutableNode> makeMove(String playerId, Coordinates nodeCoordinates) {
		// TODO
		// Check if move is valid and it is the player in turn
		// If so update the board and send a set with node that have changed
		// Update playerInTurn to NextPlayer
		// OBS Remember to check after move if next player can make a move, else
		// should playerInTurn be set to the next player that can make a move.
		return null;
	}

}
