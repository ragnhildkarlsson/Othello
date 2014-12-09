package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

class Match {

	List<Player> players;
	List<ScoreItem> matchResults;

	public Match(List<Player> players) {

		this.players = new ArrayList<Player>();
		this.players.addAll(players);
	}

	public void setScore(List<ScoreItem> playerScores) {
		this.matchResults = new ArrayList<ScoreItem>();
		this.matchResults.addAll(playerScores);
	}

	public List<Player> getPlayers() {
		List<Player> playersCopy = new ArrayList<Player>();
		playersCopy.addAll(players);
		return playersCopy;

	}

}