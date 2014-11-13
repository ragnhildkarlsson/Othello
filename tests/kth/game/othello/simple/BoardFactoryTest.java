package kth.game.othello.simple;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class BoardFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void testGeneratingNewBoardFromOldIllegalArgument() {
		BoardFactory bf = new BoardFactory("white", "black");

		// Check that wrongly sized old boards get rejected
		List<Node> oneNode = new ArrayList<Node>();

		Board tinyBoard = Mockito.mock(Board.class);
		Mockito.when(tinyBoard.getNodes()).thenReturn(oneNode);

		assertNull(bf.newBoardReplacingNodesInBoard(tinyBoard, oneNode, "dummy"));

	}

}
