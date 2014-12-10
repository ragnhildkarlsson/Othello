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
import kth.game.othello.player.PlayerHandler;
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
		PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
		RulesAdapter mockRules = Mockito.mock(RulesAdapter.class);
		String movePlayerId = "playerId";

		// Mock behavior

		when(mockGameModel.getPlayerInTurn()).thenReturn(Optional.of(movePlayerId));
		when(mockPlayerHandler.getPlayer(movePlayerId)).thenReturn(mockPlayer);
		when(mockPlayer.getType()).thenReturn(Player.Type.COMPUTER);
		when(mockPlayer.getMoveStrategy()).thenReturn(mockMoveStrategy);
		when(mockMoveStrategy.move(anyString(), anyObject(), anyObject())).thenReturn(mock(Node.class));
		when(mockBoardAdapter.getNodeById(anyString())).thenReturn(mock(Node.class));
		when(mockGameModel.getGameState()).thenReturn(mock(GameState.class));
		when(mockRules.isMoveValid(anyString(), anyString())).thenReturn(true);

		int callsToSetBoardState = 0;
		MoveCoordinator moveCoordinator = new MoveCoordinator(mockGameModel, mockBoardAdapter, mockPlayerHandler,
				mockRules, mock(GameFinishedNotifier.class), mock(MoveNotifier.class), null);
		// Test valid move without arguments
		moveCoordinator.move();
		callsToSetBoardState++;
		// Verify change was made on model
		verify(mockGameModel, times(1)).move(anyString(), any(Coordinates.class));
		// Verify change was made on boardAdapter
		verify(mockBoardAdapter, times(callsToSetBoardState)).setBoardState(any(ImmutableBoard.class));

		// Test valid move with arguments
		moveCoordinator.move(movePlayerId, "node");
		callsToSetBoardState++;
		// Verify change was made on model
		verify(mockGameModel, times(2)).move(anyString(), any(Coordinates.class));
		// Verify change was made on boardAdapter
		verify(mockBoardAdapter, times(callsToSetBoardState)).setBoardState(any(ImmutableBoard.class));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveShouldTriggerExceptionIfInvalid() throws Exception {
		BoardAdapter mockBoardAdapter = mock(BoardAdapter.class);
		Rules mockRules = mock(Rules.class);
		GameModel mockModel = mock(GameModel.class);
		String movePlayerId = "playerId";

		when(mockModel.getGameState()).thenReturn(mock(GameState.class));
		when(mockModel.getPlayerInTurn()).thenReturn(Optional.of(movePlayerId));
		when(mockRules.isMoveValid(anyString(), anyString())).thenReturn(false);
		when(mockBoardAdapter.getNodeById(anyString())).thenReturn(mock(Node.class));

		MoveCoordinator moveCoordinator = new MoveCoordinator(mockModel, mockBoardAdapter, null, mockRules,
				mock(GameFinishedNotifier.class), mock(MoveNotifier.class), null);

		// Test invalid move with arguments
		moveCoordinator.move(movePlayerId, "invalidMove");
	}

	@Test(expected = IllegalStateException.class)
	public void testStrategyRequiringMoveWithHumanPlayerShouldThrowIllegalStateException() throws Exception {
		BoardAdapter mockBoardAdapter = mock(BoardAdapter.class);
		Player mockHumanPlayer = mock(Player.class);
		PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
		GameModel mockGameModel = mock(GameModel.class);
		String playerId = "playerId";

		when(mockBoardAdapter.getNodeById(anyString())).thenReturn(mock(Node.class));
		when(mockPlayerHandler.getPlayer(anyString())).thenReturn(mockHumanPlayer);
		when(mockGameModel.getPlayerInTurn()).thenReturn(Optional.of(playerId));
		when(mockHumanPlayer.getType()).thenReturn(Player.Type.HUMAN);

		MoveCoordinator moveCoordinator = new MoveCoordinator(mockGameModel, mockBoardAdapter, mockPlayerHandler,
				mock(Rules.class), mock(GameFinishedNotifier.class), mock(MoveNotifier.class), null);

		moveCoordinator.move();
	}
}
