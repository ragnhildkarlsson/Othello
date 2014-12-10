package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;

/**
 * A SilentRunner is responsible for running one game between computer players, and then returning the result of the
 * match, without producing any output regarding the games running.
 *
 */
public class SilentRunner implements RunMatchStrategy {

	/**
	 * Run the given Othello game where all players are assumed to be computers, until there are no more valid moves,
	 * then the result of game is returned.
	 * 
	 * @param othello
	 *            the game to run
	 * @return the score of the game after it is over
	 */
	public List<ScoreItem> runMatch(Othello othello) {
		// run the game till no more moves can be made
		while (othello.isActive()) {
			othello.move();
		}

		return othello.getScore().getPlayersScore();
	}

}
