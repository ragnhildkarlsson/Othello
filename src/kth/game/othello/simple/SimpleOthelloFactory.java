package kth.game.othello.simple;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.simple.api.NodeWrapper;
import kth.game.othello.simple.board.BoardFactory;
import kth.game.othello.simple.board.ImmutableNode;
import kth.game.othello.simple.player.ComputerPlayer;
import kth.game.othello.simple.player.HumanPlayer;
import kth.game.othello.simple.player.LousyComputerPlayer;

import java.util.List;
import java.util.Set;

/**
 * A factory for producing simple Othello games.
 * 
 * @author Mikael Eriksson
 */
public class SimpleOthelloFactory implements OthelloFactory {

	private final String player1Id = "White";
	private final String player2Id = "Black";
	private final String nameOfPlayer1 = "WhitePlayer";
	private final String nameOfPlayer2 = "BlackPlayer";

	/**
	 * Creates an Othello game with two computers.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createComputerGame() {

		BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();
		ComputerPlayer computer = new LousyComputerPlayer(player1Id, nameOfPlayer1);
		ComputerPlayer computer2 = new LousyComputerPlayer(player2Id, nameOfPlayer2);

		SimpleOthello computerGame = new SimpleOthello(boardFactory, rules, computer, computer2);
		return computerGame;
	}

	/**
	 * Creates an Othello game with two humans.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanGame() {

		BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();
		HumanPlayer human = new HumanPlayer(player1Id, nameOfPlayer1);
		HumanPlayer human2 = new HumanPlayer(player2Id, nameOfPlayer2);

		SimpleOthello humanGame = new SimpleOthello(boardFactory, rules, human, human2);

		return humanGame;
	}

	/**
	 * Creates an Othello game with one computer playing against one human.
	 * 
	 * @return An Othello game
	 */
	@Override
	public Othello createHumanVersusComputerGame() {
		BoardFactory boardFactory = new BoardFactory(player1Id, player2Id);
		SimpleRules rules = new SimpleRules();

		HumanPlayer human = new HumanPlayer(player1Id, nameOfPlayer1);
		ComputerPlayer computer = new LousyComputerPlayer(player2Id, nameOfPlayer2);

		SimpleOthello humanVersusComputerGame = new SimpleOthello(boardFactory, rules, human, computer);

		return humanVersusComputerGame;
	}

    /**
     * Creates an Othello game with the given players on a board that contains the given nodes
     *
     * @param nodesData the nodes of the board
     * @param players   the players
     * @return An Othello game
     */
    @Override
    public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
        nodes = nodesData.stream().map(node -> new ImmutableNode());
        BoardFactory boardFactory = new BoardFactory();
        ImmutableBoard = boardFactory.boardFromNodes(nodes);
        List<NodeWrapper>(nodes) nodeWrappers
        BoardWrapper(nodeWrappers)
        MoveMaker(BoardFactory, ImmutableBoard, ImmutableNodes)
        SimpleOthello(MoveMaker, …)
        API Controller(SimpleOthello, BoardWrapper, NodeWrappers)

        return null;
    }
}
