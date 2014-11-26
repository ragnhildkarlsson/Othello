package kth.game.othello.simple;

import java.util.*;
import java.util.stream.Collectors;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.simple.model.*;

/**
 * This class provides a facade that implements the
 * {@link kth.game.othello.Othello} API. Use it to play Othello.
 */
public class SimpleOthello implements Othello {

	private final BoardAdapter boardAdapter;
	private final GameModelFactory gameModelFactory;
	private GameModel gameModel;
	private final Score score;
	private final Map<String, Player> playerMap = new HashMap<>();

	/**
	 * Creates a new SimpleOthello game. Assumes the GameModelFactory to always
	 * produce game models with the board that the given board adapter is
	 * pre-configured with.
	 *
	 * @param players
	 *            the players present on the given board.
	 * @param board
	 *            the board adaptor that will be used.
	 * @param gameModelFactory
	 *            the factory from which the game should produce it's game
	 *            models.
	 * @param score
	 *            the score object that should keep track of the score.
	 */
	protected SimpleOthello(Collection<Player> players, BoardAdapter board, GameModelFactory gameModelFactory,
			SimpleScore score) {
		players.stream().forEach(player -> playerMap.put(player.getId(), player));
		this.boardAdapter = board;
		this.gameModelFactory = gameModelFactory;
		this.score = score;
		Player anyPlayer = players.stream().findAny().get();
		gameModel = gameModelFactory.getNewGameModel(anyPlayer.getId());
		board.setBoardState(gameModel.getGameState().getBoard());
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
		List<Node> nodesToSwap = new ArrayList<>();

		Optional<Node> maybeNode = boardAdapter.getNodeById(nodeId);
		maybeNode.ifPresent(node -> {
			Coordinates nodeCoordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());

			GameState oldState = gameModel.getGameState();
			Optional<GameState> maybeNewState = oldState.tryMove(playerId, nodeCoordinates);
			maybeNewState.ifPresent(newState -> {
				ImmutableBoard oldBoard = oldState.getBoard();
				ImmutableBoard newBoard = newState.getBoard();
				Set<Coordinates> difference = ImmutableBoard.compare(oldBoard, newBoard);
				nodesToSwap.addAll(difference.stream().map(coordinates -> boardAdapter.getNode(coordinates))
						.collect(Collectors.toList()));
			});
		});

		return nodesToSwap;
	}

	/**
	 * Get the player in turn or null if no player can move
	 * 
	 * @return the player in turn
	 */
	@Override
	public Player getPlayerInTurn() {
		String playerIdInTurn = gameModel.getPlayerInTurn();
		return playerMap.get(playerIdInTurn);
	}

	/**
	 * The players of the game. Computer players as well as human players.
	 * 
	 * @return the list of players
	 */
	@Override
	public List<Player> getPlayers() {
		List<Player> playerList = new ArrayList<>();
		playerList.addAll(playerMap.values());
		return playerList;
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
		return !gameModel.isGameOver();
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
		boolean isMoveValid = false;
		Optional<Node> maybeNode = boardAdapter.getNodeById(nodeId);

		if (maybeNode.isPresent()) {
			Node node = maybeNode.get();
			Coordinates coordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());
			isMoveValid = gameModel.isMoveValid(playerId, coordinates);
		}

		return isMoveValid;
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
		String playerIdInTurn = gameModel.getPlayerInTurn();
		Player playerInTurn = playerMap.get(playerIdInTurn);

		Player currentPlayer = playerMap.get(playerInTurn);
		switch (currentPlayer.getType()) {
		case HUMAN:
			throw new IllegalStateException("Tried to do a Computer move using a human player: " + currentPlayer);
		case COMPUTER:
			Coordinates coordinatesToPlayAt = toCoordinates(playerInTurn.getMoveStrategy().move(playerIdInTurn, this));
			return synchronizedMove(playerIdInTurn, coordinatesToPlayAt);
		}
		throw new IllegalStateException("This should never be reached. There is a bug in move() of SimpleOthello.");
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
		Optional<Node> maybeNode = boardAdapter.getNodeById(nodeId);

		if (!playerMap.keySet().contains(playerId)) {
			throw new IllegalArgumentException("The player was not in turn: " + playerId);
		}
		Node node = maybeNode.orElseThrow(() -> new IllegalArgumentException("The node ID does not exist: " + nodeId));

		Coordinates coordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());
		if (!gameModel.isMoveValid(playerId, coordinates)) {
			throw new IllegalArgumentException("The player was not allowed to make a move at the given node.");
		} else {
			return synchronizedMove(playerId, coordinates);
		}
	}

	/**
	 * Performs a move that is assured to be reflected both in the game model
	 * and the board adapter.
	 */
	private List<Node> synchronizedMove(String playerId, Coordinates nodeCoordinates) {
		gameModel.move(playerId, nodeCoordinates);
		return boardAdapter.setBoardState(gameModel.getGameState().getBoard());
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	@Override
	public void start() {
		Random random = new Random();
		int randomPlayerIndex = random.nextInt(playerMap.size());
		Player randomPlayer = (new ArrayList<>(playerMap.values())).get(randomPlayerIndex);
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
		// TODO Game over if wrong playerId
		gameModel = gameModelFactory.getNewGameModel(playerId);
		boardAdapter.setBoardState(gameModel.getGameState().getBoard());
	}

	private Coordinates toCoordinates(Node node) {
		return new Coordinates(node.getXCoordinate(), node.getYCoordinate());
	}
}
