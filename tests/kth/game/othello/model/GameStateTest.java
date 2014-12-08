package kth.game.othello.model;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;

public class GameStateTest {

	/**
	 * Test that an empty optional is returned if the player not is the player
	 * in turn.
	 */
	@Test
	public void testTryMoveReturnOptionalEmptyIfPlayerIsNotPlayerInTurn() {
		String playerInTurn = "1";
		ModelRules mockRules = Mockito.mock(ModelRules.class);
		Mockito.when(mockRules.hasValidMove(null, playerInTurn)).thenReturn(true);
		GameState gameState = new GameState(null, null, mockRules, playerInTurn);
		Optional<GameState> res = gameState.tryMove("2", null);
		assertFalse(res.isPresent());
	}

	/**
	 * Test that when the given player is player1 and player1 is in turn, and
	 * the board is the following
	 * 
	 * <pre>
	 * X o *
	 * X=node occupied by player1
	 * o = node occupied by player2
	 * * not occupied node
	 * </pre>
	 * 
	 * then will a new gameState be returned with the following board X X X
	 */
	@Test
	public void testTryMoveReturnNextGameStateWhenPlayerHasAValidMove() {
		String player1 = "1";
		String player2 = "2";
        ImmutableNode node2 = new ImmutableNode(new Coordinates(1, 0), player2);
		Coordinates playAtCoordinates = new Coordinates(2, 0);

        Set<ImmutableNode> nodesToSwap = new HashSet<>();
		nodesToSwap.add(node2);

		Set<ImmutableNode> nodesActuallyChangedOfThisMove = new HashSet<>();
		nodesActuallyChangedOfThisMove.add(node2);
		ImmutableNode playedNode = new ImmutableNode(playAtCoordinates, player1);
		nodesActuallyChangedOfThisMove.add(playedNode);

		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		ModelRules mockRules = Mockito.mock(ModelRules.class);
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
		// responsibility.
		Mockito.verify(mockBoard).swapNodes(nodesActuallyChangedOfThisMove, player1);

	}
}
