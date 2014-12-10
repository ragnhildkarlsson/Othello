package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

/**
 * A Match is responsible for keeping track of an Othello match, the match can be run and then scores will be available.
 */
class Match {

	private Optional<List<ScoreItem>> matchResults;
	private Othello othello;

	/**
	 * Create a new match given its players and the Othello game to play.
	 * 
	 * @param players
	 *            the players of the game.
	 * @param othello
	 *            the game to play.
	 */
	public Match(List<Player> players, Othello othello) {
		this.matchResults = Optional.empty();
		this.othello = othello;
	}

	private void setScore(List<ScoreItem> playerScores) {
		List<ScoreItem> results = new ArrayList<ScoreItem>();
		results.addAll(playerScores);
		this.matchResults = Optional.of(results);
	}

	/**
	 * @return the players of this match.
	 */
	public List<Player> getPlayers() {
		List<Player> playersCopy = new ArrayList<Player>();
		playersCopy.addAll(othello.getPlayers());
		return playersCopy;
	}

	/**
	 * @return an Optional either containing the results of this match if the match has been run, or an empty Optional
	 *         if the game has not been run.
	 */
	public Optional<List<ScoreItem>> getResults() {
		if (matchResults.isPresent()) {
			List<ScoreItem> results = matchResults.get();
			return Optional.of(new ArrayList<ScoreItem>(results));
		}
		return Optional.empty();
	}

	/**
	 * Run the match given the runMatchStrategy to use.
	 * 
	 * @param runMatchStrategy
	 *            the strategy to run this match.
	 */
	public void runMatch(RunMatchStrategy runMatchStrategy) {
		List<ScoreItem> result = runMatchStrategy.runMatch(othello);
		this.setScore(result);

	}

}