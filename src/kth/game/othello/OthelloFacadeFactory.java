package kth.game.othello;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Coordinates;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeAdapter;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.model.GameModel;
import kth.game.othello.model.GameModelFactory;
import kth.game.othello.model.ImmutableBoard;
import kth.game.othello.model.ImmutableNode;
import kth.game.othello.model.ModelRules;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.SimplePlayer;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.movestrategy.SimpleStrategy;
import kth.game.othello.rules.RulesAdapter;
import kth.game.othello.score.SimpleScore;

/**
 * A factory for producing simple Othello games.
 */
public class OthelloFacadeFactory implements OthelloFactory {

	public OthelloFacadeFactory() {
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
		ModelRules rules = new ModelRules();
		GameModelFactory gameModelFactory = new GameModelFactory(immutableBoard, playerIds, rules);

		// Create adapters
		List<NodeAdapter> nodeAdapters = immutableNodes.stream().map(NodeAdapter::new).collect(Collectors.toList());
		Set<Node> nodeAdapterSet = new HashSet<>(nodeAdapters);
		SimpleScore score = new SimpleScore(nodeAdapterSet);
		BoardAdapter boardAdapter = new BoardAdapter(immutableBoard, nodeAdapters);
		RulesAdapter rulesAdapter = new RulesAdapter(rules, boardAdapter);

		// Create empty game model and init the boardAdapter
		GameModel initGameModel = gameModelFactory.newEmptyGameModel();

		// Create GameController
		PlayerHandler playerHandler = new PlayerHandler(players);
		MoveNotifier moveNotifier = new MoveNotifier();
		GameFinishedNotifier gameFinishedNotifier = new GameFinishedNotifier();
		GameController gameController = new GameController(initGameModel, boardAdapter, playerHandler, rulesAdapter,
				gameFinishedNotifier, moveNotifier, gameModelFactory);

		// Create OthelloFacade
		String othelloId = Instant.now().toString() + Long.toString((new Random()).nextLong());
		OthelloFacade othelloFacade = new OthelloFacade(othelloId, playerHandler, boardAdapter, score, rulesAdapter,
            gameController);
		moveNotifier.initiateUnderlyingOthello(othelloFacade);
		gameFinishedNotifier.initiateUnderlyingOthello(othelloFacade);

		return othelloFacade;

	}

	private ImmutableNode getImmutableNodeFromNodeData(NodeData nodeData) {
		Optional<String> occupantPlayerId = Optional.ofNullable(nodeData.getOccupantPlayerId());
		return new ImmutableNode(new Coordinates(nodeData.getXCoordinate(), nodeData.getYCoordinate()),
				occupantPlayerId);
	}
}
