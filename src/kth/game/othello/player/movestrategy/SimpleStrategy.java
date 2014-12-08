package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A simple strategy to perform a move for a computer player, taking the first
 * available move based on the order of the nodes it is given.
 * 
 */
public class SimpleStrategy implements MoveStrategy {

	/**
	 * @return the name of the strategy
	 */
	@Override
	public String getName() {
		return "simple-strategy";
	}

	/**
	 * Determines which node the given player will move at, taking the first
	 * available move based on the order of the nodes it is given.
	 * 
	 * @param playerId
	 *            the id of the player that will make a move
	 * @param rules
	 *            the rules of this game.
	 * @param board
	 *            the current board of this game.
	 * @return the node where the player wants to move. If the player is not
	 *         able to move then null is returned.
	 */
	@Override
	public Node move(String playerId, Rules rules, Board board) {
		if (!rules.hasValidMove(playerId)) {
			return null;
		}
		java.util.List<Node> nodesOnBoard = board.getNodes();
		for (Node node : nodesOnBoard) {
			if (rules.isMoveValid(playerId, node.getId())) {
				return node;
			}

		}
		return null;
	}
}
