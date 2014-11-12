package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import kth.game.othello.board.Node;
import kth.game.othello.simple.SimpleBoard.Direction;

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
	 *  could player 1 play on the non occupied node
	 * </pre>
	 */
	@Test
	public void testValidMoveOnBoardEdgeShouldBeValid() {
		String player1Id = "1";
		String player2Id = "2";
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		ArrayList<Node> nodesOnBoard = new ArrayList<>();
		// add node 1
		Node boardNode1 = getMockedNode(player1Id);
		nodesOnBoard.add(boardNode1);
		Node boardNode2 = getMockedNode(player2Id);
		nodesOnBoard.add(boardNode2);
		Node boardNode3 = getMockedNode(null);
		nodesOnBoard.add(boardNode3);
		// set up behavior for getNextNodeInDirection;
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.NORTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.EAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.SOUTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode3, Direction.WEST)).thenReturn(boardNode2);

		Mockito.when(mockBoard.getNextNodeInDirection(boardNode2, Direction.WEST)).thenReturn(boardNode1);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode1, Direction.WEST)).thenReturn(null);
		SimpleRules rules = new SimpleRules();
		assertEquals(true, rules.validMove(mockBoard, boardNode3, player1Id));

	}

	/**
	 * <pre>
	 * Test that given a board with the following nodes
	 *   0 1 2 3 4 5  
	 * 1   	 *   o	
	 * 0 x o o * o x
	 *  where:
	 *  x mark nodes occupied by player 1
	 *  o mark nodes occupied by player 2
	 *  * mark nodes not occupied by any player
	 *  will getNodesToSwap return a list with the following nodes (1,1) (2,1) (4,1)
	 *  if player 1 plays on (3,1).
	 * </pre>
	 */
	@Test
	public void testValidMoveShouldGiveCorrectNumberOfSwapedNodes() {
		String player1Id = "1";
		String player2Id = "2";
		SimpleBoard mockBoard = Mockito.mock(SimpleBoard.class);
		// add node 1
		Node boardNode21 = getMockedNode(null);
		Node boardNode41 = getMockedNode(player2Id);
		Node boardNode00 = getMockedNode(player1Id);
		Node boardNode10 = getMockedNode(player2Id);
		Node boardNode20 = getMockedNode(player2Id);
		Node boardNode30 = getMockedNode(null);
		Node boardNode40 = getMockedNode(player2Id);
		Node boardNode50 = getMockedNode(player1Id);

		// set up behavior for getNextNodeInDirection;
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.NORTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.NORTHEAST)).thenReturn(boardNode41);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.NORTHWEST)).thenReturn(boardNode21);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.EAST)).thenReturn(boardNode40);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.SOUTH)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.SOUTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.SOUTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode30, Direction.WEST)).thenReturn(boardNode20);

		Mockito.when(mockBoard.getNextNodeInDirection(boardNode41, Direction.NORTHEAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode21, Direction.NORTHWEST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode40, Direction.EAST)).thenReturn(boardNode50);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode50, Direction.EAST)).thenReturn(null);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode20, Direction.WEST)).thenReturn(boardNode10);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode10, Direction.WEST)).thenReturn(boardNode00);
		Mockito.when(mockBoard.getNextNodeInDirection(boardNode00, Direction.WEST)).thenReturn(null);

		SimpleRules rules = new SimpleRules();
		assertEquals(true, rules.getNodesToSwap(mockBoard, boardNode30, player1Id).contains(boardNode10));
		assertEquals(true, rules.getNodesToSwap(mockBoard, boardNode30, player1Id).contains(boardNode20));
		assertEquals(true, rules.getNodesToSwap(mockBoard, boardNode30, player1Id).contains(boardNode40));
		assertEquals(3, rules.getNodesToSwap(mockBoard, boardNode30, player1Id).size());

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
		assertEquals(true, rules.getNodesToSwap(null, boardNode, null).isEmpty());
		assertEquals(true, rules.getNodesToSwap(null, boardNode, player1Id).isEmpty());
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
