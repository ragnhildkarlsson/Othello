package kth.game.othello.simple;

import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * A simple player implementing the Player interface, can either be a
 * computer player or a human player.
 * 
 */
public class SimplePlayer implements Player {

	private String id;
	private String name;
	private MoveStrategy moveStrategy;

	/**
	 * Construct a player of type Human
	 */
	public SimplePlayer(String name, String id) {
		this.name = name;
		this.id = id;
		this.moveStrategy = null;
	}

	/**
	 * Construct a player of type Computer
	 */
	public SimplePlayer(String name, String id, MoveStrategy moveStrategy) {
		this.name = name;
		this.id = id;
		this.moveStrategy = moveStrategy;
	}

	/**
	 * The id is a unique identifier of this player in the context of all
	 * players
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * The current move strategy of the player
	 * 
	 * @return the move strategy
	 * @throws UnsupportedOperationException
	 *             if the player is of
	 *             {@link kth.game.othello.player.Player.Type} HUMAN
	 */
	@Override
	public MoveStrategy getMoveStrategy() {
		return this.moveStrategy;
	}

	/**
	 * The name of the player
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * The {@link kth.game.othello.player.Player.Type} of the player
	 * 
	 * @return the type
	 */
	@Override
	public Type getType() {
		if (this.moveStrategy == null) {
			return Type.HUMAN;
		} else {
			return Type.COMPUTER;
		}
	}

	/**
	 * Sets a new move strategy on the player. The player must be of
	 * {@link kth.game.othello.player.Player.Type} COMPUTER
	 * 
	 * @param moveStrategy
	 * @throws UnsupportedOperationException
	 *             if the player is of
	 *             {@link kth.game.othello.player.Player.Type} HUMAN
	 */
	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	@Override
	public String toString() {
		return "Player{" + "ID='" + id + '\'' + ", Name='" + name + '\'' + ", Type='" + this.getType() + '\'' + '}';
	}
}
