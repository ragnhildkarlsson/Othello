package kth.game.othello.simple;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;

/**
 * A factory for producing othello games.
 * 
 * @author mikael
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
}
