package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;

/**
 * The responsibility of this entity is to define a move strategy for a player
 *
 * @author Tomas Ekholm
 */
public interface MoveStrategy {

	/**
	 * @return the name of the strategy
	 */
	public String getName();

	/**
	 * Determines which node the given player will move at.
	 * 
	 * @param playerId the id of the player that will make a move
	 * @param othello the othello game on which to make the move
	 * @return the node where the player wants to move. If the player is not able to move then null is returned.
	 */
	public Node move(String playerId, Othello othello);

}
