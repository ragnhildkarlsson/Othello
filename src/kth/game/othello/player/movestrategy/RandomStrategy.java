package kth.game.othello.player.movestrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A simple strategy to perform a move for a computer player, choosing a random valid move.
 * 
 * @author mikael
 *
 */
public class RandomStrategy implements MoveStrategy {

	private final Random random;

	/**
	 * Create a new RandomStrategy given a random number generator.
	 * 
	 * @param random
	 */
	public RandomStrategy(Random random) {
		this.random = random;
	}

	@Override
	public String getName() {
		return "Random";
	}

	/**
	 * Returns a random valid node as a move or null if no move is possible for this player.
	 * 
	 * @param playerId
	 *            the id of the player that will make a move
	 * @param rules
	 *            the rules of this game.
	 * @param board
	 *            the current board of this game.
	 * @return the node where the player wants to move. If the player is not able to move then null is returned.
	 */
	@Override
	public Node move(String playerId, Rules rules, Board board) {

		if (rules.hasValidMove(playerId)) {
			List<Node> nodes = board.getNodes();
			List<Node> possibleMoves = new ArrayList<Node>();
			// remove the nodes which there is no valid move for.
			for (Node node : nodes) {
				if (rules.isMoveValid(playerId, node.getId())) {
					possibleMoves.add(node);
				}
			}
			// pick one of the valid moves randomly
			return possibleMoves.get(random.nextInt(possibleMoves.size()));
		}
		// return null if there is no valid move
		return null;
	}

}
