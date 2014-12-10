package kth.game.othello;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.model.GameModel;
import kth.game.othello.model.GameModelFactory;
import kth.game.othello.model.GameState;
import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.player.Player;
import kth.game.othello.player.SimplePlayer;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleOthelloTest {

	final private String invalidMovePlayerID = "InvalidMove";

	final private String player1ID = "player1";
	final private String player2ID = "player2";
	final private String playerInTurnId = "playerInTurn";

	private SimplePlayer mockPlayerWithID(String playerID) {
		SimplePlayer player = Mockito.mock(SimplePlayer.class);
		when(player.getId()).thenReturn(playerID);

		return player;
	}

	private List<Player> mockPlayers() {
		SimplePlayer player1 = mockPlayerWithID(player1ID);
		SimplePlayer player2 = mockPlayerWithID(player2ID);
		SimplePlayer playerInTurn = mockPlayerWithID(playerInTurnId);
		SimplePlayer invalidMovePlayer = mockPlayerWithID(invalidMovePlayerID);

		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(playerInTurn);
		players.add(invalidMovePlayer);

		return players;
	}

	@Test
	public void testGetPlayersShouldReturnCopy() throws Exception {
		GameModel gameModel = mock(GameModel.class);
		GameState gameState = mock(GameState.class);
		when(gameModel.getGameState()).thenReturn(gameState);

		GameModelFactory gameModelFactory = Mockito.mock(GameModelFactory.class);
		when(gameModelFactory.newEmptyGameModel()).thenReturn(gameModel);
		when(gameModelFactory.newGameModel()).thenReturn(gameModel);
		when(gameModelFactory.newGameModel(anyString())).thenReturn(gameModel);

		BoardAdapter mockBoard = Mockito.mock(BoardAdapter.class);

		SimpleOthello othello = new SimpleOthello(null, mockPlayers(), mockBoard, gameModelFactory, null, null, null);

		List<Player> fetchedPlayers = othello.getPlayers();

		// DEBUG HELP: Assert the othello instance actually had players
		assertNotEquals(fetchedPlayers.size(), 0);

		fetchedPlayers.clear();

		// Assert the mutation above did not propagate into the othello instance
		assertNotEquals(othello.getPlayers().size(), 0);
	}

	@Test
	public void testStartShouldBeSynchronized() throws Exception {

		GameModel gameModel = mock(GameModel.class);
		GameState gameState = mock(GameState.class);
		when(gameModel.getGameState()).thenReturn(gameState);

		GameModelFactory gameModelFactory = Mockito.mock(GameModelFactory.class);
		when(gameModelFactory.newEmptyGameModel()).thenReturn(gameModel);
		when(gameModelFactory.newGameModel()).thenReturn(gameModel);
		when(gameModelFactory.newGameModel(anyString())).thenReturn(gameModel);

		BoardAdapter mockBoard = Mockito.mock(BoardAdapter.class);

		SimpleOthello othello = new SimpleOthello(null, mockPlayers(), mockBoard, gameModelFactory, null, null, null);

		othello.start(player1ID);
		verify(mockBoard, times(2)).setBoardState(any(ImmutableBoard.class));

		othello.start();
		verify(mockBoard, times(3)).setBoardState(any(ImmutableBoard.class));
	}
}