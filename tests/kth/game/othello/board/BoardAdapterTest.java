package kth.game.othello.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kth.game.othello.model.Coordinates;
import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.model.ImmutableNode;

import org.junit.Test;
import org.mockito.Mockito;

public class BoardAdapterTest {

	@Test
	public void testGetNode() throws Exception {

		NodeAdapter mockNodeAdapter = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNodeAdapter.getYCoordinate()).thenReturn(0);
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		nodeAdapters.add(mockNodeAdapter);
		BoardAdapter boardAdapter = new BoardAdapter(mockBoard, nodeAdapters);

		assertEquals(mockNodeAdapter, boardAdapter.getNode(0, 0));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNonExistingNode() throws Exception {

		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		BoardAdapter boardAdapter = new BoardAdapter(mockBoard, nodeAdapters);

		assertNull(boardAdapter.getNode(3, 666));
	}

	@Test
	public void testSetBoardState() throws Exception {
		ImmutableBoard oldBoard = Mockito.mock(ImmutableBoard.class);
		ImmutableBoard newBoard = Mockito.mock(ImmutableBoard.class);
		ImmutableNode node00 = new ImmutableNode(new Coordinates(0, 0), null);
		ImmutableNode node10 = new ImmutableNode(new Coordinates(1, 0), null);
		ImmutableNode node10New = new ImmutableNode(new Coordinates(1, 0), "swapped");

		Set<ImmutableNode> oldNodes = new HashSet<>();
		oldNodes.add(node00);
		oldNodes.add(node10);

		Set<ImmutableNode> newNodes = new HashSet<>();
		newNodes.add(node00);
		newNodes.add(node10New);
		Mockito.when(oldBoard.getNodes()).thenReturn(oldNodes);
		Mockito.when(newBoard.getNodes()).thenReturn(newNodes);
		Mockito.when(newBoard.getNodeAtCoordinates(node10New.getCoordinates())).thenReturn(node10New);

		NodeAdapter mockNodeAdapter00 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter00.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNodeAdapter00.getYCoordinate()).thenReturn(0);
		NodeAdapter mockNodeAdapter10 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter10.getXCoordinate()).thenReturn(1);
		Mockito.when(mockNodeAdapter10.getYCoordinate()).thenReturn(0);

		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		nodeAdapters.add(mockNodeAdapter00);
		nodeAdapters.add(mockNodeAdapter10);

		BoardAdapter boardAdapter = new BoardAdapter(oldBoard, nodeAdapters);

		List<Node> changedNodeAdapters = boardAdapter.setBoardState(newBoard);

		// Verify that the NodeAdapter for (1,0) has gotten a new node
		Mockito.verify(mockNodeAdapter10).setNode(node10New);
		// Verify that one nodeAdapter has been changed correctly
		assertEquals("Should be one changed nodeAdapter", 1, changedNodeAdapters.size());
		Node changedNodeAdapter = changedNodeAdapters.get(0);
		assertEquals("should have coordinates (1,0)", 1, changedNodeAdapter.getXCoordinate());
		assertEquals("should have coordinates (1,0)", 0, changedNodeAdapter.getYCoordinate());
		// Cannot check for the correct occupying player id since this is a mock
		// NodeAdapter, thus we verify the call (see above).

	}

	@Test
	public void testGetNodesImmutableList() throws Exception {
		NodeAdapter mockNodeAdapter = Mockito.mock(NodeAdapter.class);
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		nodeAdapters.add(mockNodeAdapter);
		BoardAdapter boardAdapter = new BoardAdapter(mockBoard, nodeAdapters);
		// Check that we cannot mutate the list of boardAdapters outside the
		// class
		List<Node> fetchedAdapters = boardAdapter.getNodes();
		fetchedAdapters.add(mockNodeAdapter);

		assertEquals(1, boardAdapter.getNodes().size());

	}

	@Test
	public void testGetNodeById() throws Exception {
		NodeAdapter mockNodeAdapter1 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter1.getId()).thenReturn("right");
		NodeAdapter mockNodeAdapter2 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter2.getId()).thenReturn("wrong");
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		nodeAdapters.add(mockNodeAdapter1);
		nodeAdapters.add(mockNodeAdapter2);
		BoardAdapter boardAdapter = new BoardAdapter(mockBoard, nodeAdapters);
		// Check that we can find a node by its id
		Optional<Node> maybeNode1 = boardAdapter.getNodeById("right");
		assertEquals(true, maybeNode1.isPresent());
		assertEquals(mockNodeAdapter1, maybeNode1.get());
		// Check that get empty Optional for non existing ids
		Optional<Node> notPresent = boardAdapter.getNodeById("fdgdfgfdg");
		assertEquals(false, notPresent.isPresent());

	}

	@Test
	public void testGetMaxXAndGetMaxY() {
		NodeAdapter mockNodeAdapter1 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter1.getXCoordinate()).thenReturn(0);
		Mockito.when(mockNodeAdapter1.getYCoordinate()).thenReturn(3);
		NodeAdapter mockNodeAdapter2 = Mockito.mock(NodeAdapter.class);
		Mockito.when(mockNodeAdapter2.getXCoordinate()).thenReturn(2);
		Mockito.when(mockNodeAdapter2.getYCoordinate()).thenReturn(1);
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		List<NodeAdapter> nodeAdapters = new ArrayList<>();
		nodeAdapters.add(mockNodeAdapter1);
		nodeAdapters.add(mockNodeAdapter2);
		BoardAdapter boardAdapter = new BoardAdapter(mockBoard, nodeAdapters);
		assertEquals(2, boardAdapter.getMaxX());
		assertEquals(3, boardAdapter.getMaxY());
	}

}