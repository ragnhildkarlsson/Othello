package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;

/**
 * A Tournament between computer players, each pair of player plays two games.
 * 
 */
public class Tournament {

	private List<Player> players;
	private final OthelloFactory othelloFactory;
	private List<Match> matchups;
	private final Set<NodeData> nodesData;
	private final RunMatchStrategy runMatchStrategy;
	private final MatchFactory matchFactory;

	public Tournament(List<Player> players, OthelloFactory othelloFactory, Set<NodeData> nodesData,
			RunMatchStrategy runMatchStrategy, MatchFactory matchFactory) {
		this.players = players;
		this.othelloFactory = othelloFactory;
		this.nodesData = nodesData;
		this.runMatchStrategy = runMatchStrategy;
		this.matchFactory = matchFactory;
	}

	/**
	 * Run this Tournament, returning matches that has been played.
	 * 
	 * @return the matches after they have been played.
	 */
	public List<Match> startTournament() {
		// prepare all matches to be played
		matchups = generateMatchups();
		// play each match with the given runMatchStrategy
		for (Match match : matchups) {
			match.runMatch(runMatchStrategy);
		}
		return matchups;
	}

	private List<Match> generateMatchups() {
		List<Match> matchesToPlay = new ArrayList<>();
		for (Player playerOne : players) {
			for (Player playerTwo : players) {
				if (playerOne != playerTwo) { // players cannot play against themselves
					Othello othello = this.othelloFactory.createGame(nodesData, Arrays.asList(playerOne, playerTwo));
					matchesToPlay.add(matchFactory.generateMatch(Arrays.asList(playerOne, playerTwo), othello));
				}
			}
		}
		return matchesToPlay;
	}

}
