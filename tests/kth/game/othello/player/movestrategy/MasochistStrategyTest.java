package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;
import org.mockito.Mockito;

public class MasochistStrategyTest {

	/**
	 * Test that the Strategy returns a null when it does not exist any valid
	 * move for the given player.
	 */
	@Test
	public void testMoveReturnNullWhenNoValidMoveExist() {
		String playerId = "player";

		MasochistStrategy strategy = new MasochistStrategy();
		Rules rules = Mockito.mock(Rules.class);
		Board board = Mockito.mock(Board.class);

		Mockito.when(rules.hasValidMove(playerId)).thenReturn(false);
		assertEquals(null, strategy.move(playerId, rules, board));
	}

	/**
	 * Test the the strategy is least greedy, i.e. makes the move which causes
	 * the least nodes to be swapped.
	 * 
	 */
	@Test
	public void testMoveIsLeastGreedy() {
		Rules mockRules = Mockito.mock(Rules.class);
		String playerId = "id";
		Mockito.when(mockRules.hasValidMove(playerId)).thenReturn(true);
		Board mockBoard = Mockito.mock(Board.class);

		// Mock the nodes of the board
		String nodeLeastID = "nodeLeastID";
		Node nodeLeast = Mockito.mock(Node.class);
		Mockito.when(nodeLeast.getId()).thenReturn(nodeLeastID);
		String nodeMostID = "nodeMostID";
		Node nodeMost = Mockito.mock(Node.class);
		Mockito.when(nodeMost.getId()).thenReturn(nodeMostID);
		List<Node> twoNodes = new ArrayList<>();
		twoNodes.add(nodeMost);
		twoNodes.add(nodeLeast);
		Mockito.when(mockBoard.getNodes()).thenReturn(twoNodes);

		// Create a smaller list to return when the bad node is tried
		List<Node> oneNode = new ArrayList<>();
		oneNode.add(nodeLeast);
		// Setup so both nodes are valid moves but one gives more swapped nodes
		Mockito.when(mockRules.isMoveValid(playerId, nodeMostID)).thenReturn(true);
		Mockito.when(mockRules.isMoveValid(playerId, nodeLeastID)).thenReturn(true);

		Mockito.when(mockRules.getNodesToSwap(playerId, nodeLeastID)).thenReturn(oneNode);
		Mockito.when(mockRules.getNodesToSwap(playerId, nodeMostID)).thenReturn(twoNodes);

		// Perform the test
		MasochistStrategy masochist = new MasochistStrategy();
		Node chosenNode = masochist.move(playerId, mockRules, mockBoard);
		assertEquals(nodeLeast, chosenNode);
	}
}
