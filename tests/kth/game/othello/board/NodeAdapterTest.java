package kth.game.othello.board;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Observer;
import java.util.Set;

import kth.game.othello.board.NodeAdapter;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.ImmutableNode;

import org.junit.Test;
import org.mockito.Mockito;

public class NodeAdapterTest {

	@Test
	public void testSetNode() throws Exception {
		ImmutableNode immutableNode = new ImmutableNode(new Coordinates(0, 0), "dummy");
		NodeAdapter nodeAdapter = new NodeAdapter(immutableNode);
		// Try changing into a node with the same ImmutableNode, should not
		// cause notification.
		Observer observer = Mockito.mock(Observer.class);
		nodeAdapter.addObserver(observer);
		nodeAdapter.setNode(immutableNode);

		Mockito.verify(observer, Mockito.never()).update(nodeAdapter, "dummy");
		// Try change to a new different node
		ImmutableNode diffNode = new ImmutableNode(new Coordinates(0, 0), "changed");

		nodeAdapter.setNode(diffNode);
		Mockito.verify(observer).update(nodeAdapter, "dummy");

		assertEquals("occupying player id should be changed", "changed", nodeAdapter.getOccupantPlayerId());

	}

	@Test
	public void testGetId() throws Exception {
		Set<String> ids = new HashSet<>();
		int maxIndex = 100;
		for (int y = 0; y < maxIndex; y++) {
			for (int x = 0; x < maxIndex; x++) {
				String playerId = "x" + x + " y" + y;
				ImmutableNode immutableNode = new ImmutableNode(new Coordinates(x, y), playerId);
				NodeAdapter nodeAdapter = new NodeAdapter(immutableNode);
				ids.add(nodeAdapter.getId());
			}
		}
		// Make sure the set is of size maxIndex^2
		assertEquals(maxIndex * maxIndex, ids.size());

	}
}