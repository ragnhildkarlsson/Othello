package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.simple.SimpleBoard.Direction;

/**
 * Represent the rules of Othello
 * 
 * @author ragnhild karlsson
 * 
 */

public class SimpleRules {

	protected SimpleRules() {

	}

	/**
	 * Return true if the given node (A) is not occupied and it exist a node(B)
	 * on the board such as B is occupied with the given playerID and it exist
	 * at least one straight (horizontal, vertical, or diagonal) line between A
	 * and B where all nodes are occupied by the other Player. Else return false
	 * 
	 * @param Node
	 *            a node on the board where the player wants to play
	 * @param PlayerID
	 *            the id of the player making the move
	 * @param board
	 */
	protected boolean validMove(SimpleBoard board, Node node, String playerId) {

		// check if any nodes are swapped by move
		List<Node> swappedNodes = getNodesToSwap(board, node, playerId);
		if (swappedNodes.size() > 0) {
			return true;
		}
		// else return false
		return false;
	}

	/**
	 * Returns a list with the nodes that will be swapped for the given move,
	 * excluding the node that is placed by the player
	 * 
	 * @param board
	 *            the board where the game is played
	 * @param playerId
	 *            the id of the player making the move
	 * @param node
	 *            a node on the board that the player want to play
	 * @return the list of nodes that will be swapped for the given move,
	 *         excluding the node that is placed by the player
	 */

	protected List<Node> getNodesToSwap(SimpleBoard board, Node node, String playerId) {
		ArrayList<Node> result = new ArrayList<Node>();
		// check if node is occupied
		if (node.isMarked()) {
			result.clear();
			return result;
		}
		// Add the nodes swapped in each direction
		ArrayList<Node> nodesInDirection;
		for (Direction dir : Direction.values()) {
			nodesInDirection = getSwappableNodesInDirection(board, playerId, node, dir);
			for (Node swappedNode : nodesInDirection) {
				result.add(swappedNode);
			}
		}

		return result;

	}

	/**
	 * Return the nodes in the given direction (in relation to the given node)
	 * that are swapped if the player with the given playerId play on the given
	 * node.
	 * 
	 * @param board
	 *            The board where the game is played
	 * @param playerId
	 *            the id of the player making the move
	 * @param node
	 *            The node on the board where the player want play
	 * @param direction
	 *            The direction.
	 * @return the nodes in the given direction that would be swapped should the player
	 *         with the given playerId play on the given node.
	 */

	private ArrayList<Node> getSwappableNodesInDirection(SimpleBoard board, String playerId, Node node,
			Direction direction) {

		ArrayList<Node> result = new ArrayList<>();
		// Check that neighbor node belong to other player otherwise return
		// empty list
		Node nextNodeInDirection = board.getNextNodeInDirection(node, direction);
		if (nextNodeInDirection == null) {
			return result;
		}
		if (nextNodeInDirection.getOccupantPlayerId() == null || nextNodeInDirection.getOccupantPlayerId() == playerId) {
			return result;
		}
		result.add(nextNodeInDirection);
		node = nextNodeInDirection;

		while (true) {
			nextNodeInDirection = board.getNextNodeInDirection(node, direction);
			if (nextNodeInDirection == null) {
				// reached edge return empty list
				result.clear();
				return result;
			}
			if (nextNodeInDirection.getOccupantPlayerId() == playerId) {
				return result;
			}
			result.add(nextNodeInDirection);
			node = nextNodeInDirection;
		}
	}

}
