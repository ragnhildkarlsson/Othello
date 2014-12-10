package kth.game.othello;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.GameModel;
import kth.game.othello.model.GameState;
import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesAdapter;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tested methods:
 *
 * move() move(params)
 */
public class MoveCoordinatorTest {

	@Test
	public void testMoveShouldSynchronizeMove() throws Exception {

		// Mock dependencies
		BoardAdapter mockBoardAdapter = mock(BoardAdapter.class);
		GameModel mockGameModel = Mockito.mock(GameModel.class);
		MoveStrategy mockMoveStrategy = Mockito.mock(MoveStrategy.class);
		Player mockPlayer = Mockito.mock(Player.class);
		RulesAdapter mockRules = Mockito.mock(RulesAdapter.class);

		// Mock behaviour
		String movePlayerId = "playerId";
		when(mockPlayer.getType()).thenReturn(Player.Type.COMPUTER);
		when(mockPlayer.getId()).thenReturn(movePlayerId);
		when(mockPlayer.getMoveStrategy()).thenReturn(mockMoveStrategy);
		when(mockMoveStrategy.move(anyString(), anyObject(), anyObject())).thenReturn(mock(Node.class));
		when(mockBoardAdapter.getNodeById(anyString())).thenReturn(mock(Node.class));
		when(mockGameModel.getGameState()).thenReturn(mock(GameState.class));
		when(mockGameModel.getPlayerInTurn()).thenReturn(Optional.of(movePlayerId));
		when(mockRules.isMoveValid(anyString(), anyString())).thenReturn(true);

		MoveCoordinator moveCoordinator = new MoveCoordinator(mockRules, mock(GameFinishedNotifier.class),
				mock(MoveNotifier.class));

		// Test valid move without arguments
		moveCoordinator.move(mockPlayer, mockGameModel, mockBoardAdapter);
		// Verify change was made on model
		verify(mockGameModel).move(anyString(), any(Coordinates.class));
		// Verify change was made on boardAdapter
		verify(mockBoardAdapter).setBoardState(any(ImmutableBoard.class));

		// Test valid move with arguments
		moveCoordinator.move(movePlayerId, mock(Node.class), mockGameModel, mockBoardAdapter);
		// Verify change was made on model
		verify(mockGameModel, times(2)).move(anyString(), any(Coordinates.class));
		// Verify change was made on boardAdapter
		verify(mockBoardAdapter, times(2)).setBoardState(any(ImmutableBoard.class));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveShouldTriggerExceptionIfInvalid() throws Exception {
		Rules mockRules = mock(Rules.class);
		GameModel mockGameModel = mock(GameModel.class);
		String movePlayerId = "playerId";
		when(mockRules.isMoveValid(anyString(), anyString())).thenReturn(false);
		when(mockGameModel.getPlayerInTurn()).thenReturn(Optional.of(movePlayerId));
		MoveCoordinator moveCoordinator = new MoveCoordinator(mockRules, mock(GameFinishedNotifier.class),
				mock(MoveNotifier.class));

		// Test invalid move with arguments
		moveCoordinator.move(movePlayerId, mock(Node.class), mockGameModel, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testStrategyRequiringMoveWithHumanPlayerShouldThrowIllegalStateException() throws Exception {
		Player mockHumanPlayer = mock(Player.class);
		GameModel mockGameModel = mock(GameModel.class);
		when(mockGameModel.getPlayerInTurn()).thenReturn(Optional.of("dummy-value"));
		when(mockHumanPlayer.getType()).thenReturn(Player.Type.HUMAN);

		MoveCoordinator moveCoordinator = new MoveCoordinator(null, mock(GameFinishedNotifier.class),
				mock(MoveNotifier.class));

		moveCoordinator.move(mockHumanPlayer, mockGameModel, null);
	}
}
