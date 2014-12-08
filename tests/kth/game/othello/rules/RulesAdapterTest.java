package kth.game.othello.rules;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
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

		Node mockNode = Mockito.mock(Node.class);
		Optional<Node> optionalNode = Optional.of(mockNode);

		Mockito.when(mockBoardAdapter.getNodeById(nodeToSwapId)).thenReturn(optionalNode);
		Mockito.when(mockNode.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNode.getYCoordinate()).thenReturn(0);

		Mockito.when(mockBoardAdapter.getImmutableBoard()).thenReturn(null);
		Mockito.when(mockBoardAdapter.getNode(coordinates00)).thenReturn(mockNode);

		ImmutableNode nodeReturned = new ImmutableNode(new Coordinates(0, 0), playerId);
		Set<ImmutableNode> mockNodeSet = new HashSet<ImmutableNode>();
		mockNodeSet.add(nodeReturned);
		Mockito.when(mockRules.getNodesToSwap(null, new Coordinates(0, 0), playerId)).thenReturn(mockNodeSet);

		RulesAdapter rulesAdapter = new RulesAdapter(mockRules, mockBoardAdapter);

		List<Node> result = rulesAdapter.getNodesToSwap(playerId, nodeToSwapId);

		// Should return exactly one node with coordinates 0,0
		assertEquals(1, result.size());
		assertEquals(0, result.get(0).getXCoordinate());
		assertEquals(0, result.get(0).getYCoordinate());

	}

}
