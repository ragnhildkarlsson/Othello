package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;

/**
 * A Tournament between computer players, each pair of player plays two games.
 * 
 */
public class Tournament {

	private List<Player> players;
	private List<Match> matches;
	private final RunMatchStrategy runMatchStrategy;

	/**
	 * Create a new tournament, given its players, matches and runStrategy.
	 * 
	 * @param players
	 *            the players of this tournament.
	 * @param runMatchStrategy
	 *            the strategy on how to run each match.
	 * @param matchesToPlay
	 *            the matches to play in this tournament.
	 */
	public Tournament(List<Player> players, List<Match> matchesToPlay, RunMatchStrategy runMatchStrategy) {
		this.players = players;
		this.runMatchStrategy = runMatchStrategy;
		this.matches = matchesToPlay;
	}

	/**
	 * Run this Tournament, returning matches that has been played.
	 * 
	 * @return the matches after they have been played.
	 */
	public List<Match> startTournament() {
		// play each match with the given runMatchStrategy
		for (Match match : matches) {
			match.runMatch(runMatchStrategy);
		}
		return matches;
	}

	public List<Player> getPlayers() {
		return new ArrayList<Player>(this.players);
	}
}
