package kth.game.othello.simple.model;

import java.util.List;

/**
 * TODO
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
		TurnKeeper turnKeeper = new TurnKeeper(playerIds, startPlayerId);
		GameModel gameModel = new GameModel(rules, turnKeeper, startBoard);
		return gameModel;
	}
}
