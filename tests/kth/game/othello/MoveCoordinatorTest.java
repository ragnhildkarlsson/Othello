package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.*;
import kth.game.othello.player.Player;
import kth.game.othello.player.SimplePlayer;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesAdapter;
import kth.game.othello.score.Score;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tested methods:
 *
 * move()
 * move(params)
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
        when(mockPlayer.getType()).thenReturn(Player.Type.COMPUTER);
        when(mockPlayer.getMoveStrategy()).thenReturn(mockMoveStrategy);
        when(mockMoveStrategy.move(anyString(),anyObject(),anyObject())).thenReturn(mock(Node.class));
        when(mockBoardAdapter.getNodeById(anyString())).thenReturn(mock(Node.class));
        when(mockGameModel.getGameState()).thenReturn(mock(GameState.class));
        when(mockRules.isMoveValid(anyString(), anyString())).thenReturn(true);

        MoveCoordinator moveCoordinator = new MoveCoordinator(mockRules);

        // Test valid move without arguments
        moveCoordinator.move(mockPlayer, mockGameModel, mockBoardAdapter);
        // Verify change was made on model
        verify(mockGameModel).move(anyString(),any(Coordinates.class));
        // Verify change was made on boardAdapter
        verify(mockBoardAdapter).setBoardState(any(ImmutableBoard.class));

        // Test valid move with arguments
        moveCoordinator.move(null,mock(Node.class),mockGameModel,mockBoardAdapter);
        // Verify change was made on model
        verify(mockGameModel, times(2)).move(anyString(), any(Coordinates.class));
        // Verify change was made on boardAdapter
        verify(mockBoardAdapter, times(2)).setBoardState(any(ImmutableBoard.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveShouldTriggerExceptionIfInvalid() throws Exception {
        Rules mockRules = mock(Rules.class);
        when(mockRules.isMoveValid(anyString(),anyString())).thenReturn(false);

        MoveCoordinator moveCoordinator = new MoveCoordinator(mockRules);

        // Test invalid move with arguments
        moveCoordinator.move(null, mock(Node.class), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testStrategyRequiringMoveWithHumanPlayerShouldThrowIllegalStateException() throws Exception {
        Player mockHumanPlayer = mock(Player.class);
        when(mockHumanPlayer.getType()).thenReturn(Player.Type.HUMAN);

        MoveCoordinator moveCoordinator = new MoveCoordinator(null);

        moveCoordinator.move(mockHumanPlayer, null, null);
    }
}
