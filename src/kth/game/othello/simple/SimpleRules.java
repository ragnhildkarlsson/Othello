package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.simple.SimpleBoard.Direction;

/**
 * Represents the rules of simple Othello
 * 
 * @author ragnhild karlsson
 * 
 */

public class SimpleRules {

	/**
	 * Creates a object that has the responsibility to tell the rules of
	 * Othello.
	 */
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
	 * 
	 * @return true if move is valid else false;
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
	 * Returns true if it exists a node (A) on the board that is not occupied
	 * and it exist a node(B) on the board such as B is occupied with the given
	 * playerID and it exist at least one straight (horizontal, vertical, or
	 * diagonal) line between A and B where all nodes are occupied by the other
	 * player. Else return false
	 * 
	 * @param PlayerID
	 *            the id of the player making the move
	 * @param board
	 */
	protected boolean hasValidMove(SimpleBoard board, String playerId) {
		for (Node node : board.getNodes()) {
			if (validMove(board, node, playerId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns false if any of the two players can make a valid move. else
	 * return true
	 * 
	 * @board the board where the game is played
	 * @return Returns false if any of the two players can make a valid move.
	 *         else return true
	 */
	protected boolean isGameOver(SimpleBoard board, String player1Id, String player2Id) {
		if (hasValidMove(board, player1Id) || hasValidMove(board, player2Id)) {
			return false;
		}
		return true;
	}

	/**
	 * 
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

	/*
	 * Return the nodes in the given direction (in relation to the given node)
	 * that are swapped if the player with the given playerId play on the given
	 * node.
	 */

	private ArrayList<Node> getSwappableNodesInDirection(SimpleBoard board, String playerId, Node originNode,
			Direction direction) {

		ArrayList<Node> result = new ArrayList<>();
		// Check that the direct neighbor in the given direction belong to other
		// player
		// otherwise returns an empty list
		Node nextNodeInDirection = board.getNextNodeInDirection(originNode, direction);
		if (nextNodeInDirection == null) {
			return result;
		}
		if (nextNodeInDirection.getOccupantPlayerId() == null
				|| nextNodeInDirection.getOccupantPlayerId().equals(playerId)) {
			return result;
		}

		result.add(nextNodeInDirection);
		originNode = nextNodeInDirection;
		// check that the following nodes in the given direction belongs to the
		// other player until we reach a node
		// marked by the given player. Otherwise is an empty list returned
		while (true) {
			nextNodeInDirection = board.getNextNodeInDirection(originNode, direction);
			if (nextNodeInDirection == null) {
				// reached edge return empty list
				result.clear();
				return result;
			}
			if (nextNodeInDirection.getOccupantPlayerId().equals(playerId)) {
				return result;
			}
			result.add(nextNodeInDirection);
			originNode = nextNodeInDirection;
		}
	}
}
