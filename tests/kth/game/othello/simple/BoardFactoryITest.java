package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.List;

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
		System.err.println("Testing");

		BoardFactory bf = new BoardFactory("white", "black");
		SimpleBoard board = bf.newStartingBoard();
		List<Node> nodes = board.getNodes();
		assertEquals("The size of the new board should be equal to BOARD_SIZE*BOARD_SIZE", BOARD_SIZE * BOARD_SIZE,
				nodes.size());
		
//		int higherMiddlePosition = BOARD_SIZE / 2;
//		int lowerMiddlePosition = higherMiddlePosition - 1;
		
//		for (int x = BOARD_SIZE - 1; x >= 0; x--) {
//			for (int y = 0; y < BOARD_SIZE; y++) {
//				// If we are in the middle of the board, nodes should be occupied like
//				// XO
//				// OX
//				if (x == higherMiddlePosition || x == lowerMiddlePosition && y == higherMiddlePosition
//						|| y == lowerMiddlePosition) {
//					if (x == y) {
//						//assertSame("Should be marked by black", "black",  board.getNodeAtCoordinates(x, y).getOccupantPlayerId());
//						assertTrue(board.getNodeAtCoordinates(x, y).getOccupantPlayerId().equals("white"));
//					} else {
//						//assertSame("Should be marked by white", "white",  board.getNodeAtCoordinates(x, y).getOccupantPlayerId());
//						assertTrue(board.getNodeAtCoordinates(x, y).getOccupantPlayerId().equals("black"));
//					}	
//				} else {// else the node should be unmarked
//					//assertSame("Should be unmarked", null,  board.getNodeAtCoordinates(x, y).getOccupantPlayerId());
//				}
//			}
//		}

	}

}
