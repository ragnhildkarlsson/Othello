package kth.game.othello.model;

import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This class represents a model of an Othello game. It is responsible for all
 * interaction with the model.
 * 
 */
public class GameModel {

	private GameState presentGameState;
	private Stack<GameState> history;

	/**
	 * Create a new GameModel.
	 * 
	 * @param startState
	 *            the starting GameState of the game.
	 */
	protected GameModel(GameState startState) {
		history = new Stack<GameState>();
		this.presentGameState = startState;
	}

	/**
	 * Get the id of the player in turn or null if no player can move.
	 * 
	 * @return the id of the player in turn
	 */
	public String getPlayerInTurn() {
		return presentGameState.getPlayerInTurn();
	}

	/**
	 * Returns the present GameState in the model
	 */
	public GameState getGameState() {
		return presentGameState;
	}

	/**
	 * Determines if the player with given id has any valid move.
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return true if the player with the given id has a valid move.
	 */
	public boolean hasValidMove(String playerId) {
		return presentGameState.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over.
	 * 
	 * @return false if the game is over.
	 */
	public boolean isGameOver() {
		return presentGameState.isGameOver();
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
		return presentGameState.isMoveValid(playerId, nodeCoordinates);
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

		Optional<GameState> maybeNewGameState = presentGameState.tryMove(playerId, nodeCoordinate);
		if (!maybeNewGameState.isPresent()) {
			throw new IllegalArgumentException();
		}

		GameState newGameState = maybeNewGameState.get();
		history.push(presentGameState);
		presentGameState = newGameState;

		return getSetOfCoordinatesFromNodes(newGameState.getBoard().getNodes());
	}

	private Set<Coordinates> getSetOfCoordinatesFromNodes(Set<ImmutableNode> nodes) {
		return nodes.stream().map(ImmutableNode::getCoordinates).collect(Collectors.toSet());
	}

	/**
	 * Undo the last move and returns an optional game state with the present
	 * game state after this action or an empty optional id no previous state
	 * exist.
	 * 
	 * @return returns an optional game state with the present game state after
	 *         this action or an empty optional id no previous state exist.
	 */
	public Optional<GameState> undo() {
		if (!history.isEmpty()) {
			presentGameState = history.pop();
			return Optional.of(presentGameState);

		}
		return Optional.empty();

	}
}
