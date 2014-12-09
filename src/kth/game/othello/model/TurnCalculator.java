package kth.game.othello.model;

import java.util.List;

/**
 * This class is responsible for calculate the next player in turn.
 * 
 */
public class TurnCalculator {

	private List<String> players;

	/**
	 * Create a new TurnCalculator.
	 * 
	 * @param playerIds
	 *            the players that the turn keeper is to cycle between.
	 */
	public TurnCalculator(List<String> playerIds) {
		this.players = playerIds;
	}

	/**
	 * Return the player in turn given the previous player and the resulting
	 * board of the previous players move. If no player can do any move return
	 * null
	 * 
	 * @param previousPlayer
	 *            the id of the previous player.
	 * @param board
	 *            the board on which the players are playing.
	 * @param rules
	 *            the rules with which the players are playing.
	 * @return //TODO Return optional instead
	 */
	public String getPlayerInTurn(String previousPlayer, ImmutableBoard board, ModelRules rules) {
		if (rules.isGameOver(board)) {
			return null;
		}
		int playerIndex = players.indexOf(previousPlayer);
		if (playerIndex < 0) {
			return null;
		}
		playerIndex = (playerIndex + 1) % players.size(); // jump to player
															// after the
															// previous player
		for (int i = 0; i < players.size(); i++) {
			String possiblePlayerInTurn = players.get(playerIndex);
			if (rules.hasValidMove(board, possiblePlayerInTurn)) {
				return possiblePlayerInTurn;
			}
			playerIndex = (playerIndex + 1) % players.size();
		}
		return null;
	}

	/**
	 * @return The number of players.
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}
}
