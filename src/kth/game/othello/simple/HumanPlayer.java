package kth.game.othello.simple;

import kth.game.othello.player.Player;

public class HumanPlayer implements Player {

	private final String playerId;
	private final String name;
	private final Type type;

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
		this.type = Player.Type.HUMAN;

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
		return this.type;
	}
}
