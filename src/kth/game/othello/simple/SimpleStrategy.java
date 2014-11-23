package kth.game.othello.simple;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * TODO
 */
public class SimpleStrategy implements MoveStrategy {
	/**
	 * @return the name of the strategy
	 */
	@Override
	public String getName() {
		// TODO implement
		return null;
	}

	/**
	 * Determines which node the given player will move at.
	 * 
	 * @param playerId
	 *            the id of the player that will make a move
	 * @param othello
	 *            the othello game on which to make the move
	 * @return the node where the player wants to move. If the player is not
	 *         able to move then null is returned.
	 */
	@Override
	public Node move(String playerId, Othello othello) {
		// TODO Implement
		// old implementation form lousycomputerPlayer
		// /**
		// * Returns a node representing a valid move on the board for the
		// player. If
		// * no valid move exists returns null;
		// *
		// * @param rules
		// * The rules of the present game
		// * @param board
		// * The board where the move should be made
		// * @return A node representing a valid move on the board for the
		// player. If
		// * no valid move exists returns null;
		// */
		// public Node getMove(SimpleRules rules, ImmutableBoard board) {
		// Node result = null;
		// // Check if player have any valid move;
		// for (Node move : board.getNodes()) {
		// if (rules.validMove(board, move, getId())) {
		// result = move;
		// break;
		// }
		// }
		// return result;
		// }

		return null;
	}
}
