package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

public class Tournament {

	private List<Player> players;
	private OthelloFactory othelloFactory;
	private List<Match> matchups;
	private Square squareBoardFactory;
	private GameRunner gameRunner;

	public Tournament(List<Player> players, OthelloFactory othelloFactory, Square squareBoardFactory,
			GameRunner gameRunner) {
		this.players = players;
		this.othelloFactory = othelloFactory;
		this.squareBoardFactory = squareBoardFactory;
		this.gameRunner = gameRunner;
	}

	public void startViewableTournament() {

		// TODO

	}

	public List<Match> startTournament() {
		// prepare all matches to be played
		matchups = generateMatchups();
		// play each match
		for (Match match : matchups) {
			Set<NodeData> nodesData = squareBoardFactory.getNodes(8, match.getPlayers());
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
