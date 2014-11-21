package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.board.Node;
import kth.game.othello.simple.board.ImmutableBoard;
import kth.game.othello.simple.board.ImmutableBoard.Direction;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleRulesTest {

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 * x o *
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  player 1 could play on the non occupied node
	 * </pre>
	 **/
	@Test
	public void testValidMoveOnBoardEdgeShouldBeValid() {
		String player1Id = "1";
		String player2Id = "2";
		ImmutableBoard mockBoard = getMinimalMockedBoard(player1Id, player2Id);
		Node playAtNode = mockBoard.getNodes().get(2);
		SimpleRules rules = new SimpleRules();
		boolean res = rules.validMove(mockBoard, playAtNode, player1Id);
		for (Direction dir : Direction.values()) {
			Mockito.verify(mockBoard).getNextNodeInDirection(playAtNode, dir);
		}
		assertEquals(true, res);

	}

	/**
	 * 
	 * <pre>
	 * Test that given a board with the following nodes
	 *   0 1 2 3 4 5 6
	 * 0   	 *   o
	 * 1 x o o * o x
	 * 2     o * o
	 * 3   o   x   *
	 * 4             x
	 *  
	 *  where:
	 *  x mark nodes occupied by xPlayer
	 *  o mark nodes occupied by oPlayer
	 *  * mark nodes not occupied by any player
	 *  getNodesToSwap returns a list with the following nodes (with coordinates) (1,1) (2,1) (4,1)
	 *  if player 1 plays on (3,1).
	 * </pre>
	 */
	@Test
	public void testValidMoveShouldGiveCorrectNumberOfSwapedNodes() {
		String oPlayerID = "o";
		String xPlayerID = "x";
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);

		// mock nodes
		Node boardNode20 = getMockedNode(null);
		Node boardNode40 = getMockedNode(oPlayerID);
		Node boardNode01 = getMockedNode(xPlayerID);
		Node boardNode11 = getMockedNode(oPlayerID);
		Node boardNode21 = getMockedNode(oPlayerID);
		Node playAtNode = getMockedNode(null);
		Node boardNode41 = getMockedNode(oPlayerID);
		Node boardNode51 = getMockedNode(xPlayerID);
		Node boardNode22 = getMockedNode(oPlayerID);
		Node boardNode32 = getMockedNode(null);
		Node boardNode42 = getMockedNode(oPlayerID);
		Node boardNode13 = getMockedNode(oPlayerID);
		Node boardNode33 = getMockedNode(xPlayerID);
		Node boardNode53 = getMockedNode(null);
		Node boardNode64 = getMockedNode(xPlayerID);

		// set up behavior for getNextNodeInDirection;

		for (Direction dir : Direction.values()) {
			switch (dir) {
			case NORTHEAST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode40);
				break;
			case EAST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode41);
				break;
			case WEST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode21);
				break;
			case NORTHWEST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode20);
				break;
			case SOUTH:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode32);
				break;
			case SOUTHWEST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode22);
				break;
			case SOUTHEAST:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(boardNode42);
				break;
			default:
				Mockito.when(mockBoard.getNextNodeInDirection(playAtNode, dir)).thenReturn(null);
				break;

			}
		}

		Mockito.when(mockBoard.getNextNodeInDirection(boardNode40, Direction.NORTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode41, Direction.EAST)).thenReturn(boardNode51);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode21, Direction.WEST)).thenReturn(boardNode11);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode11, Direction.WEST)).thenReturn(boardNode01);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode22, Direction.SOUTHWEST)).thenReturn(boardNode13);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode13, Direction.SOUTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode32, Direction.SOUTH)).thenReturn(boardNode33);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode42, Direction.SOUTHEAST)).thenReturn(boardNode53);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode53, Direction.SOUTHEAST)).thenReturn(boardNode64);

		// Check that the right nodes (and no other nodes) where listed as nodes
		// to swap
		SimpleRules rules = new SimpleRules();
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, xPlayerID).contains(boardNode41));
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, xPlayerID).contains(boardNode21));
		assertEquals(true, rules.getNodesToSwap(mockBoard, playAtNode, xPlayerID).contains(boardNode11));
		assertEquals(3, rules.getNodesToSwap(mockBoard, playAtNode, xPlayerID).size());

		final int callsToGetNodesToSwap = 4;

		// Verify that all calls to getNextNodeInDirection() were made
		for (Direction dir : Direction.values()) {
			Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(playAtNode, dir);
		}
		Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(boardNode40,
				Direction.NORTHEAST);
		Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(boardNode41,
				Direction.EAST);
		Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(boardNode21,
				Direction.WEST);
		Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(boardNode11,
				Direction.WEST);
		Mockito.verify(mockBoard, Mockito.times(callsToGetNodesToSwap)).getNextNodeInDirection(boardNode42,
				Direction.SOUTHEAST);

	}

	/**
	 * Test that given a board with an occupied node it's an invalid move for
	 * any player to play at this node.
	 */
	@Test
	public void testMoveOnOccupiedNodeShouldBeInvalid() {
		String player1Id = "1";
		String player2Id = "2";
		Node boardNode = getMockedNode(player1Id);
		SimpleRules rules = new SimpleRules();
		// By giving null as the argument board, this test will fail if the
		// method validMove() tries to calculate if this move is valid
		assertEquals(false, rules.validMove(null, boardNode, player1Id));
		assertEquals(false, rules.validMove(null, boardNode, player2Id));
	}

	/**
	 * Test that given a board with an occupied node getNodesToSwap will return
	 * an empty list if any player plays on this node
	 */
	@Test
	public void testMoveOnOccupiedNodeShouldSwapNoNodes() {
		String player1Id = "1";
		Node boardNode = getMockedNode(player1Id);
		SimpleRules rules = new SimpleRules();
		// By giving null as the argument board, this test will fail if the
		// method getNodesToSwap() tries to calculate the nodes to swap
		assertEquals(true, rules.getNodesToSwap(null, boardNode, null).isEmpty());
		assertEquals(true, rules.getNodesToSwap(null, boardNode, player1Id).isEmpty());
	}

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 * x o *
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  then player 1 should have a valid move
	 * </pre>
	 */

	@Test
	public void testHasValidMoveShouldBeTrueIfPlayerHaveValidMove() {
		String player1Id = "1";
		String player2Id = "2";
		ImmutableBoard mockBoard = getMinimalMockedBoard(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();
		boolean res = rules.hasValidMove(mockBoard, player1Id);
		assertEquals(true, res);
	}

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 * x o *
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  has player 2 no valid move
	 * </pre>
	 */

	@Test
	public void testHasValidMoveShouldBeFalseIfPlayerHaveNoValidMove() {
		String player1Id = "1";
		String player2Id = "2";
		ImmutableBoard mockBoard = getMinimalMockedBoard(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();
		boolean res = rules.hasValidMove(mockBoard, player2Id);
		assertEquals(false, res);
	}

	/**
	 * Test that given a board where a player can make a move should isGameOver
	 * return false
	 */
	@Test
	public void testIsGameOverShouldReturnFalseIfAPlayerHasAValidMove() {
		String player1Id = "1";
		String player2Id = "2";
		ImmutableBoard mockBoard = getMinimalMockedBoard(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();
		boolean res = rules.isGameOver(mockBoard, player1Id, player2Id);
		assertEquals(false, res);

	}

	/**
	 * Test that given a board where no player can make a move should isGameOver
	 * return true
	 */
	@Test
	public void testIsGameOverShouldReturnTrueIfNoPlayerHasAValidMove() {
		String player1Id = "1";
		String player2Id = "2";
		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		ArrayList<Node> nodesOnBoard = new ArrayList<Node>();
		Node boardNode0 = getMockedNode(player1Id);
		nodesOnBoard.add(boardNode0);
		Node boardNode1 = getMockedNode(player1Id);
		nodesOnBoard.add(boardNode1);
		Node boardNode2 = getMockedNode(null);
		nodesOnBoard.add(boardNode2);

		for (Direction dir : Direction.values()) {
			switch (dir) {
			case EAST:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(boardNode1);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(boardNode2);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(null);
				break;
			case WEST:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(boardNode0);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(boardNode1);
				break;
			default:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(null);
				break;
			}
		}
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		SimpleRules rules = new SimpleRules();
		boolean res = rules.isGameOver(mockBoard, player1Id, player2Id);
		assertEquals(true, res);
	}

	/**
	 * 
	 * <pre>
	 * Return a minimal mockedBoard as following
	 * 
	 * 0 1 2 (node number)
	 * x o *
	 * where:
	 * x mark node occupied by player 1,node number 0
	 * o mark node occupied by player 2, node number 1 
	 * * mark node not occupied, node number 2.
	 * 
	 * The mocked board are able to answer the question
	 * getNextNodeInDirection for all nodes and all directions. Calling getNodes
	 * on the mockedBoard will return a a list of nodes sorted in increasing
	 * node number order.
	 * 
	 * <pre>
	 */
	private ImmutableBoard getMinimalMockedBoard(String player1Id, String player2Id) {

		ImmutableBoard mockBoard = Mockito.mock(ImmutableBoard.class);
		ArrayList<Node> nodesOnBoard = new ArrayList<Node>();
		Node boardNode0 = getMockedNode(player1Id);
		nodesOnBoard.add(boardNode0);
		Node boardNode1 = getMockedNode(player2Id);
		nodesOnBoard.add(boardNode1);
		Node boardNode2 = getMockedNode(null);
		nodesOnBoard.add(boardNode2);

		for (Direction dir : Direction.values()) {
			switch (dir) {
			case EAST:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(boardNode1);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(boardNode2);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(null);
				break;
			case WEST:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(boardNode0);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(boardNode1);
				break;
			default:
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode0, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, dir)).thenReturn(null);
				Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, dir)).thenReturn(null);
				break;
			}
		}
		Mockito.when(mockBoard.getNodes()).thenReturn(nodesOnBoard);
		return mockBoard;
	}

	/*
	 * Return a mocked node, if playerId is not null isMarked() will return true
	 * and getOccupantPlayerId() return the given playerId. Otherwise false and
	 * null, respectively, will be returned for these method calls.
	 */
	private Node getMockedNode(String playerId) {
		Node mockNode = Mockito.mock(Node.class);

		boolean isOccupied = true;
		if (playerId == null) {
			isOccupied = false;
		}
		Mockito.when(mockNode.isMarked()).thenReturn(isOccupied);
		Mockito.when(mockNode.getOccupantPlayerId()).thenReturn(playerId);
		return mockNode;
	}

}
