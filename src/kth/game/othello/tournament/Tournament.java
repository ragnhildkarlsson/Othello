package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

/**
 * A Tournament between computer players, each pair of player plays two games.
 * 
 */
public class Tournament {

	private List<Player> players;
	private OthelloFactory othelloFactory;
	private List<Match> matchups;
	private Set<NodeData> nodesData;
	private GameRunner gameRunner;

	public Tournament(List<Player> players, OthelloFactory othelloFactory, Set<NodeData> nodesData,
			GameRunner gameRunner) {
		this.players = players;
		this.othelloFactory = othelloFactory;
		this.nodesData = nodesData;
		this.gameRunner = gameRunner;
	}

	/**
	 * Run this Tournament with a view to display the games played.
	 * 
	 */
	public void startViewableTournament() {

		// TODO

	}

	/**
	 * Run this Tournament without a view, returning matches that has been played.
	 * 
	 * @return the matches after they have been played.
	 */
	public List<Match> startTournament() {
		// prepare all matches to be played
		matchups = generateMatchups();
		// play each match
		for (Match match : matchups) {
			Othello othello = this.othelloFactory.createGame(nodesData, match.getPlayers());
			List<ScoreItem> matchResult = gameRunner.runMatch(othello);
			match.setScore(matchResult); // save the result of the match
		}
		return matchups;
	}

	private List<Match> generateMatchups() {
		List<Match> matchesToPlay = new ArrayList<>();
		for (Player playerOne : players) {
			for (Player playerTwo : players) {
				if (playerOne != playerTwo) { // players cannot play against themselves
					matchesToPlay.add(new Match(Arrays.asList(playerOne, playerTwo)));
				}
			}
		}
		return matchesToPlay;
	}

}
