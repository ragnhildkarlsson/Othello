package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.simple.model.GameModelFactory;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableBoardFactory;
import kth.game.othello.simple.model.ImmutableNode;
import kth.game.othello.simple.model.Rules;

/**
 * A factory for producing simple Othello games.
 */
public class SimpleOthelloFactory implements OthelloFactory {

	private final String nameOfPlayer1 = "WhitePlayer";
	private final String nameOfPlayer2 = "BlackPlayer";
	private ImmutableBoardFactory immutableBoardFactory;
	private PlayerFactory playerFactory;

	public SimpleOthelloFactory() {
		this.immutableBoardFactory = new ImmutableBoardFactory();
		this.playerFactory = new PlayerFactory();
	}

	/**
	 * Creates an Othello game with two computers.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createComputerGame() {

		// TODO implement
		// See if it is possible to extract general tasks done in createGame
		// method below to private methods and reuse code

		// BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		// SimpleRules rules = new SimpleRules();
		// ComputerPlayer computer = new LousyComputerPlayer(player1Id,
		// nameOfPlayer1);
		// ComputerPlayer computer2 = new LousyComputerPlayer(player2Id,
		// nameOfPlayer2);
		//
		// SimpleOthello computerGame = new SimpleOthello(boardFactory, rules,
		// computer, computer2);
		return null;
	}

	/**
	 * Creates an Othello game with two humans.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanGame() {
		// TODO implement

		// Old Implementation
		// BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		// SimpleRules rules = new SimpleRules();
		// HumanPlayer human = new HumanPlayer(player1Id, nameOfPlayer1);
		// HumanPlayer human2 = new HumanPlayer(player2Id, nameOfPlayer2);
		//
		// SimpleOthello humanGame = new SimpleOthello(boardFactory, rules,
		// human, human2);

		return null;
	}

	/**
	 * Creates an Othello game with one computer playing against one human.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanVersusComputerGame() {

		// See if it is possible to extract general tasks done in createGame
		// method below to private methods and reuse code

		// TODO implement
		// BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		// SimpleRules rules = new SimpleRules();
		//
		// HumanPlayer human = new HumanPlayer(player1Id, nameOfPlayer1);
		// ComputerPlayer computer = new LousyComputerPlayer(player2Id,
		// nameOfPlayer2);
		//
		// SimpleOthello humanVersusComputerGame = new
		// SimpleOthello(boardFactory, rules, human, computer);

		return null;
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
		ImmutableBoard immutableBoard = immutableBoardFactory.boardFromNodes(immutableNodes);

		ArrayList<String> playerIds = new ArrayList<String>();
		for (Player player : players) {
			playerIds.add(player.getId());
		}
		Rules rules = new Rules();

		GameModelFactory gameModelFactory = new GameModelFactory(immutableBoard, playerIds, rules);

		// Create wrappers

		List<NodeWrapper> nodeWrappers = new ArrayList<NodeWrapper>();
		for (ImmutableNode immutableNode : immutableNodes) {
			nodeWrappers.add(getNodeWrapperFromImmuatableNode(immutableNode));
		}
		SimpleScore score = new SimpleScore();
		// Add score as observer for all nodes;
		for (NodeWrapper nodeWrapper : nodeWrappers) {
			nodeWrapper.addObserver(score);
		}

		BoardWrapper boardWrapper = new BoardWrapper(nodeWrappers);
		PlayerHandler playerHandler = new PlayerHandler(players);
		SimpleOthello othello = new SimpleOthello(immutableBoard, boardWrapper, playerHandler, gameModelFactory, score);
		return othello;
	}

	private ImmutableNode getImmutableNodeFromNodeData(NodeData nodeData) {
		// TODO implement
		return null;
	}

	private NodeWrapper getNodeWrapperFromImmuatableNode(ImmutableNode immutableNode) {
		// TODO implement
		return null;
	}
}
