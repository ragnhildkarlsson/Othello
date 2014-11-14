package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleBoardTest {

	private final String dummyID = "dummyID";
	private final SimpleBoard dummy8x8Board = generateBoardWithSide(8);

	private List<Node> generateNDummyNodes(int numberOfNodes) {

		// Mock dummy node
		Node dummyNode = Mockito.mock(Node.class);
		Mockito.when(dummyNode.getId()).thenReturn(dummyID);

		// Construct a list of dummy nodes
		List<Node> dummyNodes = new ArrayList<>();
		for (int i = 0; i < numberOfNodes; i++) {
			dummyNodes.add(dummyNode);
		}

		return dummyNodes;
	}

	private SimpleBoard generateBoardWithSide(int boardSide) {

		final int boardSize = boardSide * boardSide;

		// Mock dummy nodes
		List<Node> dummyNodes = generateNDummyNodes(boardSize);

		// Create board and retrieve its nodes

		return new SimpleBoard(dummyNodes);

	}

	@Test
	public void testGetNodesShouldReturnCopy() throws Exception {

		SimpleBoard board = generateBoardWithSide(2);
		List<Node> retrievedNodes = board.getNodes();

		// Mock one node to try and insert
		Node specificNode = Mockito.mock(Node.class);
		Mockito.when(specificNode.getId()).thenReturn("specificNode");

		retrievedNodes.set(0, specificNode);

		for (Node node : board.getNodes()) {
			if (node.getId().equals(specificNode.getId())) {
				fail("SimpleBoard should be immutable but could be mutated through its returned list of nodes.");
			}
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfRightBoundsShouldReturnNull() throws Exception {
		assertNull(dummy8x8Board.getNodeAtCoordinates(8, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfBottomBoundsShouldReturnNull() throws Exception {
		assertNull(dummy8x8Board.getNodeAtCoordinates(0, -1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfTopBoundsShouldReturnNull() throws Exception {
		assertNull(dummy8x8Board.getNodeAtCoordinates(0, 8));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfLeftBoundsShouldReturnNull() throws Exception {
		assertNull(dummy8x8Board.getNodeAtCoordinates(-1, 0));
	}

	@Test
	public void testGetNodeAtCoordinatesShouldReturnCorrectNode() throws Exception {

		final int boardSide = 8;
		final int boardSize = boardSide * boardSide;

		// First node to look for
		Node lowerLeftNode = Mockito.mock(Node.class);
		Mockito.when(lowerLeftNode.getId()).thenReturn("dskjjhd687");

		// Second node to look for
		Node upperRightNode = Mockito.mock(Node.class);
		Mockito.when(upperRightNode.getId()).thenReturn("dsjkhds728563");

		// Get dummy nodes
		List<Node> dummyNodes = generateNDummyNodes(boardSize);

		// Carefully insert specific nodes at expected positions
		int lastIndex = boardSize - 1;
		dummyNodes.set(0, lowerLeftNode);
		dummyNodes.set(lastIndex, upperRightNode);

		// Create board
		SimpleBoard board = new SimpleBoard(dummyNodes);

		// Test board
		assertEquals(board.getNodeAtCoordinates(0, 0).getId(), lowerLeftNode.getId());
		assertEquals(board.getNodeAtCoordinates(7, 7).getId(), upperRightNode.getId());

	}

	@Test
	public void testGetNodeByID() throws Exception {

		SimpleBoard board = generateBoardWithSide(1);

		assertNotNull(board.getNodeById(dummyID));

		assertNull(board.getNodeById("some random ID that shouldn't exist in board 37892783"));

	}

	@Test
	public void testGetNextNodeInDirection() throws Exception {

		/*-
		 * Construct a board with nodeIDs like these:
		 * NW N NE
		 *  W D E Where NW, N, SE etc. are the 8 directions
		 * SW S SE
		 */
		List<Node> nodes = new ArrayList<>();
		String middleID = "middle";
		String[] nodeIDs = new String[] { SimpleBoard.Direction.NORTHWEST.name(),
				SimpleBoard.Direction.NORTH.name(), SimpleBoard.Direction.NORTHEAST.name(),
				SimpleBoard.Direction.WEST.name(), middleID, SimpleBoard.Direction.EAST.name(),
				SimpleBoard.Direction.SOUTHWEST.name(), SimpleBoard.Direction.SOUTH.name(),
				SimpleBoard.Direction.SOUTHEAST.name() };
		for (String nodeID : nodeIDs) {
			Node node = Mockito.mock(Node.class);
			Mockito.when(node.getId()).thenReturn(nodeID);
			Mockito.when(node.getXCoordinate()).thenReturn(1);
			Mockito.when(node.getYCoordinate()).thenReturn(1);
			nodes.add(node);
		}

		// Create board
		SimpleBoard board = new SimpleBoard(nodes);

		Node middleNode = board.getNodeAtCoordinates(1, 1);
		assertEquals(middleID, middleNode.getId());

		for (SimpleBoard.Direction direction : SimpleBoard.Direction.values()) {
			Node nodeInDirection = board.getNextNodeInDirection(middleNode, direction);
			assertEquals(direction.name(), nodeInDirection.getId());
		}

	}
}