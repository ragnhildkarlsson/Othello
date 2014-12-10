package kth.game.othello.model;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for creating new game models.
 * 
 */

public class GameModelFactory {
	private ImmutableBoard startBoard;
	private List<String> playerIds;
	private ModelRules rules;

	/**
	 * Generate a new GameModelFactory given its designated starting board, the
	 * players and the rules of the game.
	 * 
	 * 
	 * @param startBoard
	 *            the board in starting position
	 * @param playerIds
	 *            the players of the game
	 * @param rules
	 *            the rules of the game
	 */
	public GameModelFactory(ImmutableBoard startBoard, List<String> playerIds, ModelRules rules) {
		this.startBoard = startBoard;
		this.playerIds = playerIds;
		this.rules = rules;
	}

	/**
	 * Generates a new game model with the given starting player.
	 *
	 * @param startPlayerId
	 *            the player to start the game
	 * @return a game state with the set starting board and with the given
	 *         player ID first in turn.
	 */
	public GameModel newGameModel(String startPlayerId) {
		return newGameModel(Optional.of(startPlayerId), startBoard);
	}

	/**
	 * Generates a new game model with a random starting player.
	 *
	 * @return a new game model with a random starting player.
	 */
	public GameModel newGameModel() {
		Random random = new Random();
		int randomPlayerIndex = random.nextInt(playerIds.size());
		String randomPlayerID = playerIds.get(randomPlayerIndex);
		return newGameModel(randomPlayerID);
	}

	public GameModel newEmptyGameModel() {
		Set<ImmutableNode> emptyNodes = startBoard.getNodes().stream()
				.map(node -> new ImmutableNode(node.getCoordinates(), Optional.empty())).collect(Collectors.toSet());
		ImmutableBoard emptyBoard = new ImmutableBoard(emptyNodes);
		return newGameModel(Optional.empty(), emptyBoard);
	}

	private GameModel newGameModel(Optional<String> startPlayerId, ImmutableBoard startBoard) {
		TurnCalculator turnCalculator = new TurnCalculator(playerIds);
		GameState startState = new GameState(startBoard, turnCalculator, rules, startPlayerId);
		return new GameModel(startState);
	}
}
