package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;

/**
 * A Strategy (according to the strategy pattern) to run an Othello game, then return the results.
 */
public interface RunMatchStrategy {

	public List<ScoreItem> runMatch(Othello othello);

}
