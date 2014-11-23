package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.simple.model.Coordinates;
import kth.game.othello.simple.model.GameModel;
import kth.game.othello.simple.model.GameModelFactory;
import kth.game.othello.simple.model.ImmutableBoard;

/**
 * Created by spike on 11/22/14.
 */
public class SimpleOthello implements Othello {

	private BoardWrapper boardWrapper;
	private ImmutableBoard startingBoard;
	private PlayerHandler playerHandler;
	private GameModelFactory gameModelFactory;
	private GameModel gameModel;
	private Score score;

	public SimpleOthello(ImmutableBoard startingBoard, BoardWrapper board, PlayerHandler playerHandler,
			GameModelFactory gameModelFactory, Score score) {
		this.startingBoard = startingBoard;
		this.playerHandler = playerHandler;
		this.boardWrapper = board;
		this.gameModelFactory = gameModelFactory;

	}

	/**
	 * The board on which the game is played.
	 * 
	 * @return the state of the board
	 */
	@Override
	public Board getBoard() {
		return boardWrapper;
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
		Node node = boardWrapper.getNodeById(nodeId);
		Coordinates nodeCoordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());
		Set<Coordinates> coordinatesOfSwappedNodes = gameModel.getNodesToSwap(playerId, nodeCoordinates);
		ArrayList<Node> result = new ArrayList<Node>();
		for (Coordinates cord : coordinatesOfSwappedNodes) {
			result.add(boardWrapper.getNode(cord.getXCoordinate(), cord.getYCoordinate()));
		}
		return result;
	}

	/**
	 * Get the player in turn or null if no player can move
	 * 
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		return playerHandler.getPlayerById(gameModel.getPlayerInTurn());
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 * 
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		return playerHandler.getPlayers();
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
		return gameModel.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or over
	 * 
	 * @return false if the game is over
	 */
	@Override
	public boolean isActive() {
		return gameModel.isActive();
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
		Node node = boardWrapper.getNodeById(nodeId);
		Coordinates coordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());
		return gameModel.isMoveValid(playerId, coordinates);
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

		// TODO Fix
		// update boardwrapper with the changes from the model
		return null;

		// Player currentPlayer = players.get(playerInTurn);
		// switch (currentPlayer.getType()) {
		// case HUMAN:
		// throw new
		// IllegalStateException("Tried to do an AI move using a human player.");
		// case COMPUTER:
		// ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
		// Node nodeToPlayAt = computerPlayer.getMove(rules, board);
		// return this.move(currentPlayer.getId(), nodeToPlayAt.getId());
		// }
		// throw new IllegalStateException("This should never be reached.");
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
		// TODO
		// Update boardwrapper with the changes from the nodes
		return null;
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {
		// Create a new game model
		// change the nodes on board wrapper according to startBoard
	}

	/**
	 * Starts the game.
	 * 
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	@Override
	public void start(String playerId) {
		// TODO Throw exception if playerId is not among players
		// Create a new game model
		// change the nodes on board wrapper according to startBoard
	}
}
