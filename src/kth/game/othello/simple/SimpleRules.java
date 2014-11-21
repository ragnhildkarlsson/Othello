package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.simple.board.ImmutableBoard;
import kth.game.othello.simple.board.ImmutableBoard.Direction;

/**
 * Represents the rules of simple Othello.
 * 
 * @author Ragnhild Karlsson
 * 
 */

public class SimpleRules {

	/**
	 * Creates an object with the responsibility of telling the rules of
	 * Othello.
	 */
	protected SimpleRules() {

	}

	/**
	 * Returns true iff the given node (A) is not occupied and there exist a
	 * node(B) on the board such that B is occupied with the given playerID and
	 * there exist at least one straight (horizontal, vertical, or diagonal)
	 * line between A and B where all nodes are occupied by the other Player.
	 * 
	 * @param Node
	 *            the node on the board where the player wants to play.
	 * @param PlayerID
	 *            the id of the player making the move.
	 * @param board
	 *            the board where the move would be made.
	 * @return true iff move is valid.
	 */
	protected boolean validMove(ImmutableBoard board, Node node, String playerId) {

		// check if any nodes are swapped by move
		List<Node> swappedNodes = getNodesToSwap(board, node, playerId);
		if (swappedNodes.size() > 0) {
			return true;
		}
		// else return false
		return false;
	}

	/**
	 * Returns true iff any node (A) is not occupied and there exist a node(B)
	 * on the board such that B is occupied with the given playerID and there
	 * exist at least one straight (horizontal, vertical, or diagonal) line
	 * between A and B where all nodes are occupied by the other Player.
	 * 
	 * @param PlayerID
	 *            the id of the player making the move.
	 * @param board
	 *            the board in which to look for a valid move.
	 * @return true iff a valid move exists for the given player.
	 */
	protected boolean hasValidMove(ImmutableBoard board, String playerId) {
		for (Node node : board.getNodes()) {
			if (validMove(board, node, playerId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns false iff any of the two players can make a valid move.
	 * 
	 * @board the board to be checked for game over.
	 * @return Returns false iff any of the two players can make a valid move.
	 */
	protected boolean isGameOver(ImmutableBoard board, String player1Id, String player2Id) {
		if (hasValidMove(board, player1Id) || hasValidMove(board, player2Id)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a list with the nodes that will be swapped for the given move,
	 * excluding the node that is placed by the player.
	 * 
	 * @param board
	 *            the board where the move would be made.
	 * @param playerId
	 *            the id of the player making the move.
	 * @param node
	 *            a node on the board where the player wants to play.
	 * @return the list of nodes that will be swapped for the given move,
	 *         excluding the node that is placed by the player.
	 */
	protected List<Node> getNodesToSwap(ImmutableBoard board, Node node, String playerId) {
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
	 * Returns the nodes in the given direction (in relation to the given node)
	 * that would be swapped if the player with the given playerId would play at
	 * the given node.
	 */
	private ArrayList<Node> getSwappableNodesInDirection(ImmutableBoard board, String playerId, Node originNode,
			Direction direction) {

        // check that the following nodes in the given direction belongs to the
        // opponent until we reach a node marked by the given player. If this
        // never happens, return an empty list.
        ArrayList<Node> result = new ArrayList<>();
        while (true) {
			Node nextNodeInDirection = board.getNextNodeInDirection(originNode, direction);
			if (nextNodeInDirection == null || nextNodeInDirection.getOccupantPlayerId() == null) {
				// Reached edge or unmarked node - return empty list.
				result.clear();
				return result;
			}

			if (nextNodeInDirection.getOccupantPlayerId().equals(playerId)) {
                // Found own node. Return nodes in between.
				return result;
			}

			result.add(nextNodeInDirection);
			originNode = nextNodeInDirection;
		}
	}
}
