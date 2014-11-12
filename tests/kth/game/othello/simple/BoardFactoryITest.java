package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

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
		BoardFactory bf = new BoardFactory("white", "black");
		SimpleBoard board = bf.newStartingBoard();
		List<Node> nodes = board.getNodes();
		int numberOfNodes = BOARD_SIZE * BOARD_SIZE;
		assertEquals("The size of the new board should be equal to BOARD_SIZE*BOARD_SIZE", numberOfNodes, nodes.size());

		// Check that the Cartesian coordinate system is used properly
		int maxCoordinateValue = BOARD_SIZE - 1;
		Node firstNode = nodes.get(0);
		Node lastNode = nodes.get(numberOfNodes - 1);
		assertTrue("First node in list should be (maxCoordinateValue, 0)",
				(firstNode.getXCoordinate() == maxCoordinateValue) && (firstNode.getYCoordinate() == 0));
		assertTrue("Last node in list should be (0, maxCoordinateValue)",
				(lastNode.getXCoordinate() == 0) && (lastNode.getYCoordinate() == maxCoordinateValue));

		// Check that the start board is correctly marked
		int higherMiddlePosition = BOARD_SIZE / 2;
		int lowerMiddlePosition = higherMiddlePosition - 1;

		for (int x = BOARD_SIZE - 1; x >= 0; x--) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				// If we are in the middle of the board, nodes should be
				// occupied like
				// 1 X O
				// 0 O X
				// - 0 1
				System.err.println(" x and y is " + x + " " + y);
				if ((x == higherMiddlePosition || x == lowerMiddlePosition)
						&& (y == higherMiddlePosition || y == lowerMiddlePosition)) {
					if (x == y) { // equally colored marks should be diagonal to
									// each other.
						assertSame("Should be marked black", "black", board.getNodeAtCoordinates(x, y)
								.getOccupantPlayerId());
					} else {
						assertSame("Should be marked white", "white", board.getNodeAtCoordinates(x, y)
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
				Node node = new SimpleNode(x, y, null);
				nodes.add(node);
			}
		}
		board = new SimpleBoard(nodes);
		// Generate a list with nodes to swap
		List<Node> nodesToSwap = new ArrayList<Node>();
		Node firstNode = new SimpleNode(maxIndex, 0, null);
		Node lastNode = new SimpleNode(0, maxIndex, null);
		nodesToSwap.add(firstNode);
		nodesToSwap.add(lastNode);

		String playerId = "swapped";
		BoardFactory boardFactory = new BoardFactory("white", "black");
		Board newBoard = boardFactory.newBoardReplacingNodesInBoard(board, nodesToSwap, playerId);
		nodes = newBoard.getNodes();
		assertSame("First node should be swapped", playerId, nodes.get(0).getOccupantPlayerId());
		assertSame("Last node should be swapped", playerId, nodes.get(nodes.size() - 1).getOccupantPlayerId());
	}
}
