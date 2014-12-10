package kth.game.othello;

import java.util.List;
import java.util.Map;
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

	Rules rules;

	protected MoveCoordinator(Rules rules) {
		this.rules = rules;
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
		return boardAdapter.setBoardState(gameModel.getGameState().getBoard());
	}

	private Coordinates toCoordinates(Node node) {
		return new Coordinates(node.getXCoordinate(), node.getYCoordinate());
	}

}
