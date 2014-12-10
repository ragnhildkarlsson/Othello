package kth.game.othello.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

/**
 * Tests the ImmutableBoard class with the two friendlies Coordinates and
 * ImmutableNode. These two classes are considered friendlies since they are
 * made up of data and thereby does not need to be mocked.
 * 
 * @author mikael
 * @author Daniel
 * 
 */
public class ImmutableBoardTest {

	private final Optional<String> dummyID = Optional.of("dummyID");
	private final ImmutableBoard dummy8x8Board = generateBoardWithSide(8);

	private Set<ImmutableNode> generateDummyNodes(int numberOfNodesOnSide) {
		// Construct a set of dummy nodes with real coordinates
		Set<ImmutableNode> dummyNodes = new HashSet<>();
		for (int y = 0; y < numberOfNodesOnSide; y++) {
			for (int x = 0; x < numberOfNodesOnSide; x++) {
				// Create a new node
				ImmutableNode dummyNode = new ImmutableNode(new Coordinates(x, y), dummyID);
				dummyNodes.add(dummyNode);
			}
		}

		return dummyNodes;
	}

	private ImmutableBoard generateBoardWithSide(int boardSide) {
		// Create dummy nodes
		Set<ImmutableNode> dummyNodes = generateDummyNodes(boardSide);
		// Create board and retrieve its nodes
		return new ImmutableBoard(dummyNodes);

	}

	@Test
	public void testCompareBoards() {
		Set<ImmutableNode> nodesOnBoard1 = new HashSet<>();
		ImmutableNode node0Board1 = new ImmutableNode(new Coordinates(0, 0), Optional.of("1"));
		ImmutableNode node1Board1 = new ImmutableNode(new Coordinates(1, 1), Optional.of("1"));
		nodesOnBoard1.add(node0Board1);
		nodesOnBoard1.add(node1Board1);

		Set<ImmutableNode> nodesOnBoard2 = new HashSet<>();
		ImmutableNode node0Board2 = new ImmutableNode(new Coordinates(0, 0), Optional.of("2"));
		ImmutableNode node1Board2 = new ImmutableNode(new Coordinates(1, 1), Optional.of("1"));
		nodesOnBoard2.add(node0Board2);
		nodesOnBoard2.add(node1Board2);

		ImmutableBoard board1 = new ImmutableBoard(nodesOnBoard1);
		ImmutableBoard board2 = new ImmutableBoard(nodesOnBoard2);

		Set<Coordinates> diffCoords = ImmutableBoard.compare(board1, board2);

		Set<Coordinates> correctDiff = new HashSet<>();
		correctDiff.add(new Coordinates(0, 0));

		assertEquals(correctDiff, diffCoords);

	}

	@Test
	public void testGetNodesShouldReturnCopy() throws Exception {

		ImmutableBoard board = generateBoardWithSide(2);
		Set<ImmutableNode> retrievedNodes = board.getNodes();

		// Create one node to try and insert
		ImmutableNode specificNode = new ImmutableNode(new Coordinates(3, 3), Optional.of("SpecificNode"));

		retrievedNodes.add(specificNode);

		for (ImmutableNode node : board.getNodes()) {
			if (node.getOccupantPlayerId().equals(specificNode.getOccupantPlayerId().get())) {
				fail("SimpleBoard should be immutable but could be mutated through its returned list of nodes.");
			}
		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfRightBoundsShouldThrowIlleagalArgumentException() throws Exception {
		Coordinates cord = new Coordinates(8, 0);
		dummy8x8Board.getNodeAtCoordinates(cord);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfBottomBoundsShouldThrowIlleagalArgumentException() throws Exception {
		Coordinates cord = new Coordinates(0, -1);
		dummy8x8Board.getNodeAtCoordinates(cord);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfTopBoundsShouldThrowIlleagalArgumentException() throws Exception {
		Coordinates cord = new Coordinates(0, 8);
		dummy8x8Board.getNodeAtCoordinates(cord);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNodeOutOfLeftBoundsShouldThrowIlleagalArgumentException() throws Exception {
		Coordinates cord = new Coordinates(-1, 0);
		dummy8x8Board.getNodeAtCoordinates(cord);
	}

	@Test
	public void testGetNodeAtCoordinatesShouldReturnCorrectNode() throws Exception {

		// First node to look for at position (0,0)
		Coordinates zeroZeroCord = new Coordinates(0, 0);
		ImmutableNode zeroZeroNode = new ImmutableNode(zeroZeroCord, Optional.of("dskjjhd687"));

		// Second node to look for at position (7,7)
		Coordinates sevenSevenCord = new Coordinates(7, 7);
		ImmutableNode sevenSevenNode = new ImmutableNode(sevenSevenCord, Optional.of("dsjkhds728563"));

		Set<ImmutableNode> dummyNodes = new HashSet<>();
		// Add our new version of nodes with the correct id
		dummyNodes.add(zeroZeroNode);
		dummyNodes.add(sevenSevenNode);

		// Create board
		ImmutableBoard board = new ImmutableBoard(dummyNodes);

		// Test board
		assertEquals(zeroZeroNode.getOccupantPlayerId(), board.getNodeAtCoordinates(zeroZeroCord).getOccupantPlayerId());
		assertEquals(sevenSevenNode.getOccupantPlayerId(), board.getNodeAtCoordinates(sevenSevenCord)
				.getOccupantPlayerId());

	}

	@Test
	public void testGetNextNodeInDirection() throws Exception {

		/*-
		 * Construct a board with nodeIDs like these:
		 * NW N NE
		 * W D E Where NW, N, SE etc. are the 8 directions
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
			ImmutableNode node = new ImmutableNode(cord, Optional.of(direction));
			nodes.add(node);
		}

		// Create board
		ImmutableBoard board = new ImmutableBoard(nodes);
		ImmutableNode middleNode = board.getNodeAtCoordinates(new Coordinates(1, 1));
		assertEquals(middle, middleNode.getOccupantPlayerId().get());

		for (ImmutableBoard.Direction direction : ImmutableBoard.Direction.values()) {
			Optional<ImmutableNode> nodeInDirection = board.getNextNodeInDirection(middleNode, direction);
			assertEquals(direction.name(), nodeInDirection.get().getOccupantPlayerId().get());
		}
	}

	@Test
	public void testGetCopyWithNodeSwapped() {
		// Create a simple board
		ImmutableNode node1 = new ImmutableNode(new Coordinates(0, 0), dummyID);
		ImmutableNode node2 = new ImmutableNode(new Coordinates(1, 0), dummyID);
		Set<ImmutableNode> nodes = new HashSet<>();
		nodes.add(node1);
		nodes.add(node2);

		ImmutableBoard oldBoard = new ImmutableBoard(nodes);

		Set<ImmutableNode> nodeToSwap = new HashSet<>();
		nodeToSwap.add(node2);
		String swapID = "swapped";
		ImmutableBoard newBoard = oldBoard.swapNodes(nodeToSwap, swapID);
		// Check that the new board node 1 is unchanged and node 2 is changed
		assertEquals(node1, newBoard.getNodeAtCoordinates(new Coordinates(0, 0)));
		assertEquals(swapID, newBoard.getNodeAtCoordinates(new Coordinates(1, 0)).getOccupantPlayerId().get());

	}
}