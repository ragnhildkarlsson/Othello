package kth.game.othello.simple.player;

import kth.game.othello.board.Node;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.SimpleRules;

/**
 * A representation of a computer player, i.e. a player that can generate moves
 * using AI.
 * 
 * @author Mikael Eriksson
 * 
 */
public interface ComputerPlayer {

	/**
	 * Returns a node representing a valid move on the board for the player. If
	 * no valid move exists returns null;
	 * 
	 * @param rules
	 *            The rules of the present game
	 * @param board
	 *            The board where the move should be made
	 * @return A node representing a valid move on the board for the player. If
	 *         no valid move exists returns null;
	 */
	public Node getMove(SimpleRules rules, ImmutableBoard board);
}
