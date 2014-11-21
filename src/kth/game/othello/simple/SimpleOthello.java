package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.simple.board.BoardFactory;
import kth.game.othello.simple.board.ImmutableBoard;

/**
 * This class represents a simple Othello game.
 * 
 * @author Daniel Schlaug
 */
public class SimpleOthello implements Othello {

	private final int nPlayers = 2;
	private final BoardFactory boardFactory;
	private final List<Player> players = new ArrayList<>();

	private ImmutableBoard board;
	private SimpleRules rules;

	private int playerInTurn = 0;

	/**
	 * Creates an Othello game between two computers.
	 * 
	 * @param boardFactory
	 *            the factory that will be used to create the boards in the
	 *            game.
	 * @param rules
	 *            the rules for the game.
	 * @param computer
	 *            the first computer player.
	 * @param computer2
	 *            the second computer player.
	 */
	protected SimpleOthello(BoardFactory boardFactory, SimpleRules rules, ComputerPlayer computer,
			ComputerPlayer computer2) {
		this(boardFactory, rules, (Player) computer, (Player) computer2);
	}

	/**
	 * Creates an Othello game between a human and computer player.
	 * 
	 * @param boardFactory
	 *            the factory that will be used to create the boards in the
	 *            game.
	 * @param rules
	 *            the rules for the game.
	 * @param computer
	 *            the computer player.
	 * @param human
	 *            the human player.
	 */
	protected SimpleOthello(BoardFactory boardFactory, SimpleRules rules, HumanPlayer human, ComputerPlayer computer) {
		this(boardFactory, rules, (Player) computer, (Player) human);
	}

	/**
	 * Creates an Othello game between two human players.
	 * 
	 * @param boardFactory
	 *            the factory that will be used to create the boards in the
	 *            game.
	 * @param rules
	 *            the rules for the game.
	 * @param human
	 *            the first human player.
	 * @param human2
	 *            the second human player.
	 */
	protected SimpleOthello(BoardFactory boardFactory, SimpleRules rules, HumanPlayer human, HumanPlayer human2) {
		this(boardFactory, rules, (Player) human, (Player) human2);
	}

	// This constructor should be called by all the above.
	private SimpleOthello(BoardFactory boardFactory, SimpleRules rules, Player startingPlayer, Player secondPlayer) {
		players.add(startingPlayer);
		players.add(secondPlayer);
		this.rules = rules;
		this.boardFactory = boardFactory;
	}

	/**
	 * The board on which the game is played.
	 * 
	 * @return the state of the board
	 */
	@Override
	public Board getBoard() {
		return this.board;
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
		List<Node> nodesToSwap;
		Node node = board.getNodeById(nodeId);
		nodesToSwap = rules.getNodesToSwap(board, node, playerId);
		return nodesToSwap;
	}

	/**
	 * Get the player in turn or null if no player can move
	 * 
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		return players.get(playerInTurn);
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 * 
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		// Return a copy of our internal players to enforce immutability.
		List<Player> cloneOfPlayers = new ArrayList<>();
		cloneOfPlayers.addAll(players);
		return cloneOfPlayers;
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
		return rules.hasValidMove(board, playerId);
	}

	/**
	 * Determines if the game is active or over
	 * 
	 * @return false if the game is over
	 */
	@Override
	public boolean isActive() {
		return !rules.isGameOver(board, players.get(0).getId(), players.get(1).getId());
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
		Node node = board.getNodeById(nodeId);
		return rules.validMove(board, node, playerId);
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
		Player currentPlayer = players.get(playerInTurn);
		switch (currentPlayer.getType()) {
		case HUMAN:
			throw new IllegalArgumentException("Tried to do an AI move using a human player.");
		case COMPUTER:
			ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
			Node nodeToPlayAt = computerPlayer.getMove(rules, board);
			return this.move(currentPlayer.getId(), nodeToPlayAt.getId());
		}
		throw new IllegalStateException("This should never be reached.");
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
		if (!isMoveValid(playerId, nodeId)) {
			throw new IllegalArgumentException("Tried to make an invalid move.");
		}
		Node nodePlayedAt = board.getNodeById(nodeId);
		List<Node> nodesToSwap = rules.getNodesToSwap(board, nodePlayedAt, playerId);
		nodesToSwap.add(nodePlayedAt);
		this.board = boardFactory.newBoardReplacingNodesInBoard(board, nodesToSwap, playerId);
		this.switchPlayer();
		return nodesToSwap;
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {
		int randomPlayerIndex = new Random().nextInt(nPlayers);
		Player randomPlayer = players.get(randomPlayerIndex);
		start(randomPlayer.getId());
	}

	/**
	 * Starts the game.
	 * 
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	@Override
	public void start(String playerId) {
		this.board = boardFactory.newDefaultStartingBoard();
		for (int i = 0; i < nPlayers; i++) {
			String existingPlayerID = players.get(i).getId();
			if (existingPlayerID.equals(playerId)) {
				playerInTurn = i;
				return;
			}
		}

		// Could not find player with playerId
		throw new IllegalArgumentException("Tried to start with non-existing playerId: " + playerId);
	}

	private void switchPlayer() {
		// Alternates between player 0 and player 1
		playerInTurn = (playerInTurn + 1) % nPlayers;
	}
}
