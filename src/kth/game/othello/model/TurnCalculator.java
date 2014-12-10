package kth.game.othello.model;

import java.util.List;
import java.util.Optional;

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
	 * Returns an Optional with the id of the player in turn. Given the previous
	 * player and the resulting board of the previous players move. If no player
	 * can do any move return an empty Optional
	 * 
	 * @param previousPlayer
	 *            the id of the previous player.
	 * @param board
	 *            the board on which the players are playing.
	 * @param rules
	 *            the rules with which the players are playing.
	 */
	public Optional<String> getPlayerInTurn(String previousPlayer, ImmutableBoard board, ModelRules rules) {
		if (rules.isGameOver(board)) {
			return Optional.empty();
		}
		int playerIndex = players.indexOf(previousPlayer);
		if (playerIndex < 0) {
			return Optional.empty();
		}
		playerIndex = (playerIndex + 1) % players.size(); // jump to player
															// after the
															// previous player
		for (int i = 0; i < players.size(); i++) {
			String possiblePlayerInTurn = players.get(playerIndex);
			if (rules.hasValidMove(board, possiblePlayerInTurn)) {
				return Optional.of(possiblePlayerInTurn);
			}
			playerIndex = (playerIndex + 1) % players.size();
		}
		return Optional.empty();
	}

	/**
	 * @return The number of players.
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}
}
