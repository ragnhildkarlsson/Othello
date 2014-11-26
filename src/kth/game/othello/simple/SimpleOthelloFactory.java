package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.simple.model.Coordinates;
import kth.game.othello.simple.model.GameModelFactory;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableNode;
import kth.game.othello.simple.model.Rules;

/**
 * A factory for producing simple Othello games.
 */
public class SimpleOthelloFactory implements OthelloFactory {

	public SimpleOthelloFactory() {
	}

	/**
	 * Creates an Othello game with two computers.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createComputerGame() {

		MoveStrategy moveStrategy = new SimpleStrategy();
		SimplePlayer computer1 = new SimplePlayer("computer1", "computer1ID", moveStrategy);
		SimplePlayer computer2 = new SimplePlayer("computer2", "computer2ID", moveStrategy);

		Square square = new Square();
		List<Player> players = new ArrayList<Player>();
		players.add(computer1);
		players.add(computer2);
		Set<NodeData> nodesData = square.getNodes(8, players);

		return createGame(nodesData, players);
	}

	/**
	 * Creates an Othello game with two humans.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanGame() {
		SimplePlayer player1 = new SimplePlayer("player1", "player1ID");
		SimplePlayer player2 = new SimplePlayer("player2", "player2ID");

		Square square = new Square();
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		Set<NodeData> nodesData = square.getNodes(8, players);

		return createGame(nodesData, players);
	}

	/**
	 * Creates an Othello game with one computer playing against one human.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanVersusComputerGame() {

		MoveStrategy moveStrategy = new SimpleStrategy();
		SimplePlayer player1 = new SimplePlayer("player1", "player1ID");
		SimplePlayer computer2 = new SimplePlayer("computer2", "computer2ID", moveStrategy);

		Square square = new Square();
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(computer2);
		Set<NodeData> nodesData = square.getNodes(8, players);

		return createGame(nodesData, players);
	}

	/**
	 * Creates an Othello game with the given players on a board that contains
	 * the given nodes
	 * 
	 * @param nodesData
	 *            the nodes of the board
	 * @param players
	 *            the players
	 * @return An Othello game
	 */
	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {

		// Create the gameModelFactory
		Set<ImmutableNode> immutableNodes = new HashSet<ImmutableNode>();
		for (NodeData node : nodesData) {
			immutableNodes.add(getImmutableNodeFromNodeData(node));
		}
		ImmutableBoard immutableBoard = new ImmutableBoard(immutableNodes);

		ArrayList<String> playerIds = new ArrayList<String>();
		for (Player player : players) {
			playerIds.add(player.getId());
		}
		Rules rules = new Rules();

		GameModelFactory gameModelFactory = new GameModelFactory(immutableBoard, playerIds, rules);

		// Create adapters
		List<NodeAdapter> nodeAPIViews = new ArrayList<NodeAdapter>();
		for (ImmutableNode immutableNode : immutableNodes) {
			nodeAPIViews.add(new NodeAdapter(immutableNode));
		}

		Set<Node> nodeAPIViewsSet = new HashSet<Node>(nodeAPIViews);
		SimpleScore score = new SimpleScore(nodeAPIViewsSet);
		// Add score as observer for all nodes;
		for (NodeAdapter nodeAPIView : nodeAPIViews) {
			nodeAPIView.addObserver(score);
		}

		BoardAdapter boardAdapter = new BoardAdapter(immutableBoard, nodeAPIViews);
		SimpleOthello othello = new SimpleOthello(players, boardAdapter, gameModelFactory, score);
		return othello;
	}

	private ImmutableNode getImmutableNodeFromNodeData(NodeData nodeData) {
		return new ImmutableNode(new Coordinates(nodeData.getXCoordinate(), nodeData.getYCoordinate()),
				nodeData.getOccupantPlayerId());
	}
}
