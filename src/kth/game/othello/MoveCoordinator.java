package kth.game.othello;

import java.util.List;
import java.util.Observer;
import java.util.Optional;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.GameModel;
import kth.game.othello.model.GameModelFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.rules.Rules;

/**
 * A MoveCoordinator is responsible for making moves such that they are
 * reflected in all relevant components.
 */
public class MoveCoordinator {

	// MoveCoordinator OWNS the game model and may change model as it pleases.
	private GameModel gameModel;

	private final BoardAdapter boardAdapter;
	private final PlayerHandler playerHandler;
	private final Rules rules;
	private final GameFinishedNotifier gameFinishedNotifier;
	private final MoveNotifier moveNotifier;
	private final GameModelFactory gameModelFactory;

	/**
	 * Create a new MoveCoordinator instance.
	 * 
	 * @param rules
	 *            the rules the moves have to follow.
	 * @param gameFinishedNotifier
	 *            the notifier of gameFinished events.
	 * @param moveNotifier
	 *            the notifier of move events.
	 */
	protected MoveCoordinator(GameModel initGameModel, BoardAdapter boardAdapter, PlayerHandler playerHandler,
			Rules rules, GameFinishedNotifier gameFinishedNotifier, MoveNotifier moveNotifier,
			GameModelFactory gameModelFactory) {
		this.boardAdapter = boardAdapter;
		this.playerHandler = playerHandler;
		this.rules = rules;
		this.gameFinishedNotifier = gameFinishedNotifier;
		this.moveNotifier = moveNotifier;
		this.gameModelFactory = gameModelFactory;
		this.gameModel = initGameModel;
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
	public List<Node> move() {
		Optional<String> maybePlayerIdInTurn = gameModel.getPlayerInTurn();
		if (!maybePlayerIdInTurn.isPresent()) {
			// Game Over
			throw new IllegalStateException("Game is over no move is possible");
		}
		String playerIdInTurn = maybePlayerIdInTurn.get();
		Player player = playerHandler.getPlayer(playerIdInTurn);

		switch (player.getType()) {
		case HUMAN:
			throw new IllegalStateException("Tried to do a Computer move using a human player: " + player);
		case COMPUTER:
			Coordinates coordinatesToPlayAt = toCoordinates(player.getMoveStrategy().move(player.getId(), rules,
					boardAdapter));
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
	 *            the id of the node the player wants to play at
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		Node nodeToPlayAt = boardAdapter.getNodeById(nodeId);
		Coordinates coordinates = toCoordinates(nodeToPlayAt);

		if (!gameModel.getPlayerInTurn().equals(Optional.of(playerId))
				|| !rules.isMoveValid(playerId, nodeToPlayAt.getId())) {
			throw new IllegalArgumentException("The player was not allowed to make a move at the given node.");
		}
		return synchronizedMove(playerId, coordinates);
	}

	/**
	 * Performs a move that is assured to be reflected both in the game model
	 * and the board adapter.
	 */
	private List<Node> synchronizedMove(String playerId, Coordinates nodeCoordinates) {
		gameModel.move(playerId, nodeCoordinates);
		return updateBoardState();
	}

	private List<Node> updateBoardState() {
		List<Node> swapped = boardAdapter.setBoardState(gameModel.getGameState().getBoard());
		moveNotifier.moveWasMade(swapped);
		if (!gameModel.getPlayerInTurn().isPresent()) {
			gameFinishedNotifier.gameDidFinish();
		}
		return swapped;
	}

	/**
	 * Starts the game. The player in turn will be chosen randomly.
	 */
	public void start() {
		gameModel = gameModelFactory.newGameModel();
	}

	/**
	 * Starts the game.
	 *
	 * @param playerId
	 *            the id of the player that will start the game.
	 */
	public void start(String playerId) {
		gameModel = gameModelFactory.newGameModel(playerId);
	}

	/**
	 * Undo the last move.
	 */
	public void undo() {
		gameModel.undo().ifPresent(gameState -> boardAdapter.setBoardState(gameState.getBoard()));
	}

	private Coordinates toCoordinates(Node node) {
		return new Coordinates(node.getXCoordinate(), node.getYCoordinate());
	}

	/**
	 * Adds an observer. The observer will be called when the game has finished.
	 *
	 * @param observer
	 *            the observer
	 */
	public void addGameFinishedObserver(Observer observer) {
		this.gameFinishedNotifier.addGameFinishedObserver(observer);
	}

	/**
	 * Adds an observer. The observers update will be called when a move has
	 * finished including the nodes that have changed by the move.
	 *
	 * @param observer
	 *            the observer
	 */
	public void addMoveObserver(Observer observer) {
		this.moveNotifier.addMoveObserver(observer);
	}

	/**
	 * @return An optional containing the player in turn iff the game is not
	 *         game over.
	 */
	public Optional<Player> getPlayerInTurn() {
		Optional<Player> playerInTurn = Optional.empty();
		Optional<String> maybePlayerId = gameModel.getPlayerInTurn();
		if (maybePlayerId.isPresent()) {
			playerInTurn = Optional.of(playerHandler.getPlayer(maybePlayerId.get()));
		}
		return playerInTurn;
	}
}
