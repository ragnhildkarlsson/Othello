package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;
import org.mockito.Mockito;

public class RandomStrategyTest {

	/**
	 * Test that the Strategy returns a null when it does not exist any valid
	 * move for the given player.
	 */
	@Test
	public void testMoveReturnNullWhenNoValidMoveExist() {
		String playerId = "player";

		Random mockRandom = Mockito.mock(Random.class);
		RandomStrategy strategy = new RandomStrategy(mockRandom);
		Rules rules = Mockito.mock(Rules.class);
		Board board = Mockito.mock(Board.class);

		Mockito.when(rules.hasValidMove(playerId)).thenReturn(false);
		assertEquals(null, strategy.move(playerId, rules, board));
	}

	/**
	 * Test that the Strategy returns a move when there does exist a valid move
	 * for the given player.
	 */
	@Test
	public void testMoveReturn() {
		String playerId = "player";
		Random mockRandom = Mockito.mock(Random.class);

		Rules rules = Mockito.mock(Rules.class);
		Board board = Mockito.mock(Board.class);

		List<Node> mockNodeList = new ArrayList<>();
		Node mockNodeBad = Mockito.mock(Node.class);
		Mockito.when(mockNodeBad.getId()).thenReturn("bad");
		Node mockNodeGood = Mockito.mock(Node.class);
		Mockito.when(mockNodeGood.getId()).thenReturn("good");
		mockNodeList.add(mockNodeBad);
		mockNodeList.add(mockNodeGood);
		Mockito.when(board.getNodes()).thenReturn(mockNodeList);

		Mockito.when(rules.hasValidMove(playerId)).thenReturn(true);
		Mockito.when(rules.isMoveValid(playerId, "bad")).thenReturn(false);
		Mockito.when(rules.isMoveValid(playerId, "good")).thenReturn(true);

		Mockito.when(mockRandom.nextInt(1)).thenReturn(0);

		RandomStrategy randomStrategy = new RandomStrategy(mockRandom);
		Node result = randomStrategy.move(playerId, rules, board);
		assertEquals(mockNodeGood, result);

	}

}
