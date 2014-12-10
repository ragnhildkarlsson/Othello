package kth.game.othello;

import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Optional;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.GameModel;
import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

/**
 * A MoveCoordinator is responsible for making moves such that they are
 * reflected in all relevant components.
 */
public class MoveCoordinator {

	private final Rules rules;
	private final GameFinishedNotifier gameFinishedNotifier;
	private final MoveNotifier moveNotifier;

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
	protected MoveCoordinator(Rules rules, GameFinishedNotifier gameFinishedNotifier, MoveNotifier moveNotifier) {
		this.rules = rules;
		this.gameFinishedNotifier = gameFinishedNotifier;
		this.moveNotifier = moveNotifier;
	}

	/**
	 * If the player in turn is a computer then this computer makes a move and
	 * updates the player in turn.
	 *
	 * @param gameModel
	 *            the gameModel where the move should be made
	 * @param boardAdapter
	 *            the boardAdapter where the move should be reflected
	 * @param player
	 *            the player that should make the move
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalStateException
	 *             if there is not a computer in turn
	 */
	public List<Node> move(Map<String, Player> players, GameModel gameModel, BoardAdapter boardAdapter) {
		Optional<String> playerIdInTurn = gameModel.getPlayerInTurn();
		if (!playerIdInTurn.isPresent()) {
			// game is over - no player in turn
			throw new IllegalStateException("Game is over - no move can be made");
		}
		Player player = players.get(playerIdInTurn.get());
		switch (player.getType()) {
		case HUMAN:
			throw new IllegalStateException("Tried to do a Computer move using a human player: " + player);
		case COMPUTER:
			Coordinates coordinatesToPlayAt = toCoordinates(player.getMoveStrategy().move(player.getId(), rules,
					boardAdapter));
			return synchronizedMove(playerIdInTurn.get(), coordinatesToPlayAt, gameModel, boardAdapter);
		}
		throw new IllegalStateException("This should never be reached. There is a bug in move() of SimpleOthello.");
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so,
	 * then the move is made which updates the board and the player in turn.
	 *
	 * @param playerId
	 *            the id of the player that makes the move
	 * @param node
	 *            the node where the player wants to move
	 * @param gameModel
	 *            the gameModel where the move should be made
	 * @param boardAdapter
	 *            the boardAdapter where the move should be reflected
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public List<Node> move(String playerId, Node node, GameModel gameModel, BoardAdapter boardAdapter)
			throws IllegalArgumentException {
		Coordinates coordinates = new Coordinates(node.getXCoordinate(), node.getYCoordinate());
		if (!rules.isMoveValid(playerId, node.getId()) || gameModel.getPlayerInTurn() == Optional.of(playerId)) {
			throw new IllegalArgumentException("The player was not allowed to make a move at the given node.");
		} else {
			return synchronizedMove(playerId, coordinates, gameModel, boardAdapter);
		}
	}

	/**
	 * Performs a move that is assured to be reflected both in the game model
	 * and the board adapter.
	 */
	private List<Node> synchronizedMove(String playerId, Coordinates nodeCoordinates, GameModel gameModel,
			BoardAdapter boardAdapter) {
		gameModel.move(playerId, nodeCoordinates);
		List<Node> swapped = boardAdapter.setBoardState(gameModel.getGameState().getBoard());
		moveNotifier.moveWasMade(swapped);
		if (gameModel.getPlayerInTurn() == null) {
			gameFinishedNotifier.gameDidFinish();
		}
		return swapped;
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
}
