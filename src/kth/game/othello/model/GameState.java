package kth.game.othello.model;

import java.util.Optional;
import java.util.Set;

import kth.game.othello.board.Coordinates;

public class GameState {

	private TurnCalculator turnCalculator;
	private ModelRules rules;
	private ImmutableBoard board;
	private Optional<String> playerInTurn;

	/**
	 * Creates a new game state with the given board and TurnCalculator. The
	 * player in turn for the game state will be the player with the
	 * startPlayerId if this player has a valid move, else the next player that
	 * has a valid move will be the player in turn. If no player has a valid
	 * move or startPlayerId is an empty optional the player in turn will be an
	 * empty optional.
	 * 
	 * @param board
	 *            the board of this game state
	 * @param turnCalculator
	 *            the turnCalculator of this game state
	 * @param rules
	 *            the rules of this game
	 * @param startPlayerId
	 *            the id of the wanted player in turn in this game state.
	 */
	public GameState(ImmutableBoard board, TurnCalculator turnCalculator, ModelRules rules,
			Optional<String> startPlayerId) {
		this.board = board;
		this.turnCalculator = turnCalculator;
		this.rules = rules;
		if (startPlayerId.isPresent()) {
			if (rules.hasValidMove(board, startPlayerId.get())) {
				playerInTurn = startPlayerId;
			} else {
				playerInTurn = turnCalculator.getPlayerInTurn(startPlayerId.get(), board, rules);
			}
		} else {
			playerInTurn = startPlayerId;
		}
	}

	/**
	 * Return the board of the gameState
	 */
	public ImmutableBoard getBoard() {
		return board;
	}

	/**
	 * Get an Optional with the id of the player in turn or an empty optional if
	 * no player can move.
	 * 
	 * @return the id of the player in turn or null if no player can move
	 */
	public Optional<String> getPlayerInTurn() {
		return playerInTurn;
	}

	/**
	 * Determines if the player with given id has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player with the given id has a valid move.
	 */
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(board, playerId);
	}

	/**
	 * Determines if the game is active or over.
	 * 
	 * @return false if the game is over.
	 */
	public boolean isGameOver() {
		return rules.isGameOver(board);
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
		return rules.validMove(board, nodeCoordinates, playerId);
	}

	/**
	 * If the given player is the player in turn and the move is valid, an
	 * optional of the resulting gameState is returned or otherwise an empty
	 * optional.
	 * 
	 * @param playerId
	 *            id of the player that do the move
	 * @param nodeCoordinates
	 *            The coordinates of the node where the player want to do the
	 *            move
	 * @return If the given player is the player in turn and the move is valid,
	 *         an optional of the resulting gameState is returned otherwise an
	 *         empty optional.
	 */
	public Optional<GameState> tryMove(String playerId, Coordinates nodeCoordinates) {

		if (!playerInTurn.isPresent()) {
			return Optional.empty();
		}
		if (!playerId.equals(playerInTurn.get())) {
			return Optional.empty();
		}
		// That the player is the player in turn implies that this player has a
		// valid move therefore no need to check that.
		Set<ImmutableNode> nodesToSwap = rules.getNodesToSwap(board, nodeCoordinates, playerId);
		nodesToSwap.add(new ImmutableNode(nodeCoordinates, Optional.of(playerId)));
		// Add the node played at
		ImmutableBoard newBoard = board.swapNodes(nodesToSwap, playerId);
		Optional<String> nextPlayerInTurn = turnCalculator.getPlayerInTurn(playerId, newBoard, rules);
		GameState nextGameState = new GameState(newBoard, turnCalculator, rules, nextPlayerInTurn);
		return Optional.of(nextGameState);
	}
}
