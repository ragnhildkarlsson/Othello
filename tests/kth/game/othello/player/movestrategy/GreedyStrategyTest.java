package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;
import org.mockito.Mockito;

public class GreedyStrategyTest {

	/**
	 * Test that the Strategy returns a null when it does not exist any valid
	 * move for the given player.
	 */
	@Test
	public void testMoveReturnNullWhenNoValidMoveExist() {
		String playerId = "player";

		GreedyStrategy strategy = new GreedyStrategy();
		Rules rules = Mockito.mock(Rules.class);
		Board board = Mockito.mock(Board.class);

		Mockito.when(rules.hasValidMove(playerId)).thenReturn(false);
		assertEquals(null, strategy.move(playerId, rules, board));
	}

	/**
	 * Test the the strategy is greedy, i.e. makes the move which causes the
	 * most nodes to be swapped.
	 * 
	 */
	@Test
	public void testMoveIsGreedy() {
		// Mock the rules and the board
		Rules mockRules = Mockito.mock(Rules.class);
		String playerId = "id";
		Mockito.when(mockRules.hasValidMove(playerId)).thenReturn(true);
		Board mockBoard = Mockito.mock(Board.class);

		// Mock the nodes of the board
		String nodeBadID = "nodeBadID";
		Node nodeBad = Mockito.mock(Node.class);
		Mockito.when(nodeBad.getId()).thenReturn(nodeBadID);
		String nodeGoodID = "nodeGoodID";
		Node nodeGood = Mockito.mock(Node.class);
		Mockito.when(nodeGood.getId()).thenReturn(nodeGoodID);
		List<Node> twoNodes = new ArrayList<>();
		twoNodes.add(nodeGood);
		twoNodes.add(nodeBad);
		Mockito.when(mockBoard.getNodes()).thenReturn(twoNodes);

		// Create a smaller list to return when the bad node is tried
		List<Node> oneNode = new ArrayList<>();
		oneNode.add(nodeBad);
		// Setup so both nodes are valid moves but one gives more swapped nodes
		Mockito.when(mockRules.isMoveValid(playerId, nodeGoodID)).thenReturn(true);
		Mockito.when(mockRules.isMoveValid(playerId, nodeBadID)).thenReturn(true);

		Mockito.when(mockRules.getNodesToSwap(playerId, nodeBadID)).thenReturn(oneNode);
		Mockito.when(mockRules.getNodesToSwap(playerId, nodeGoodID)).thenReturn(twoNodes);

		// Perform the test
		GreedyStrategy greedy = new GreedyStrategy();
		Node chosenNode = greedy.move(playerId, mockRules, mockBoard);
		assertEquals(nodeGood, chosenNode);
	}
}
