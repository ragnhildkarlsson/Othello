package kth.game.othello;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.Node;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.Player;

/**
 * This class represents a simple Othello game.
 *
 * @author
 */
public class SimpleOthello implements Othello {

	protected SimpleOthello(BoardFactory boardFactory, Player humanPlayer1, Player humanPlayer2) {

	}

	protected SimpleOthello(BoardFactory boardFactory, Player humanPlayer, ComputerPlayer computerPlayer) {

	}

	protected SimpleOthello(BoardFactory boardFactory, ComputerPlayer computerPlayer1, ComputerPlayer computerPlayer2) {

	}

	/**
	 * The board on which the game is played.
	 *
	 * @return the state of the board
	 */
	@Override
	public Board getBoard() {
		return null;
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
		return null;
	}

	/**
	 * Get the player in turn or null if no player can move
	 *
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		return null;
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 *
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		return null;
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
		return false;
	}

	/**
	 * Determines if the game is active or over
	 *
	 * @return false if the game is over
	 */
	@Override
	public boolean isActive() {
		return false;
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
		return false;
	}

	/**
	 * If the player in turn is a computer than this computer makes a move and
	 * updates the player in turn.
	 *
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalStateException
	 *             if there is not a computer in turn
	 */
	@Override
	public List<Node> move() {
		return null;
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
		return null;
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {

	}

	/**
	 * Starts the game.
	 *
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	@Override
	public void start(String playerId) {

	}
}
