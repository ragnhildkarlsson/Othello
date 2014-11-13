package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class ComputerPlayerTest {

	/**
	 * Tests that given a board with a valid move for the player, then getMove
	 * should return this valid move.
	 */
	@Test
	public void testWhenValidMoveExistsShouldGetMoveReturnValidMove() {
		String testPlayerId = "id";
		ComputerPlayer testPlayer = new ComputerPlayer(testPlayerId, null);

		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		Node unvalidMove = Mockito.mock(Node.class);
		nodesOnBoard.add(unvalidMove);
		Node validMove = Mockito.mock(Node.class);
		nodesOnBoard.add(validMove);

		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		SimpleRules mockRules = Mockito.mock(SimpleRules.class);

		Mockito.when(mockRules.validMove(mockBoard, validMove, testPlayerId)).thenReturn(true);
		Mockito.when(mockRules.validMove(mockBoard, unvalidMove, testPlayerId)).thenReturn(false);

		assertEquals("The valid move should be returned", validMove, testPlayer.getMove(mockRules, mockBoard));
		Mockito.verify(mockBoard).getNodes();
		Mockito.verify(mockRules).validMove(mockBoard, validMove, testPlayerId);
		Mockito.verify(mockRules).validMove(mockBoard, unvalidMove, testPlayerId);
	}

	/**
	 * Test that given a board where no valid moves exist, then should getMove
	 * return null
	 */
	@Test
	public void testWhenNoValidMoveExistsShouldGetMoveReturnNull() {
		String testPlayerId = "id";
		ComputerPlayer testPlayer = new ComputerPlayer(testPlayerId, null);

		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		Node unvalidMove = Mockito.mock(Node.class);
		nodesOnBoard.add(unvalidMove);

		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		SimpleRules mockRules = Mockito.mock(SimpleRules.class);
		Mockito.when(mockRules.validMove(mockBoard, unvalidMove, null)).thenReturn(false);

		assertEquals("Check that null is returned when no valid move exists", null,
				testPlayer.getMove(mockRules, mockBoard));
		Mockito.verify(mockBoard).getNodes();
		Mockito.verify(mockRules).validMove(mockBoard, unvalidMove, testPlayerId);

	}
}
