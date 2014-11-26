package kth.game.othello.simple.model;

import java.util.List;

/**
 * This class is responsible for calculate the next player in turn. game.
 * 
 */
public class TurnCalculator {

	private List<String> players;

	/**
	 * Create a new turnKeeper.
	 * 
	 * @param playerIds
	 * @param startingPlayerId
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
	 *            the id of the previousPlayer
	 * @param the
	 * 
	 */
	public String getPlayerInTurn(String previousPlayer, ImmutableBoard newBoard, Rules rules) {
		if (rules.isGameOver(newBoard)) {
			return null;
		}
		int playerIndex = players.indexOf(previousPlayer);
		playerIndex = (playerIndex + 1) % players.size(); // jump to player
															// after the
															// previous player
		for (int i = 0; i < players.size(); i++) {
			String possiblePlayerInTurn = players.get(playerIndex);
			if (rules.hasValidMove(newBoard, possiblePlayerInTurn)) {
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
