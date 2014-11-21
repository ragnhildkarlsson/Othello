package kth.game.othello.simple.player;

import kth.game.othello.player.Player;

/**
 * A simple implementation of the Player interface, which always is the Type
 * HUMAN.
 * 
 * @author Mikael Eriksson
 * @author Ragnhild Karlsson
 * 
 */
public class HumanPlayer implements Player {

	private final String playerId;
	private final String name;

	/**
	 * A player has a unique id, a name and a type.
	 * 
	 * @param playerId
	 *            unique id of the player
	 * @param name
	 *            name of the player
	 */
	protected HumanPlayer(String playerId, String name) {
		this.playerId = playerId;
		this.name = name;
	}

	/**
	 * The id is a unique identifier of this player in the context of all
	 * players
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.playerId;
	}

	/**
	 * The name of the player
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * The {@link kth.game.othello.player.Player.Type} of the player
	 * 
	 * @return the type
	 */
	@Override
	public Type getType() {
		return Player.Type.HUMAN;
	}

    @Override
    public String toString() {
        return "HumanPlayer{" +
            "ID='" + playerId + '\'' +
            ", Name='" + name + '\'' +
            '}';
    }
}
