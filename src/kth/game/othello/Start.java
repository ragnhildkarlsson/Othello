package kth.game.othello;

import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * Use this class to start a new game against the computer
 */
public class Start {

    public static void main(String[] args) {
        OthelloFactory othelloFactory = new SimpleOthelloFactory();
        Othello othello = othelloFactory.createHumanVersusComputerGame();

        OthelloView othelloView = OthelloViewFactory.create(othello, 0, 0);
        othelloView.start();
    }
}
