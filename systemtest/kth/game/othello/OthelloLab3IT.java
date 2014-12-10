package kth.game.othello;

import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

import org.junit.Test;

public class OthelloLab3IT {

	@Test
	public void testViewWithTwoComputers() throws Exception {
		OthelloFactory othelloFactory = new SimpleOthelloFactory();
		Othello othello = othelloFactory.createComputerGame();

		OthelloView othelloView = OthelloViewFactory.create(othello);
		othelloView.start();
	}
}
