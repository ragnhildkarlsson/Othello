package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;

/**
 * Generates new Matches.
 */
public class MatchFactory {

	public Match generateMatch(List<Player> players, Othello othello) {
		return new Match(players, othello);
	}

}
