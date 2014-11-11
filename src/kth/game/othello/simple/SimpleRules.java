package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

/**
 * Represent the rules of Othello
 * 
 * @author ragnhild karlsson
 * 
 */

// public enum Direction {
// NORTH,NORTHEAST,EAST, HUMAN
// }

public class SimpleRules {

	protected SimpleRules() {

	}

	/**
	 * Return true if the given node (A) is not occupied and it exist a node(B)
	 * on the board such as B is occupied with the given playerID and it exist
	 * at least one straight (horizontal, vertical, or diagonal) line between A
	 * and B where all nodes are occupied by the other Player. Else return false
	 * 
	 * @param NodeId
	 *            of the node where the player want to place the node
	 * @param PlayerID
	 *            of the player
	 * @param board
	 */
	protected boolean validMove(SimpleBoard board, String nodeId, String playerId) {
		// check if Node exist on board.
		Node node = board.getNodeById(nodeId);
		if (node == null) {
			return false;
		}
		// check if node exist on board.
		if (node.isMarked()) {
			return false;
		}
		// check if any nodes are swapped by move
		List<Node> swappedNodes = getNodesToSwap(board, nodeId, playerId);
		if (swappedNodes.size() > 0) {
			return true;
		}
		// else return false
		return false;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param board
	 *            the board where the game is played
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the id of the node where the move is made
	 * @return the list of nodes that will be swapped for the given move
	 */
	protected List<Node> getNodesToSwap(SimpleBoard board, String nodeId, String playerId) {
		ArrayList<Node> result = new ArrayList<Node>();
		// check if node exist on board
		Node node = board.getNodeById(nodeId);
		if (node == null) {
			return result;
		}
		// Check for line north of node
		boolean lineFound = false;
		boolean edgeFound = false;
		while (!lineFound && !edgeFound) {

		}

		result.add(node);
		return result;

	}

	/**
	 * 
	 * @param board
	 * @param playerId
	 * @param nodeId
	 * @param direction
	 *            Integer representing one of eight direction 1 = north, 2 = N
	 * @return
	 */
	private List<Node> getSwappableNodesInDirection(SimpleBoard board, String playerId, String nodeId, int direction) {
		ArrayList<Node> result = new ArrayList<>();
		return result;
	}

}
