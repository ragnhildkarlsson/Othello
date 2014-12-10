package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.SimpleOthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

public class TournamentFactory {

	public Tournament generateTournament(List<Player> computerPlayers, RunMatchStrategy runMatchStrategy) {

		OthelloFactory othelloFactory = new SimpleOthelloFactory();
		List<Match> matches = generateMatchups(computerPlayers, othelloFactory);

		Tournament tournament = new Tournament(computerPlayers, matches, runMatchStrategy);
		return tournament;
	}

	private List<Match> generateMatchups(List<Player> players, OthelloFactory othelloFactory) {
		List<Match> matchesToPlay = new ArrayList<>();
		for (Player playerOne : players) {
			for (Player playerTwo : players) {
				if (playerOne != playerTwo) { // players cannot play against themselves
					List<Player> matchup = Arrays.asList(playerOne, playerTwo);
					Set<NodeData> nodesData = new Square().getNodes(8, matchup);
					Othello othello = othelloFactory.createGame(nodesData, matchup);
					matchesToPlay.add(new Match(matchup, othello));
				}
			}
		}
		return matchesToPlay;
	}
}
