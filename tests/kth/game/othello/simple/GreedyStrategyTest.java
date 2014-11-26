package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class GreedyStrategyTest {

	/**
	 * Test that the Strategy returns a null when it does not exist any valid
	 * move for the given player.
	 */
	@Test
	public void testMoveReturnNullWhenNoValidMoveExist() {
		Othello mockOthello = Mockito.mock(Othello.class);
		String playerId = "player";
		Mockito.when(mockOthello.hasValidMove(playerId)).thenReturn(false);
		SimpleStrategy strategy = new SimpleStrategy();
		assertEquals(null, strategy.move(playerId, mockOthello));
	}

	/**
	 * Test the the strategy is greedy, i.e. makes the move which causes the
	 * most nodes to be swapped.
	 * 
	 */
	@Test
	public void testMoveIsGreedy() {
		// Mock the Othello
		Othello mockOthello = Mockito.mock(Othello.class);
		String playerId = "id";
		Mockito.when(mockOthello.hasValidMove(playerId)).thenReturn(true);
		Board mockBoard = Mockito.mock(Board.class);
		Mockito.when(mockOthello.getBoard()).thenReturn(mockBoard);

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
		Mockito.when(mockOthello.isMoveValid(playerId, nodeGoodID)).thenReturn(true);
		Mockito.when(mockOthello.isMoveValid(playerId, nodeBadID)).thenReturn(true);

		Mockito.when(mockOthello.getNodesToSwap(playerId, nodeBadID)).thenReturn(oneNode);
		Mockito.when(mockOthello.getNodesToSwap(playerId, nodeGoodID)).thenReturn(twoNodes);

		// Perform the test
		GreedyStrategy greedy = new GreedyStrategy();
		Node chosenNode = greedy.move(playerId, mockOthello);
		assertEquals(nodeGood, chosenNode);
	}
}
