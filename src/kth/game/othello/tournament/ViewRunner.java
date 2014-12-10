package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * A ViewRunner is responsible for running games between computer players, and then returning the result of the match,
 * each match is displayed as a view.
 *
 */
public class ViewRunner implements RunMatchStrategy {

	/**
	 * Run the given Othello game where all players are assumed to be computers, until there are no more valid moves,
	 * then the result of game is returned. While the game is run it will be displayed in a view.
	 * 
	 * @param othello
	 *            the game to run
	 * @return the score of the game after it is over
	 */
	@Override
	public List<ScoreItem> runMatch(Othello othello) {
		OthelloView othelloView = OthelloViewFactory.create(othello, 0, 0);
		othelloView.start();
		return othello.getScore().getPlayersScore();
	}

}
