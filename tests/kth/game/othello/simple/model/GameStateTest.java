package kth.game.othello.simple.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

public class GameStateTest {

	/**
	 * Test that an empty optional is returned if the player not is the player
	 * in turn.
	 */
	@Test
	public void testTryMoveReturnOptionalEmptyIfPlayerIsNotPlayerInTurn() {
		String playerInTurn = "1";
		Rules mockRules = Mockito.mock(Rules.class);
		Mockito.when(mockRules.hasValidMove(null, playerInTurn)).thenReturn(true);
		GameState gameState = new GameState(null, null, mockRules, playerInTurn);
		Optional<GameState> res = gameState.tryMove("2", null);
		assertEquals(Optional.empty(), res);
	}

	/**
	 * Test that when the given player is player1 and player1 is in turn, and
	 * the board is the following
	 * 
	 * <pre>
	 * X o *
	 * </pre>
	 * 
	 * X=node occupied by player1 o = node occupied by player2 * not occupied
	 * node Will a new gameState be returned with the following board X X X
	 */
	@Test
	public void testTryMoveReturnNextGameStateWhenPlayerHaveAValidMove() {
		String player1 = "1";
		String player2 = "2";
		ImmutableNode node1 = new ImmutableNode(new Coordinates(0, 0), player1);
		ImmutableNode node2 = new ImmutableNode(new Coordinates(1, 0), player2);
		Coordinates playAtCoordinates = new Coordinates(2, 0);
		ImmutableNode node3 = new ImmutableNode(playAtCoordinates, null);

		Set<ImmutableNode> nodesToSwap = new HashSet<ImmutableNode>();
		nodesToSwap.add(node2);

		Set<ImmutableNode> nodesActuallyChangedOfThisMove = new HashSet<ImmutableNode>();
		nodesActuallyChangedOfThisMove.add(node2);
		ImmutableNode playedNode = new ImmutableNode(playAtCoordinates, player1);
		nodesActuallyChangedOfThisMove.add(playedNode);

		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		Rules mockRules = Mockito.mock(Rules.class);
		TurnCalculator mockTurnCalculator = Mockito.mock(TurnCalculator.class);
		Mockito.when(mockRules.hasValidMove(mockBoard, player1)).thenReturn(true);
		GameState gameState = new GameState(mockBoard, mockTurnCalculator, mockRules, player1);
		Mockito.verify(mockRules).hasValidMove(mockBoard, player1);

		Mockito.when(mockRules.getNodesToSwap(mockBoard, playAtCoordinates, player1)).thenReturn(nodesToSwap);
		Mockito.when(mockBoard.swapNodes(nodesActuallyChangedOfThisMove, player1)).thenReturn(mockBoard);
		gameState.tryMove(player1, playAtCoordinates);
		Mockito.verify(mockRules).getNodesToSwap(mockBoard, playAtCoordinates, player1);

		// Verify that mockBoard.swapNodes are called with a set of nodes that
		// contains both node 2 and playedNode. The rest is ImmutableBoards
		// responsibilty.
		Mockito.verify(mockBoard).swapNodes(nodesActuallyChangedOfThisMove, player1);

	}
}
