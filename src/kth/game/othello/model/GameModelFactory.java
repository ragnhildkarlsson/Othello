package kth.game.othello.model;

import java.util.List;

/**
 * This class is responsible for creating new game models.
 * 
 */

public class GameModelFactory {
	private ImmutableBoard startBoard;
	private List<String> playerIds;
	private Rules rules;

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
	public GameModelFactory(ImmutableBoard startBoard, List<String> playerIds, Rules rules) {
		this.startBoard = startBoard;
		this.playerIds = playerIds;
		this.rules = rules;
	}

	/**
	 * Generate a new game model with a set board but an optional player to
	 * start game.
	 * 
	 * 
	 * @param startPlayerId
	 *            the player to start the game
	 * @return a game state with the set starting board and with the given
	 *         player ID first in turn.
	 */
	public GameModel getNewGameModel(String startPlayerId) {
		TurnCalculator turnCalculator = new TurnCalculator(playerIds);
		GameState startState = new GameState(startBoard, turnCalculator, rules, startPlayerId);
		return new GameModel(startState);
	}
}
