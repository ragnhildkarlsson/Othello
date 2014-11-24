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

	private Set<ImmutableNode> generateNDummyNodes(int numberOfNodes) {

		// Mock dummy node
		ImmutableNode dummyNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(dummyNode.getOccupantPlayerId()).thenReturn(dummyID);

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
		Mockito.when(specificNode.getOccupantPlayerId()).thenReturn("specificNode");

		retrievedNodes.add(specificNode);

		for (ImmutableNode node : board.getNodes()) {
			if (node.getOccupantPlayerId().equals(specificNode.getOccupantPlayerId())) {
				fail("SimpleBoard should be immutable but could be mutated through its returned list of nodes.");
			}
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfRightBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = new Coordinates(8, 0);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfBottomBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = new Coordinates(0, -1);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfTopBoundsShouldReturnNull() throws Exception {
		// Mock a coordinate object
		Coordinates cord = new Coordinates(0, 8);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfLeftBoundsShouldReturnNull() throws Exception {
		Coordinates cord = new Coordinates(-1, 1);
		assertNull(dummy8x8Board.getNodeAtCoordinates(cord));
	}

	@Test
	public void testGetNodeAtCoordinatesShouldReturnCorrectNode() throws Exception {

		final int boardSide = 8;
		final int boardSize = boardSide * boardSide;

		// First node to look for at position (0,0)
		ImmutableNode zeroZeroNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(zeroZeroNode.getOccupantPlayerId()).thenReturn("dskjjhd687");
		Coordinates zeroZeroCord = new Coordinates(0, 0);
		Mockito.when(zeroZeroNode.getCoordinates()).thenReturn(zeroZeroCord);

		// Second node to look for
		ImmutableNode sevenSevenNode = Mockito.mock(ImmutableNode.class);
		Mockito.when(sevenSevenNode.getOccupantPlayerId()).thenReturn("dsjkhds728563");
		Coordinates sevenSevenCord = new Coordinates(7, 7);
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
		assertEquals(board.getNodeAtCoordinates(zeroZeroCord).getOccupantPlayerId(), zeroZeroNode.getOccupantPlayerId());
		assertEquals(board.getNodeAtCoordinates(sevenSevenCord).getOccupantPlayerId(),
				sevenSevenNode.getOccupantPlayerId());

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
		String middle = "middle";
		Coordinates[] nodeCoordinates = new Coordinates[] { new Coordinates(0, 0), new Coordinates(1, 0),
				new Coordinates(2, 0), new Coordinates(0, 1), new Coordinates(1, 1), new Coordinates(2, 1),
				new Coordinates(0, 2), new Coordinates(1, 2), new Coordinates(2, 2) };

		String[] directions = new String[] { ImmutableBoard.Direction.NORTHWEST.name(),
				ImmutableBoard.Direction.NORTH.name(), ImmutableBoard.Direction.NORTHEAST.name(),
				ImmutableBoard.Direction.WEST.name(), middle, ImmutableBoard.Direction.EAST.name(),
				ImmutableBoard.Direction.SOUTHWEST.name(), ImmutableBoard.Direction.SOUTH.name(),
				ImmutableBoard.Direction.SOUTHEAST.name() };
		for (int i = 0; i < directions.length; i++) {
			String direction = directions[i];
			Coordinates cord = nodeCoordinates[i];
			ImmutableNode node = Mockito.mock(ImmutableNode.class);
			Mockito.when(node.getOccupantPlayerId()).thenReturn(direction);
			Mockito.when(node.getCoordinates()).thenReturn(cord);
			nodes.add(node);
		}

		// Create board
		System.out.println("Size of nodes is " + nodes.size());
		ImmutableBoard board = new ImmutableBoard(nodes);
		ImmutableNode middleNode = board.getNodeAtCoordinates(new Coordinates(1, 1));
		assertEquals(middle, middleNode.getOccupantPlayerId());

		for (ImmutableBoard.Direction direction : ImmutableBoard.Direction.values()) {
			ImmutableNode nodeInDirection = board.getNextNodeInDirection(middleNode, direction);
			// TODO should assert on coordinates instead of using occupying
			// player?
			assertEquals(direction.name(), nodeInDirection.getOccupantPlayerId());
		}

	}
}