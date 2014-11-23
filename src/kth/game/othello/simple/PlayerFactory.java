package kth.game.othello.simple;

import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

public class PlayerFactory {

	private static final String DEFAULT_WHITE_PLAYER_ID = "white";
	private static final String DEFAULT_BLACK_PLAYER_ID = "black";
	private static final String DEFAULT_WHITE_PLAYER_NAME = "WhitePlayer";
	private static final String DEFAULT_BLACK_PLAYER_NAME = "BlackPlayer";

	public PlayerFactory() {

	}

	public Player getComputerPlayer(MoveStrategy startStrategy) {
		// TODO implement
		return null;
	}

	public Player getHumanPlayer() {
		// TODO implement
		return null;
	}
}
