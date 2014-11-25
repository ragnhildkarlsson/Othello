package kth.game.othello.simple.model;

import java.util.List;

/**
 * This class is responsible for creating new game models.
 * 
 */

public class GameModelFactory {
	private ImmutableBoard startBoard;
	private List<String> playerIds;
	private Rules rules;

	public GameModelFactory(ImmutableBoard startBoard, List<String> playerIds, Rules rules) {
		this.startBoard = startBoard;
		this.playerIds = playerIds;
		this.rules = rules;
	}

	public GameModel getNewGameModel(String startPlayerId) {
		TurnCalculator turnCalculator = new TurnCalculator(playerIds);
		GameState startState = new GameState(startBoard, turnCalculator, rules, startPlayerId);
		GameModel gameModel = new GameModel(startState);
		return gameModel;
	}
}
