package kth.game.othello;

import java.util.*;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.GameModel;
import kth.game.othello.model.GameModelFactory;
import kth.game.othello.player.Player;
import kth.game.othello.rules.RulesAdapter;
import kth.game.othello.score.Score;

/**
 * This class provides a facade that implements the
 * {@link kth.game.othello.Othello} API. Use it to play Othello.
 */
public class SimpleOthello implements Othello {

	private final String id;
	private final BoardAdapter boardAdapter;
	private final RulesAdapter rulesAdapter;
	private final MoveCoordinator moveCoordinator;
	private final Score score;
	private final Map<String, Player> playerMap = new HashMap<>();

	/**
	 * Creates a new SimpleOthello game. Assumes the GameModelFactory to always
	 * produce game models with the board that the given board adapter is
	 * pre-configured with.
	 *
	 * @param board
	 *            the board adaptor that will be used.
	 * @param score
	 *            the score object that should keep track of the score.
	 * @param rules
	 *            the rules to be used for the game.
	 * @param moveCoordinator
	 *            the moveCoordinator to be used for the game.
	 */
	protected SimpleOthello(String id, BoardAdapter board,
			Score score, RulesAdapter rules, MoveCoordinator moveCoordinator) {

		this.id = id;
		this.score = score;
		this.rulesAdapter = rules;
		this.moveCoordinator = moveCoordinator;
		this.boardAdapter = board;

	}

	/**
	 * Adds an observer. The observer will be called when the game has finished.
	 *
	 * @param observer
	 *            the observer
	 */
	@Override
	public void addGameFinishedObserver(Observer observer) {
	    this.moveCoordinator.addGameFinishedObserver(observer);
	}

	/**
	 * Adds an observer. The observers update will be called when a move has
	 * finished including the nodes that have changed by the move.
	 *
	 * @param observer
	 *            the observer
	 */
	@Override
	public void addMoveObserver(Observer observer) {
		this.moveCoordinator.addMoveObserver(observer);
	}

	/**
	 * The board on which the game is played.
	 * 
	 * @return the state of the board
	 */
	@Override
	public Board getBoard() {
		return boardAdapter;
	}

	/**
	 * @return a unique id in the context of all Othello games.
	 */
	@Override
	public String getId() {
		return id;
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
		return rulesAdapter.getNodesToSwap(playerId, nodeId);
	}

	/**
	 * Get the player in turn or null if no player can move
	 * 
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		return moveCoordinator.getPlayerInTurn().orElse(null);
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 * 
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		return new ArrayList<>(playerMap.values());
	}

	/**
	 * The score of the game
	 * 
	 * @return the score
	 */
	@Override
	public Score getScore() {
		return score;
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
		return rulesAdapter.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over
	 * 
	 * @return false if the game is over
	 */
	@Override
	public boolean isActive() {
		return !rulesAdapter.isGameOver();
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
		return rulesAdapter.isMoveValid(playerId, nodeId);
	}

	/**
	 * If the player in turn is a computer then this computer makes a move and
	 * updates the player in turn.
	 * 
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalStateException
	 *             if there is not a computer in turn
	 */
	@Override
	public List<Node> move() {
		return moveCoordinator.move();
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
	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return moveCoordinator.move(playerId, nodeId);
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {
		moveCoordinator.start();
	}

	/**
	 * Starts the game.
	 * 
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	@Override
	public void start(String playerId) {
		moveCoordinator.start(playerId);
	}

	/**
	 * Undo the last move.
	 */
	@Override
	public void undo() {
        moveCoordinator.undo();
	}

}
