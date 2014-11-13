package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class ComputerPlayerTest {

	/**
	 * Tests that given a board with a move that is valid for the player should
	 * the player return this valid move is returned for getMove
	 */
	@Test
	public void testWhenValidMoveExistsShouldGetMoveReturnValidMove() {

		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		Node unvalidMove = Mockito.mock(Node.class);
		nodesOnBoard.add(unvalidMove);
		Node validMove = Mockito.mock(Node.class);
		nodesOnBoard.add(validMove);
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		SimpleRules mockRules = Mockito.mock(SimpleRules.class);
		Mockito.when(mockRules.validMove(mockBoard, validMove, null)).thenReturn(true);
		Mockito.when(mockRules.validMove(mockBoard, unvalidMove, null)).thenReturn(false);
		ComputerPlayer testPlayer = new ComputerPlayer(null, null);
		assertEquals("The valid move should be returned", validMove, testPlayer.getMove(mockRules, mockBoard));
		Mockito.verify(mockBoard).getNodes();
		Mockito.verify(mockRules).validMove(mockBoard, validMove, null);
		Mockito.verify(mockRules).validMove(mockBoard, unvalidMove, null);
	}

	/**
	 * Test that given that no valid move exist returns getMove null
	 */
	public void testWhenNoValidMoveExistsShouldGetMoveReturnNull() {
		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		Node unvalidMove = Mockito.mock(Node.class);
		nodesOnBoard.add(unvalidMove);
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		SimpleRules mockRules = Mockito.mock(SimpleRules.class);
		Mockito.when(mockRules.validMove(mockBoard, unvalidMove, null)).thenReturn(false);
		ComputerPlayer testPlayer = new ComputerPlayer(null, null);
		assertEquals("The that null is returned when no valid move exists", null,
				testPlayer.getMove(mockRules, mockBoard));
		Mockito.verify(mockBoard).getNodes();
		Mockito.verify(mockRules).validMove(mockBoard, unvalidMove, null);

	}
}
