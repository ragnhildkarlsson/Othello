package kth.game.othello.tournament;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;
import kth.game.othello.player.SimplePlayer;
import kth.game.othello.player.movestrategy.GreedyStrategy;
import kth.game.othello.player.movestrategy.MasochistStrategy;
import kth.game.othello.player.movestrategy.RandomStrategy;
import kth.game.othello.player.movestrategy.SimpleStrategy;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;

public class TournamentIT {

	@Test
	public void testConsoleOnlyTournament() {
		TournamentFactory tournamentFactory = new TournamentFactory();

		Random rand = new Random();
		List<Player> computerPlayers = new ArrayList<Player>();
		Player simple = new SimplePlayer("Simple", "SimpleID", new SimpleStrategy());
		Player greedy = new SimplePlayer("Greedy", "GreedyID", new GreedyStrategy());
		Player masochist = new SimplePlayer("Masochist", "MasochistID", new MasochistStrategy());
		Player random = new SimplePlayer("Random", "RandomID", new RandomStrategy(rand));

		computerPlayers.addAll(Arrays.asList(simple, greedy, masochist, random));

		Tournament tournament = tournamentFactory.generateTournament(computerPlayers);

		List<Match> matchesPlayed = tournament.startTournament();

		assertEquals(12, matchesPlayed.size());
		// All matches should have results
		for (Match match : matchesPlayed) {
			assertEquals(true, match.getResults().isPresent());
		}
		// Print the results
		printResults(matchesPlayed, computerPlayers);

	}

	private void printResults(List<Match> matchesPlayed, List<Player> players) {
		// Print each match for itself
		int matchIndex = 1;
		for (Match match : matchesPlayed) {
			List<ScoreItem> matchResults = match.getResults().get();
			ScoreItem player1Score = matchResults.get(0);
			ScoreItem player2Score = matchResults.get(1);
			System.out.println("Match " + matchIndex);
			System.out.println(player1Score.getPlayerId() + " versus " + player2Score.getPlayerId());
			System.out.println("Score " + player1Score.getScore() + " and " + player2Score.getScore());
			System.out.println("");
			matchIndex++;
		}
		// Get all score items in one list
		List<ScoreItem> allScoreItems = new ArrayList<ScoreItem>();
		for (Match match : matchesPlayed) {
			allScoreItems.addAll(match.getResults().get());
		}

		// Print the total score for each player
		for (Player player : players) {
			int sum = 0;
			for (ScoreItem scoreItem : allScoreItems) {
				if (scoreItem.getPlayerId() == player.getId()) {
					sum += scoreItem.getScore();
				}

			}
			System.out.println("Player " + player.getName() + " total score was " + sum);

		}
	}
}
