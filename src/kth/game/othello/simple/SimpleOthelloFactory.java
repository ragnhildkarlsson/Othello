package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.simple.adapter.BoardAdapter;
import kth.game.othello.simple.adapter.NodeAdapter;
import kth.game.othello.model.*;
import kth.game.othello.simple.player.SimplePlayer;
import kth.game.othello.simple.player.movestrategy.SimpleStrategy;

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
		List<Player> players = new ArrayList<>();
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
		List<Player> players = new ArrayList<>();
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
		List<Player> players = new ArrayList<>();
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
		Set<ImmutableNode> immutableNodes = nodesData.stream().map(this::getImmutableNodeFromNodeData)
				.collect(Collectors.toSet());
		ImmutableBoard immutableBoard = new ImmutableBoard(immutableNodes);

		List<String> playerIds = players.stream().map(Player::getId).collect(Collectors.toList());
        Rules rules = new Rules();

		GameModelFactory gameModelFactory = new GameModelFactory(immutableBoard, playerIds, rules);

		// Create adapters
		List<NodeAdapter> nodeAdapters = immutableNodes.stream().map(NodeAdapter::new).collect(Collectors.toList());

        Set<Node> nodeAdapterSet = new HashSet<>(nodeAdapters);
		SimpleScore score = new SimpleScore(nodeAdapterSet);

		BoardAdapter boardAdapter = new BoardAdapter(immutableBoard, nodeAdapters);
        return new SimpleOthello(players, boardAdapter, gameModelFactory, score);
	}

	private ImmutableNode getImmutableNodeFromNodeData(NodeData nodeData) {
		return new ImmutableNode(new Coordinates(nodeData.getXCoordinate(), nodeData.getYCoordinate()),
				nodeData.getOccupantPlayerId());
	}
}
