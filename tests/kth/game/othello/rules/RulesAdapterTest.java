package kth.game.othello.rules;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.model.ImmutableNode;
import kth.game.othello.model.ModelRules;

import org.junit.Test;
import org.mockito.Mockito;

public class RulesAdapterTest {

	@Test
	public void testGetNodesToSwap() {

		String playerId = "playerId";
		String nodeToSwapId = "ToBeSwapped";
		Coordinates coordinates00 = new Coordinates(0, 0);
		ModelRules mockRules = Mockito.mock(ModelRules.class);
		BoardAdapter mockBoardAdapter = Mockito.mock(BoardAdapter.class);
		ImmutableBoard mockImmutableBoard = Mockito.mock(ImmutableBoard.class);

		Node mockNode = Mockito.mock(Node.class);

		Mockito.when(mockBoardAdapter.getNodeById(nodeToSwapId)).thenReturn(mockNode);
		Mockito.when(mockNode.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNode.getYCoordinate()).thenReturn(0);

		Mockito.when(mockBoardAdapter.getImmutableBoard()).thenReturn(mockImmutableBoard);
		Mockito.when(mockBoardAdapter.getNode(coordinates00)).thenReturn(mockNode);

		ImmutableNode nodeReturned = new ImmutableNode(coordinates00, playerId);
		Set<ImmutableNode> mockNodeSet = new HashSet<ImmutableNode>();
		mockNodeSet.add(nodeReturned);
		Mockito.when(mockRules.getNodesToSwap(mockImmutableBoard, coordinates00, playerId)).thenReturn(mockNodeSet);

		RulesAdapter rulesAdapter = new RulesAdapter(mockRules, mockBoardAdapter);

		List<Node> result = rulesAdapter.getNodesToSwap(playerId, nodeToSwapId);

		// Should return exactly one node with coordinates 0,0
		assertEquals(1, result.size());
		assertEquals(0, result.get(0).getXCoordinate());
		assertEquals(0, result.get(0).getYCoordinate());

	}

	@Test
	public void testIsMoveValid() {
		String nodeId = "44545";
		String playerId = "playerId";
		Coordinates coordinates00 = new Coordinates(0, 0);
		ModelRules mockRules = Mockito.mock(ModelRules.class);
		BoardAdapter mockBoardAdapter = Mockito.mock(BoardAdapter.class);

		Node mockNode = Mockito.mock(Node.class);

		Mockito.when(mockBoardAdapter.getNodeById(nodeId)).thenReturn(mockNode);
		Mockito.when(mockNode.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNode.getYCoordinate()).thenReturn(0);

		ImmutableBoard mockImmutableBoard = Mockito.mock(ImmutableBoard.class);
		Mockito.when(mockBoardAdapter.getImmutableBoard()).thenReturn(mockImmutableBoard);

		Mockito.when(mockRules.validMove(mockImmutableBoard, coordinates00, playerId)).thenReturn(true);

		RulesAdapter rulesAdapter = new RulesAdapter(mockRules, mockBoardAdapter);

		boolean result = rulesAdapter.isMoveValid(playerId, nodeId);

		assertEquals(true, result);
		Mockito.verify(mockRules).validMove(mockImmutableBoard, coordinates00, playerId);
	}

	@Test
	public void testHasValidMove() {
		String playerId = "playerId";
		ModelRules mockRules = Mockito.mock(ModelRules.class);
		BoardAdapter mockBoardAdapter = Mockito.mock(BoardAdapter.class);

		ImmutableBoard mockImmutableBoard = Mockito.mock(ImmutableBoard.class);
		Mockito.when(mockBoardAdapter.getImmutableBoard()).thenReturn(mockImmutableBoard);

		Mockito.when(mockRules.hasValidMove(mockImmutableBoard, playerId)).thenReturn(true);

		RulesAdapter rulesAdapter = new RulesAdapter(mockRules, mockBoardAdapter);

		boolean result = rulesAdapter.hasValidMove(playerId);

		assertEquals(true, result);
		Mockito.verify(mockRules).hasValidMove(mockImmutableBoard, playerId);

	}
}
