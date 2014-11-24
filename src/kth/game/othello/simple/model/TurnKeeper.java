package kth.game.othello.simple.model;

import java.util.List;

/**
 * This class is responsible for keeping the current player in turn to play the
 * game.
 * 
 * @author mikael
 * 
 */
public class TurnKeeper {

	private List<String> players;
	private int playerInTurnIndex;

	/**
	 * Create a new turnKeeper.
	 * 
	 * @param playerIds
	 * @param startingPlayerId
	 */
	public TurnKeeper(List<String> playerIds, String startingPlayerId) {
		this.players = playerIds;
		this.playerInTurnIndex = players.indexOf(startingPlayerId);

	}

	/**
	 * Get the id of the player in turn or null if no player can move
	 * 
	 * @return the id of the player in turn or null if no player can move;
	 */
	public String getPlayerInTurn(Rules rules, ImmutableBoard board) {
		for (int i = 0; i < players.size(); i++) {
			String possiblePlayerInTurn = players.get(playerInTurnIndex);
			if (rules.hasValidMove(board, possiblePlayerInTurn)) {
				return possiblePlayerInTurn;
			}
			this.nextTurn();
		}
		return null;
	}

	/**
	 * Change the current player in turn to the next player.
	 */
	public void nextTurn() {
		playerInTurnIndex = (playerInTurnIndex + 1) % players.size();
	}

	/**
	 * @return The number of players.
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}
}
