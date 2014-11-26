package kth.game.othello.simple.model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a model of an Othello game. It is responsible for all
 * interaction with the model.
 * 
 */
public class GameModel {

	private GameState gameState;

	/**
	 * Create a new GameModel.
	 * 
	 * @param rules
	 *            the rules of the game.
	 * 
	 * @param turnKeeper
	 *            the turn keeper of the game.
	 * 
	 * @param ImmutableBoard
	 *            the starting of the game.
	 */
	protected GameModel(GameState startState) {
		this.gameState = startState;
	}

	/**
	 * Get the id of the player in turn or null if no player can move.
	 * 
	 * @return the id of the player in turn
	 */
	public String getPlayerInTurn() {
		return gameState.getPlayerInTurn();
	}

	/**
	 * Returns the present GameState in the model
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Determines if the player with given id has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player with the given id has a valid move.
	 */
	public boolean hasValidMove(String playerId) {
		return gameState.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over.
	 * 
	 * @return false if the game is over.
	 */
	public boolean isGameOver() {
		return gameState.isGameOver();
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId
	 *            the id of the player making the move.
	 * @param nodeCoordinates
	 *            the coordinates of the node where the player wants to play.
	 * @return true if the move is valid.
	 */
	public boolean isMoveValid(String playerId, Coordinates nodeCoordinates) {
		return gameState.isMoveValid(playerId, nodeCoordinates);
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so,
	 * then the move is made which updates the board and the player in turn.
	 * 
	 * @param playerId
	 *            the id of the player that makes the move
	 * @param nodeCoordinate
	 *            the coordinates of the node where the player wants to move
	 * @return the coordinates of the nodes that where swapped, including the
	 *         coordinates of the node where the player made the move;
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public Set<Coordinates> move(String playerId, Coordinates nodeCoordinate) throws IllegalArgumentException {

		// Optional<GameState> newGameState = gameState.tryMove(playerId,
		// nodeCoordinates);
		// TODO
		// // Check that this player is in turn
		//
		// ImmutableNode nodeToPlay =
		// board.getNodeAtCoordinates(nodeCoordinate);
		// if (!turnKeeper.getPlayerInTurn(rules, board).equals(playerId)) {
		// throw new
		// IllegalArgumentException("Trying to make a move with a player not in turn");
		// }
		// // Check that the move is valid
		// if (!rules.validMove(board, nodeToPlay, playerId)) {
		// throw new IllegalArgumentException("Trying to make unvalid move");
		// }
		// // Get the nodes that will be swapped
		// Set<ImmutableNode> nodesToSwap = rules.getNodesToSwap(board,
		// nodeToPlay, playerId);
		//
		// // perform the move
		// board = board.swapNodes(nodesToSwap, playerId);
		//
		// // go to the next turn
		// turnKeeper.nextTurn();
		//
		// return getSetOfCoordinatesFromNodes(nodesToSwap);
		return null;
	}

	private Set<Coordinates> getSetOfCoordinatesFromNodes(Set<ImmutableNode> nodes) {
		Set<Coordinates> coordinates = new HashSet<Coordinates>();
		for (ImmutableNode immutableNode : nodes) {
			coordinates.add(immutableNode.getCoordinates());
		}
		return coordinates;
	}
}
