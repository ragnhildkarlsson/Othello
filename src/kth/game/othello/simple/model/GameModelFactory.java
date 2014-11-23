package kth.game.othello.simple.model;

import java.util.List;

/**
 * TODO
 * 
 */

public class GameModelFactory {
	private ImmutableBoard startBoard;
	private List<String> playerIds;
	private SimpleRules rules;

	public GameModelFactory(ImmutableBoard startBoard, List<String> playerIds, SimpleRules rules) {
		this.startBoard = startBoard;
		this.playerIds = playerIds;
		this.rules = rules;
	}

	public GameModel getNewGameModel(String startPlayerId) {
		MoveMaker moveMaker = new MoveMaker(startBoard, startPlayerId, playerIds, rules);
		GameModel gameModel = new GameModel(moveMaker);
		return gameModel;
	}
}
