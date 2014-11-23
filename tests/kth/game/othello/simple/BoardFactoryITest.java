package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.simple.model.Coordinates;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableBoardFactory;
import kth.game.othello.simple.model.ImmutableNode;

import org.junit.Test;

/**
 * Performs integration tests on BoardFactory, SimpleBoard and SimpleNode.
 * 
 * @author mikael
 * 
 */
public class BoardFactoryITest {

	private static final int DEFAULT_BOARD_SIZE = 8;

	// @Test TODO ska detta finnas och isåf hur?
	// public void testStartingBoardGeneration() {
	//
	// // Check the size of the board
	// ImmutableBoardFactory bf = new ImmutableBoardFactory();
	// ImmutableBoard board = bf.newDefaultStartingBoard();
	// Set<ImmutableNode> nodes = board.getNodes();
	// int expectedNumberOfNodes = DEFAULT_BOARD_SIZE * DEFAULT_BOARD_SIZE;
	// assertEquals("The size of the new board should be equal to DEFAULT_BOARD_SIZE^2",
	// expectedNumberOfNodes,
	// nodes.size());
	//
	//
	//
	// // Check that the coordinate system is correct, the first node in the
	// // list should be (0,0),(1,0),(2,0) ... last should be (maxX,maxY)
	// int maxCoordinateValue = DEFAULT_BOARD_SIZE - 1;
	// ImmutableNode firstNode = nodes.get(0);
	// Node secondNode = nodes.get(1);
	// Node lastNode = nodes.get(nodes.size() - 1);
	// assertTrue("First node in list should be (0, 0)",
	// (firstNode.getXCoordinate() == 0) && (firstNode.getYCoordinate() == 0));
	// assertTrue("Second node in list should be (1, 0)",
	// (secondNode.getXCoordinate() == 1) && (secondNode.getYCoordinate() ==
	// 0));
	// assertTrue("Last node in list should be (maxCoordinateValue, maxCoordinateValue)",
	// (lastNode.getXCoordinate() == maxCoordinateValue) &&
	// (lastNode.getYCoordinate() == maxCoordinateValue));
	//
	// // Check that the start board is correctly marked
	// int higherMiddlePosition = DEFAULT_BOARD_SIZE / 2;
	// int lowerMiddlePosition = higherMiddlePosition - 1;
	//
	// for (int y = 0; y < DEFAULT_BOARD_SIZE; y++) {
	// for (int x = 0; x < DEFAULT_BOARD_SIZE; x++) {
	// // If we are in the middle of the board, nodes should be
	// // occupied like
	// // - 0 1
	// // 0 X O
	// // 1 O X
	// if ((y == higherMiddlePosition || y == lowerMiddlePosition)
	// && (x == higherMiddlePosition || x == lowerMiddlePosition)) {
	// if (y == x) { // equally colored marks should be diagonal to
	// // each other.
	// assertSame("Should be marked white", "white",
	// board.getNodeAtCoordinates(x, y)
	// .getOccupantPlayerId());
	// } else {
	// assertSame("Should be marked black", "black",
	// board.getNodeAtCoordinates(x, y)
	// .getOccupantPlayerId());
	// }
	// } else {// else the node should be unmarked
	// assertSame("Should be unmarked", null, board.getNodeAtCoordinates(x,
	// y).getOccupantPlayerId());
	// }
	// }
	// }
	//
	// }

	@Test
	public void testGeneratingNewBoardFromOld() {
		// Generate a board to test swapping on
		ImmutableBoard board;
		int maxIndex = DEFAULT_BOARD_SIZE - 1;
		Set<ImmutableNode> nodes = new HashSet<>();
		for (int x = maxIndex; x >= 0; x--) {
			for (int y = 0; y < DEFAULT_BOARD_SIZE; y++) {
				Coordinates cord = new Coordinates(x, y);
				ImmutableNode node = new ImmutableNode(cord, null);
				nodes.add(node);
			}
		}
		board = new ImmutableBoard(nodes);
		// Generate a list with nodes to swap
		Set<ImmutableNode> nodesToSwap = new HashSet<>();
		ImmutableNode firstNode = new ImmutableNode(new Coordinates(0, 0), null);
		ImmutableNode lastNode = new ImmutableNode(new Coordinates(maxIndex, maxIndex), null);
		nodesToSwap.add(firstNode);
		nodesToSwap.add(lastNode);

		String playerId = "swapped";
		ImmutableBoardFactory boardFactory = new ImmutableBoardFactory();
		ImmutableBoard newBoard = boardFactory.newBoardReplacingNodesInBoard(board, nodesToSwap, playerId);
		assertSame("First node should be swapped", playerId, newBoard.getNodeAtCoordinates(new Coordinates(0, 0)));
		assertSame("Last node should be swapped", playerId,
				newBoard.getNodeAtCoordinates(new Coordinates(maxIndex, maxIndex)));
		// Check that no other has been swapped
		for (int y = 1; y < maxIndex; y++) {
			for (int x = 1; x < maxIndex; x++) {
				assertSame("no other node should be swapped", null,
						newBoard.getNodeAtCoordinates(new Coordinates(x, y)));
			}
		}
	}
}
