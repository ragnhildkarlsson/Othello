package kth.game.othello.simple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.simple.model.ImmutableBoard;
import kth.game.othello.simple.model.ImmutableBoardFactory;
import kth.game.othello.simple.model.ImmutableNode;

/**
 * A factory for producing simple Othello games.
 */
public class SimpleOthelloFactory implements OthelloFactory {

	private static final int DEFAULT_BOARD_SIZE = 8;
	private static final String DEFAULT_WHITE_PLAYER_ID = "white";
	private static final String DEFAULT_BLACK_PLAYER_ID = "black";
	private final String nameOfPlayer1 = "WhitePlayer";
	private final String nameOfPlayer2 = "BlackPlayer";

	/**
	 * Creates an Othello game with two computers.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createComputerGame() {
		// TODO implement

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
		Set<ImmutableNode> immutableNodes = new HashSet<ImmutableNode>();
		for (NodeData node : nodesData) {
			immutableNodes.add(getImmutableNodeFromNodeData(node));
		}
		Set<NodeWrapper> nodeWrappers;
		for (ImmutableNode immutableNode : immutableNodes) {
			nodeWrappers.add(getNodeWrapperFromImmuatableNode(immutableNode));
		}
		ImmutableBoardFactory boardFactory = new ImmutableBoardFactory();
		ImmutableBoard immutableBoard = boardFactory.boardFromNodes();
		// List<NodeWrapper>(nodes) nodeWrappers
		// BoardWrapper(nodeWrappers)
		// MoveMaker(BoardFactory, ImmutableBoard, ImmutableNodes)
		// SimpleOthello(MoveMaker, …)
		// API Controller(SimpleOthello, BoardWrapper, NodeWrappers)
		//
		return null;
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
