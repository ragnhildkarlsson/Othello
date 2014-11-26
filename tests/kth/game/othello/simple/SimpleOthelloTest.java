package kth.game.othello.simple;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kth.game.othello.Othello;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.score.Score;
import kth.game.othello.simple.model.*;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SimpleOthelloTest {

	final private String invalidMovePlayerID = "InvalidMove";

	final private String player1ID = "player1";
	final private String player2ID = "player2";
    final private String playerInTurnId = "playerInTurn";

	final private String nodeToPlayAtID = "nodeToPlayAt";
	final private String otherNodeID = "otherNode";

	private Node mockNodeWithId(String id) {
		Node node = Mockito.mock(Node.class);
		when(node.getId()).thenReturn(id);
		return node;
	}

	private SimplePlayer mockPlayerWithID(String playerID) {
		SimplePlayer player = Mockito.mock(SimplePlayer.class);
		when(player.getType()).thenReturn(Player.Type.COMPUTER);
		when(player.getId()).thenReturn(playerID);

        Node nodeToPlatAt = mockNodeWithId(nodeToPlayAtID);
        MoveStrategy strategy = Mockito.mock(MoveStrategy.class);
        when(strategy.move(anyString(),any(Othello.class))).thenReturn(nodeToPlatAt);

        when(player.getMoveStrategy()).thenReturn(strategy);

		return player;
	};

	private SimpleOthello othelloWithMockedDependencies() {
        List<Player> players = mockPlayers();

		Node nodeToPlayAt = mockNodeWithId(nodeToPlayAtID);
		Node otherNode = mockNodeWithId(otherNodeID);

		BoardAdapter mockBoard = Mockito.mock(BoardAdapter.class);
		when(mockBoard.getNodeById(nodeToPlayAtID)).thenReturn(Optional.of(nodeToPlayAt));
		when(mockBoard.getNodeById(otherNodeID)).thenReturn(Optional.of(otherNode));

        GameModelFactory mockFactory = mockFactory();

		List<Node> mockNodesToSwap = new ArrayList<>();
		mockNodesToSwap.add(mockNodeWithId(otherNodeID));

        Score mockScore = Mockito.mock(Score.class);

		return new SimpleOthello(players, mockBoard, mockFactory, mockScore);
	}

    private GameModel mockGameModel() {
        GameModel mockModel = Mockito.mock(GameModel.class);
        when(mockModel.getPlayerInTurn()).thenReturn(playerInTurnId);
        return mockModel;
    }

    private GameModelFactory mockFactory() {
        GameModel mockModel = mockGameModel();
        GameState gameState = mock(GameState.class);
        when(mockModel.getGameState()).thenReturn(gameState);

        GameModelFactory gameModelFactory = Mockito.mock(GameModelFactory.class);
        when(gameModelFactory.getNewGameModel(anyString())).thenReturn(mockModel);

        return gameModelFactory;
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
	public void testGetPlayerInTurnShouldReturnPlayer() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

        assertEquals(othello.getPlayerInTurn().getId(), playerInTurnId);
	}

	@Test
	public void testGetPlayersShouldReturnCopy() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

        List<Player> fetchedPlayers = othello.getPlayers();

        // DEBUG HELP: Assert the othello instance actually had players
        assertNotEquals(fetchedPlayers.size(), 0);

        fetchedPlayers.clear();

		// Assert the mutation above did not propagate into the othello instance
		assertNotEquals(othello.getPlayers().size(), 0);
	}

	@Test
	public void testMoveShouldUpdateModelAndAdapter() throws Exception {
        BoardAdapter boardAdapter = mock(BoardAdapter.class);
        Node nodeToPlayAt = mockNodeWithId(nodeToPlayAtID);
        when(boardAdapter.getNodeById(anyString())).thenReturn(Optional.of(nodeToPlayAt));

        GameState gameState = mock(GameState.class);

        GameModel mockModel = mockGameModel();
        when(mockModel.getGameState()).thenReturn(gameState);
        when(mockModel.isMoveValid(anyString(), any(Coordinates.class))).thenReturn(true);

        GameModelFactory gameModelFactory = Mockito.mock(GameModelFactory.class);
        when(gameModelFactory.getNewGameModel(anyString())).thenReturn(mockModel);

        SimpleOthello othello = new SimpleOthello(mockPlayers(), boardAdapter, gameModelFactory, null);

		// Test valid move without arguments
		othello.start();
        reset(boardAdapter);
        when(boardAdapter.getNodeById(anyString())).thenReturn(Optional.of(nodeToPlayAt));
		othello.move();

        verify(boardAdapter).setBoardState(any(ImmutableBoard.class));

		// Test valid move with arguments
		othello.move(playerInTurnId, nodeToPlayAtID);

		verify(boardAdapter, times(2)).setBoardState(any(ImmutableBoard.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveShouldTriggerExceptionIfInvalid() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		// Test invalid move with arguments
		othello.start();
		othello.move(invalidMovePlayerID, nodeToPlayAtID);
	}

	@Test
	public void testStartWithArgumentShouldSetGivenPlayer() throws Exception {

        GameModel gameModel = mockGameModel();
        GameState gameState = mock(GameState.class);
        when(gameModel.getGameState()).thenReturn(gameState);

        GameModelFactory gameModelFactory = Mockito.mock(GameModelFactory.class);
        when(gameModelFactory.getNewGameModel(anyString())).thenReturn(gameModel);

        BoardAdapter mockBoard = Mockito.mock(BoardAdapter.class);

		SimpleOthello othello = new SimpleOthello(mockPlayers(), mockBoard, gameModelFactory, null);

        verify(gameModelFactory).getNewGameModel(anyString());
        reset(gameModelFactory);
        when(gameModelFactory.getNewGameModel(anyString())).thenReturn(gameModel);

        othello.start(player1ID);

        verify(gameModelFactory).getNewGameModel(player1ID);

	}
}