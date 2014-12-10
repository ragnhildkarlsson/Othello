package kth.game.othello.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is responsible for keeping the players of the game
 */

public class PlayerHandler {

	HashMap<String, Player> players;

	public PlayerHandler(Collection<Player> players) {
		this.players = new HashMap<String, Player>();
		players.stream().forEach(player -> this.players.put(player.getId(), player));
	}

	/**
	 * Return the player that have the given id. If no player exist with the
	 * given id is a NoSuchElementException thrown.
	 * 
	 * @param playerId
	 * @return The player that have given id.
	 */
	public Player getPlayer(String playerId) {
		if (!players.containsKey(playerId)) {
			throw new NoSuchElementException("Player id \"" + playerId + "\" does not exist.");
		}
		return players.get(playerId);
	}

	/**
	 * Return a list with all players of the game.
	 * 
	 * @return the list of players
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(players.values());
	}

}
