package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

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

	private static final int BOARD_SIZE = 8;

	@Test
	public void testStartingBoardGeneration() {

		// Check the size of the board
		ImmutableBoardFactory bf = new ImmutableBoardFactory("white", "black");
		ImmutableBoard board = bf.newDefaultStartingBoard();
		List<Node> nodes = board.getNodes();
		int expectedNumberOfNodes = BOARD_SIZE * BOARD_SIZE;
		assertEquals("The size of the new board should be equal to BOARD_SIZE*BOARD_SIZE", expectedNumberOfNodes,
				nodes.size());

		// Check that the coordinate system is correct, the first node in the
		// list should be (0,0),(1,0),(2,0) ... last should be (maxX,maxY)
		int maxCoordinateValue = BOARD_SIZE - 1;
		Node firstNode = nodes.get(0);
		Node secondNode = nodes.get(1);
		Node lastNode = nodes.get(nodes.size() - 1);
		assertTrue("First node in list should be (0, 0)",
				(firstNode.getXCoordinate() == 0) && (firstNode.getYCoordinate() == 0));
		assertTrue("Second node in list should be (1, 0)",
				(secondNode.getXCoordinate() == 1) && (secondNode.getYCoordinate() == 0));
		assertTrue("Last node in list should be (maxCoordinateValue, maxCoordinateValue)",
				(lastNode.getXCoordinate() == maxCoordinateValue) && (lastNode.getYCoordinate() == maxCoordinateValue));

		// Check that the start board is correctly marked
		int higherMiddlePosition = BOARD_SIZE / 2;
		int lowerMiddlePosition = higherMiddlePosition - 1;

		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				// If we are in the middle of the board, nodes should be
				// occupied like
				// - 0 1
				// 0 X O
				// 1 O X
				if ((y == higherMiddlePosition || y == lowerMiddlePosition)
						&& (x == higherMiddlePosition || x == lowerMiddlePosition)) {
					if (y == x) { // equally colored marks should be diagonal to
									// each other.
						assertSame("Should be marked white", "white", board.getNodeAtCoordinates(x, y)
								.getOccupantPlayerId());
					} else {
						assertSame("Should be marked black", "black", board.getNodeAtCoordinates(x, y)
								.getOccupantPlayerId());
					}
				} else {// else the node should be unmarked
					assertSame("Should be unmarked", null, board.getNodeAtCoordinates(x, y).getOccupantPlayerId());
				}
			}
		}

	}

	@Test
	public void testGeneratingNewBoardFromOld() {
		// Generate a board to test swapping on
		Board board;
		int maxIndex = BOARD_SIZE - 1;
		List<Node> nodes = new ArrayList<Node>();
		for (int x = maxIndex; x >= 0; x--) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				Node node = new ImmutableNode(x, y, null);
				nodes.add(node);
			}
		}
		board = new ImmutableBoard(nodes);
		// Generate a list with nodes to swap
		List<Node> nodesToSwap = new ArrayList<Node>();
		Node firstNode = new ImmutableNode(0, 0, null);
		Node lastNode = new ImmutableNode(maxIndex, maxIndex, null);
		nodesToSwap.add(firstNode);
		nodesToSwap.add(lastNode);

		String playerId = "swapped";
		ImmutableBoardFactory boardFactory = new ImmutableBoardFactory("white", "black");
		Board newBoard = boardFactory.newBoardReplacingNodesInBoard(board, nodesToSwap, playerId);
		nodes = newBoard.getNodes();
		assertSame("First node should be swapped", playerId, nodes.get(0).getOccupantPlayerId());
		assertSame("Last node should be swapped", playerId, nodes.get(nodes.size() - 1).getOccupantPlayerId());
		// Check that no other has been swapped
		for (int i = 1; i < nodes.size() - 1; i++) {
			assertSame("no other node should be swapped", null, nodes.get(i).getOccupantPlayerId());

		}
	}
}
