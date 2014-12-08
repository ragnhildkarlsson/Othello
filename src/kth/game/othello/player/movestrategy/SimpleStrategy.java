package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A simple strategy to perform a move for a computer player, taking a random
 * valid move.
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
	 * Determines which node the given player will move at, this will be a
	 * random valid move or null if no valid move exists.
	 * 
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
		if (!othello.hasValidMove(playerId)) {
			return null;
		}
		java.util.List<Node> nodesOnBoard = othello.getBoard().getNodes();
		for (Node node : nodesOnBoard) {
			if (othello.isMoveValid(playerId, node.getId())) {
				return node;
			}

		}
		return null;
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO Auto-generated method stub
		return null;
	}
}
