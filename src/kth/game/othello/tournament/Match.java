package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

class Match {

	List<Player> players;
	Optional<List<ScoreItem>> matchResults;

	public Match(List<Player> players) {
		this.matchResults = Optional.empty();
		this.players = new ArrayList<Player>();
		this.players.addAll(players);
	}

	public void setScore(List<ScoreItem> playerScores) {
		List<ScoreItem> results = new ArrayList<ScoreItem>();
		results.addAll(playerScores);
		this.matchResults = Optional.of(results);
	}

	public List<Player> getPlayers() {
		List<Player> playersCopy = new ArrayList<Player>();
		playersCopy.addAll(players);
		return playersCopy;
	}

	public boolean hasResults() {
		if (matchResults.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public List<ScoreItem> getResults() {
		if (matchResults.isPresent()) {
			List<ScoreItem> results = matchResults.get();
			List<ScoreItem> resultCopy = new ArrayList<ScoreItem>();
			resultCopy.addAll(results);
			return resultCopy;
		}
		return null;
	}

}