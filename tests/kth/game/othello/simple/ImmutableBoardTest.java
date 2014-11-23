package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.simple.model.Coordinates;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableNode;

import org.junit.Test;
import org.mockito.Mockito;

public class ImmutableBoardTest {

	private final String dummyID = "dummyID";
	private final ImmutableBoard dummy8x8Board = generateBoardWithSide(8);

	private Coordinates createMockCoordinates(int x, int y) {
		Coordinates cord = Mockito.mock(Coordinates.class);
		Mockito.when(cord.getXCoordinate()).thenReturn(x);
		Mockito.when(cord.getYCoordinate()).thenReturn(y);
		return cord;
	}

	private Set<ImmutableNode> generateNDummyNodes(int numberOfNodes) {

		// Mock dummy node
		ImmutableNode dummyNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(dummyNode.getId()).thenReturn(dummyID);

		// Construct a list of dummy nodes

		Set<ImmutableNode> dummyNodes = new HashSet<ImmutableNode>();
		// Set<ImmutableNode> dummyNodes = new Set<>();
		for (int i = 0; i < numberOfNodes; i++) {
			dummyNodes.add(dummyNode);
		}

		return dummyNodes;
	}

	private ImmutableBoard generateBoardWithSide(int boardSide) {

		final int boardSize = boardSide * boardSide;

		// Mock dummy nodes
		Set<ImmutableNode> dummyNodes = generateNDummyNodes(boardSize);

		// Create board and retrieve its nodes

		return new ImmutableBoard(dummyNodes);

	}

	@Test
	public void testGetNodesShouldReturnCopy() throws Exception {

		ImmutableBoard board = generateBoardWithSide(2);
		Set<ImmutableNode> retrievedNodes = board.getNodes();

		// Mock one node to try and insert
		ImmutableNode specificNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(specificNode.getId()).thenReturn("specificNode");

		retrievedNodes.add(specificNode);

		for (ImmutableNode node : board.getNodes()) {
			if (node.getId().equals(specificNode.getId())) {
				fail("SimpleBoard should be immutable but could be mutated through its returned list of nodes.");
			}
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfRightBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = Mockito.mock(Coordinates.class);
		Mockito.when(cord.getXCoordinate()).thenReturn(8);
		Mockito.when(cord.getYCoordinate()).thenReturn(0);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfBottomBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = createMockCoordinates(0, -1);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfTopBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = createMockCoordinates(0, 8);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfLeftBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = createMockCoordinates(-1, 1);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test
	public void testGetNodeAtCoordinatesShouldReturnCorrectNode() throws Exception {

		final int boardSide = 8;
		final int boardSize = boardSide * boardSide;

		// First node to look for at position (0,0)
		ImmutableNode zeroZeroNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(zeroZeroNode.getId()).thenReturn("dskjjhd687");
		Coordinates zeroZeroCord = createMockCoordinates(0, 0);
		Mockito.when(zeroZeroNode.getCoordinates()).thenReturn(zeroZeroCord);

		// Second node to look for
		ImmutableNode sevenSevenNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(sevenSevenNode.getId()).thenReturn("dsjkhds728563");
		Coordinates sevenSevenCord = createMockCoordinates(7, 7);
		Mockito.when(sevenSevenNode.getCoordinates()).thenReturn(sevenSevenCord);

		// Get dummy nodes
		Set<ImmutableNode> dummyNodes = generateNDummyNodes(boardSize);

		// Carefully insert specific nodes at expected positions
		// remove the current (0,0) and (7,7) nodes in the set
		dummyNodes.remove(zeroZeroNode);
		dummyNodes.remove(sevenSevenNode);
		// Add our new version of nodes with the correct id
		dummyNodes.add(zeroZeroNode);
		dummyNodes.add(sevenSevenNode);

		// Create board
		ImmutableBoard board = new ImmutableBoard(dummyNodes);

		// Test board
		assertEquals(board.getNodeAtCoordinates(zeroZeroCord).getId(), zeroZeroNode.getId());
		assertEquals(board.getNodeAtCoordinates(sevenSevenCord).getId(), sevenSevenNode.getId());

	}

	@Test
	public void testGetNodeByID() throws Exception {

		ImmutableBoard board = generateBoardWithSide(1);

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
		Set<ImmutableNode> nodes = new HashSet<>();
		String middleID = "middle";
		String[] nodeIDs = new String[] { ImmutableBoard.Direction.NORTHWEST.name(),
				ImmutableBoard.Direction.NORTH.name(), ImmutableBoard.Direction.NORTHEAST.name(),
				ImmutableBoard.Direction.WEST.name(), middleID, ImmutableBoard.Direction.EAST.name(),
				ImmutableBoard.Direction.SOUTHWEST.name(), ImmutableBoard.Direction.SOUTH.name(),
				ImmutableBoard.Direction.SOUTHEAST.name() };
		for (String nodeID : nodeIDs) {
			ImmutableNode node = Mockito.mock(ImmutableNode.class);
			Mockito.when(node.getId()).thenReturn(nodeID);
			Coordinates cord = createMockCoordinates(1, 1);
			Mockito.when(node.getCoordinates()).thenReturn(cord);
			nodes.add(node);
		}

		// Create board
		ImmutableBoard board = new ImmutableBoard(nodes);

		ImmutableNode middleNode = board.getNodeById(middleID);
		assertEquals(middleID, middleNode.getId());

		for (ImmutableBoard.Direction direction : ImmutableBoard.Direction.values()) {
			ImmutableNode nodeInDirection = board.getNextNodeInDirection(middleNode, direction);
			assertEquals(direction.name(), nodeInDirection.getId());
		}

	}
}