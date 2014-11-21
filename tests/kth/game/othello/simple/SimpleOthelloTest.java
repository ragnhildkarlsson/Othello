package kth.game.othello.simple;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import kth.game.othello.simple.board.BoardFactory;
import kth.game.othello.simple.board.ImmutableBoard;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;

public class SimpleOthelloTest {

	final private String invalidMovePlayerID = "InvalidMove";

	final private String player1ID = "player1";
	final private String player2ID = "player2";

	final private String nodeToPlayAtID = "nodeToPlayAt";
	final private String otherNodeID = "otherNode";

	private Node mockNodeWithId(String id) {
		Node node = Mockito.mock(Node.class);
		when(node.getId()).thenReturn(id);
		return node;
	}

	private ComputerPlayer mockComputerPlayerWithID(String playerID) {
		ComputerPlayer player = Mockito.mock(ComputerPlayer.class);
		when(player.getType()).thenReturn(Player.Type.COMPUTER);
		when(player.getId()).thenReturn(playerID);
		Node nodeToPlatAt = mockNodeWithId(nodeToPlayAtID);
		when(player.getMove(any(SimpleRules.class), any(ImmutableBoard.class))).thenReturn(nodeToPlatAt);
		return player;
	};

	private SimpleOthello othelloWithMockedDependencies() {
		ComputerPlayer player1 = mockComputerPlayerWithID(player1ID);
		ComputerPlayer player2 = mockComputerPlayerWithID(player2ID);

		Node nodeToPlayAt = mockNodeWithId(nodeToPlayAtID);
		Node otherNode = mockNodeWithId(otherNodeID);

		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		when(mockBoard.getNodeById(nodeToPlayAtID)).thenReturn(nodeToPlayAt);
		when(mockBoard.getNodeById(otherNodeID)).thenReturn(otherNode);

		BoardFactory mockFactory = Mockito.mock(BoardFactory.class);
		when(mockFactory.newDefaultStartingBoard()).thenReturn(mockBoard);
		when(mockFactory.newBoardReplacingNodesInBoard(any(Board.class), anyList(), anyString())).thenReturn(mockBoard);

		SimpleRules mockRules = Mockito.mock(SimpleRules.class);
		when(mockRules.validMove(any(ImmutableBoard.class), any(Node.class), eq(invalidMovePlayerID))).thenReturn(false);
		when(
				mockRules.validMove(any(ImmutableBoard.class), any(Node.class),
						AdditionalMatchers.not(eq(invalidMovePlayerID)))).thenReturn(true);
		List<Node> mockNodesToSwap = new ArrayList<>();
		mockNodesToSwap.add(mockNodeWithId(otherNodeID));
		when(mockRules.getNodesToSwap(any(ImmutableBoard.class), any(Node.class), anyString()))
				.thenReturn(mockNodesToSwap);

		return new SimpleOthello(mockFactory, mockRules, player1, player2);
	}

	@Test
	public void testGetPlayerInTurnShouldReturnStartingPlayer() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		othello.start(player2ID);

		assertEquals(player2ID, othello.getPlayerInTurn().getId());
	}

	@Test
	public void testGetPlayerInTurnShouldUpdateOnMove() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		othello.start(player2ID);
		othello.move();
		assertEquals(player1ID, othello.getPlayerInTurn().getId());

		othello.move();
		assertEquals(player2ID, othello.getPlayerInTurn().getId());
	}

	@Test
	public void testGetPlayersShouldReturnCopy() throws Exception {
		ComputerPlayer player = mockComputerPlayerWithID(null);

		SimpleOthello othello = new SimpleOthello(null, null, player, player);

		List<Player> players = othello.getPlayers();

		players.clear();

		// Assert the mutation above did not propagate into the othello instance
		assertNotEquals(othello.getPlayers().size(), 0);
	}

	@Test
	public void testMoveShouldReturnSwappedNodesIfValid() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		// Test valid move without arguments
		othello.start();
		List<Node> nodes = othello.move();
		boolean otherNodeWasThere = false;
		boolean nodeToPlayAtWasThere = false;

		for (Node node : nodes) {
			if (node.getId().equals(otherNodeID)) {
				otherNodeWasThere = true;
			}
			if (node.getId().equals(nodeToPlayAtID)) {
				nodeToPlayAtWasThere = true;
			}
		}

		assertTrue(nodeToPlayAtWasThere);
		assertTrue(otherNodeWasThere);

		// Test valid move with arguments
		othello.start();
		nodes = othello.move(player1ID, nodeToPlayAtID);

		otherNodeWasThere = false;
		nodeToPlayAtWasThere = false;

		for (Node node : nodes) {
			if (node.getId().equals(otherNodeID)) {
				otherNodeWasThere = true;
			}
			if (node.getId().equals(nodeToPlayAtID)) {
				nodeToPlayAtWasThere = true;
			}
		}

		assertTrue(nodeToPlayAtWasThere);
		assertTrue(otherNodeWasThere);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveShouldTriggerExceptionIfInvalid() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		// Test invalid move with arguments
		othello.start();
		othello.move(invalidMovePlayerID, nodeToPlayAtID);
	}

	@Test
	public void testStartShouldRandomizePlayer() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		boolean player1HasStarted = false;
		boolean player2HasStarted = false;
		int triesLimit = 150;

		for (int i = 0; i < triesLimit; i++) {
			othello.start();

			if (othello.getPlayerInTurn().getId().equals(player1ID)) {
				player1HasStarted = true;
			}
			if (othello.getPlayerInTurn().getId().equals(player2ID)) {
				player2HasStarted = true;
			}

			if (player1HasStarted && player2HasStarted) {
				break;
			}
		}

		assertTrue(player1HasStarted);
		assertTrue(player2HasStarted);

	}

	@Test
	public void testStartWithArgumentShouldSetGivenPlayer() throws Exception {
		SimpleOthello othello = othelloWithMockedDependencies();

		othello.start(player1ID);
		assertEquals(othello.getPlayerInTurn().getId(), player1ID);

		othello.start(player2ID);
		assertEquals(othello.getPlayerInTurn().getId(), player2ID);
	}
}