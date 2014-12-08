/**
 * 
 */
package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A simple strategy to perform a move for a computer player, choosing the move
 * which generates the least swapped nodes for the given player.
 * 
 * @author mikael
 *
 */
public class MasochistStrategy implements MoveStrategy {

	/**
	 * @return the name of the strategy
	 */
	@Override
	public String getName() {
		return "Masochist";
	}

	/**
	 * Returns the node that would generate the least swaps for this player, or
	 * null if no move is possible.
	 * 
	 * @param playerId
	 *            the id of the player that will make a move
	 * @param othello
	 *            the othello game on which to make the move
	 * @return the node where the player wants to move. If the player is not
	 *         able to move then null is returned.
	 */
	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// Check if there is any valid move for this player.
		if (!rules.hasValidMove(playerId)) {
			return null;
		}

		int minSwaps = Integer.MAX_VALUE;
		Node worstNode = null;
		// Find the move which grants the player the least swapped nodes
		for (Node node : board.getNodes()) {
			if (rules.isMoveValid(playerId, node.getId())) {
				int numberOfNodesSwapped = rules.getNodesToSwap(playerId, node.getId()).size();
				if (numberOfNodesSwapped < minSwaps) {
					minSwaps = numberOfNodesSwapped;
					worstNode = node;
				}
			}
		}
		return worstNode;
	}

}
