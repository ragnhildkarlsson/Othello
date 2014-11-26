package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import java.util.Set;

import kth.game.othello.board.Node;
import kth.game.othello.score.ScoreItem;

import kth.game.othello.simple.adapter.NodeAdapter;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleScoreTest {

	/**
	 * Tests that given the following starting nodes
	 * 
	 * <pre>
	 * Node 1 occupied by player 1
	 * Node 2 occupied by player 1
	 * Node 3 occupied by player 2
	 * </pre>
	 * 
	 * And no nodes are updated will getPlayersScore return a list with these
	 * two score items
	 * 
	 * <pre>
	 * ScoreItem 1: player 1 score 2
	 * ScoreItem 2: player 2 score 1
	 * </pre>
	 * 
	 * listed in decreasing order after score.
	 */

	@Test
	public void getPlayersScore() {
		String idPlayer1 = "player1";
		String idPlayer2 = "player2";
		Node mockNode1 = Mockito.mock(Node.class);
		Node mockNode2 = Mockito.mock(Node.class);
		Node mockNode3 = Mockito.mock(Node.class);
		Mockito.when(mockNode1.getOccupantPlayerId()).thenReturn(idPlayer1);
		Mockito.when(mockNode2.getOccupantPlayerId()).thenReturn(idPlayer1);
		Mockito.when(mockNode3.getOccupantPlayerId()).thenReturn(idPlayer2);

		Mockito.when(mockNode1.getId()).thenReturn("node1");
		Mockito.when(mockNode2.getId()).thenReturn("node2");
		Mockito.when(mockNode3.getId()).thenReturn("node3");
		Set<Node> startingNodes = new HashSet<Node>();
		startingNodes.add(mockNode1);
		startingNodes.add(mockNode2);
		startingNodes.add(mockNode3);
		SimpleScore score = new SimpleScore(startingNodes);
		List<ScoreItem> res = score.getPlayersScore();
		assertEquals(2, res.size());
		assertEquals(idPlayer1, res.get(0).getPlayerId());
		assertEquals(2, res.get(0).getScore());
		assertEquals(idPlayer2, res.get(1).getPlayerId());
		assertEquals(1, res.get(1).getScore());
	}

	/**
	 * Tests that given the following starting nodes
	 * 
	 * <pre>
	 * Node 1 occupied by player 1
	 * Node 2 occupied by player 1
	 * Node 3 occupied by player 2
	 * </pre>
	 * 
	 * And no nodes are updated will getPoints(id of player 1) return 2 and
	 * getPoints( id of player 2) return 1
	 */
	@Test
	public void testGetPoint() {
		String idPlayer1 = "player1";
		String idPlayer2 = "player2";
		Node mockNode1 = Mockito.mock(Node.class);
		Node mockNode2 = Mockito.mock(Node.class);
		Node mockNode3 = Mockito.mock(Node.class);
		Mockito.when(mockNode1.getOccupantPlayerId()).thenReturn(idPlayer1);
		Mockito.when(mockNode2.getOccupantPlayerId()).thenReturn(idPlayer1);
		Mockito.when(mockNode3.getOccupantPlayerId()).thenReturn(idPlayer2);
		Mockito.when(mockNode1.getId()).thenReturn("node1");
		Mockito.when(mockNode2.getId()).thenReturn("node2");
		Mockito.when(mockNode3.getId()).thenReturn("node3");
		Set<Node> startingNodes = new HashSet<Node>();
		startingNodes.add(mockNode1);
		startingNodes.add(mockNode2);
		startingNodes.add(mockNode3);
		SimpleScore score = new SimpleScore(startingNodes);
		assertEquals(2, score.getPoints(idPlayer1));
		assertEquals(1, score.getPoints(idPlayer2));
	}

	/**
	 * Test that given one starting node: Node1 - not occupied and we add us
	 * self as observer of the score will we be notified when the score update
	 * method is called as following update(Node1 - occupied by "player")
	 */
	@Test
	public void testUpdateWorkFromNullToOccupied() {

		NodeAdapter startNode = Mockito.mock(NodeAdapter.class);
		Mockito.when(startNode.getOccupantPlayerId()).thenReturn(null);
		Mockito.when(startNode.getId()).thenReturn("node1");

		String playerId = "player";
		NodeAdapter updatedNode = Mockito.mock(NodeAdapter.class);
		Mockito.when(updatedNode.getOccupantPlayerId()).thenReturn(playerId);
		Mockito.when(updatedNode.getId()).thenReturn("node1");

		Set<Node> startingNodes = new HashSet<Node>();
		startingNodes.add(startNode);
		SimpleScore score = new SimpleScore(startingNodes);

		Observer mockObserver = Mockito.mock(Observer.class);
		score.addObserver(mockObserver);
		score.update(updatedNode, playerId);
		ArrayList<String> updatedPlayers = new ArrayList<String>();
		updatedPlayers.add(playerId);
		Mockito.verify(mockObserver).update(score, updatedPlayers);
	}

	/**
	 * Test that given one starting node: Node1 - occupied by player1 and we add
	 * us self as observer of the score will we be notified when the score
	 * update method is called as following update (Node1 - occupied by
	 * "player2")
	 */
	@Test
	public void testUpdateWorkFromOccupiedToOccupiedByOtherPlayer() {
		String player1Id = "player1";
		String player2Id = "player2";

		NodeAdapter startNode = Mockito.mock(NodeAdapter.class);
		Mockito.when(startNode.getOccupantPlayerId()).thenReturn(player1Id);
		Mockito.when(startNode.getId()).thenReturn("node1");

		NodeAdapter updatedNode = Mockito.mock(NodeAdapter.class);
		Mockito.when(updatedNode.getOccupantPlayerId()).thenReturn(player2Id);
		Mockito.when(updatedNode.getId()).thenReturn("node1");

		Set<Node> startingNodes = new HashSet<Node>();
		startingNodes.add(startNode);
		SimpleScore score = new SimpleScore(startingNodes);

		Observer mockObserver = Mockito.mock(Observer.class);
		score.addObserver(mockObserver);
		score.update(updatedNode, player2Id);
		ArrayList<String> updatedPlayers = new ArrayList<String>();
		updatedPlayers.add(player1Id);
		updatedPlayers.add(player2Id);
		Mockito.verify(mockObserver).update(score, updatedPlayers);
	}

}