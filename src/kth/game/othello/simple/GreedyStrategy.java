package kth.game.othello.simple;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.movestrategy.MoveStrategy;

public class GreedyStrategy implements MoveStrategy {

	private final String name = "greedy-strategy";

	/**
	 * @return the name of the strategy
	 */
	@Override
	public String getName() {
		return name;
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

}