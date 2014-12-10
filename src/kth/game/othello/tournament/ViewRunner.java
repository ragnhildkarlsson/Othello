package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class ViewRunner implements RunMatchStrategy {

	@Override
	public List<ScoreItem> runMatch(Othello othello) {
		OthelloView othelloView = OthelloViewFactory.create(othello);
		othelloView.start();
		return othello.getScore().getPlayersScore();
	}

}
