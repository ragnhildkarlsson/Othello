package kth.game.othello.notification;

import java.util.List;
import java.util.Observable;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * Stub delegation of all Othello functionality but observation. This class and
 * any of its children will have circular dependency with
 * {@link kth.game.othello.SimpleOthello} and may therefore be considered a part
 * of its implementation, existing only to get around the incorrect use of the
 * Observer type in the API.
 */
public abstract class Notifier extends Observable implements Othello {
	private Othello underlyingOthello;

	protected Othello getUnderlyingOthello() {
		assert (underlyingOthello != null) : "The move notifier must be connected to the othello before it can be used.";
		return underlyingOthello;
	}

	/**
	 * Set the othello to which the notifier is connected. This has to be set
	 * before the notifier can be used and can only be called once.
	 *
	 * @param underlyingOthello
	 *            the othello to which the notifier is connected.
	 */
	public void initiateUnderlyingOthello(Othello underlyingOthello) {
		this.underlyingOthello = underlyingOthello;
	}

	/**
	 * The board on which the game is played.
	 *
	 * @return the state of the board
	 */
	@Override
	public Board getBoard() {
		return underlyingOthello.getBoard();
	}

	/**
	 * @return a unique id in the context of all Othello games.
	 */
	@Override
	public String getId() {
		return underlyingOthello.getId();
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 *
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the id of the node where the move is made
	 * @return the list of nodes that will be swapped for the given move
	 */
	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return underlyingOthello.getNodesToSwap(playerId, nodeId);
	}

	/**
	 * Get the player in turn or null if no player can move
	 *
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		return underlyingOthello.getPlayerInTurn();
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 *
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		return underlyingOthello.getPlayers();
	}

	/**
	 * The score of the game
	 *
	 * @return the score
	 */
	@Override
	public Score getScore() {
		return underlyingOthello.getScore();
	}

	/**
	 * Determines if a player has any valid move.
	 *
	 * @param playerId
	 *            the id of the player
	 * @return true if the player has a valid move
	 */
	@Override
	public boolean hasValidMove(String playerId) {
		return underlyingOthello.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over
	 *
	 * @return false if the game is over
	 */
	@Override
	public boolean isActive() {
		return underlyingOthello.isActive();
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 *
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the node id where the player wants to play
	 * @return true if the move is valid
	 */
	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return underlyingOthello.isMoveValid(playerId, nodeId);
	}

	/**
	 * If the player in turn is a computer than this computer makes a move and
	 * updates the player in turn. All observers will be notified with the
	 * additional argument being the list of nodes that were swapped.
	 *
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalStateException
	 *             if there is not a computer in turn
	 */
	@Override
	public List<Node> move() {
		return underlyingOthello.move();
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so,
	 * then the move is made which updates the board and the player in turn. All
	 * observers will be notified with the additional argument being the list of
	 * nodes that were swapped.
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
	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return underlyingOthello.move(playerId, nodeId);
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {
		underlyingOthello.start();
	}

	/**
	 * Starts the game.
	 *
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	@Override
	public void start(String playerId) {
		underlyingOthello.start(playerId);
	}

	/**
	 * Undo the last move.
	 */
	@Override
	public void undo() {
		underlyingOthello.undo();
	}
}
