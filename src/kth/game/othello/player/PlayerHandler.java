package kth.game.othello.player;

import java.util.*;

/**
 * This class is responsible for keeping the players of the game and mapping
 * between their id and object representation.
 */
public class PlayerHandler {

	HashMap<String, Player> players;

	public PlayerHandler(Collection<Player> players) {
		this.players = new HashMap<>();
		players.stream().forEach(player -> this.players.put(player.getId(), player));
	}

	/**
	 * Return the player that have the given id. If no player exist with the
	 * given id is a NoSuchElementException thrown.
	 * 
	 * @param playerId
	 *            the player id of the wanted player object
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
