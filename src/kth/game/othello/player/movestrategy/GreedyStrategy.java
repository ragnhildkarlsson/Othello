package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A simple strategy to perform a move for a computer player, choosing the move
 * which generates the most swapped nodes for the given player.
 * 
 * @author mikael
 *
 */
public class GreedyStrategy implements MoveStrategy {

	/**
	 * @return the name of the strategy
	 */
	@Override
	public String getName() {
		return "greedy-strategy";
	}

	/**
	 * Returns the node that would generate the most swaps for this player, or
	 * null if no move is possible.
	 * 
	 * @param playerId
	 *            the id of the player that will make a move
	 * @param othello
	 *            the othello game on which to make the move
	 * @return the node where the player wants to move. If the player is not
	 *         able to move then null is returned.
	 */
	// TODO REMOVE
	public Node move(String playerId, Othello othello) {
		// Check if there is any valid move for this player.
		if (!othello.hasValidMove(playerId)) {
			return null;
		}

		int maxSwaps = 0;
		Node bestNode = null;
		// Find the move which grants the player the most swapped nodes
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(playerId, node.getId())) {
				int numberOfNodesSwapped = othello.getNodesToSwap(playerId, node.getId()).size();
				if (numberOfNodesSwapped > maxSwaps) {
					maxSwaps = numberOfNodesSwapped;
					bestNode = node;
				}
			}
		}
		return bestNode;
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
