package kth.game.othello.tournament;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;
import org.mockito.Mockito;

public class TournamentTest {

	/**
	 * Test that with two players then two matches are played and the correct score is saved, ScoreItem considered
	 * friendly.
	 * 
	 */
	@Test
	public void testStartTournament() {

		String player1Id = "player1";
		String player2Id = "player2";
		List<Player> players = new ArrayList<Player>();
		Player player1 = Mockito.mock(Player.class);
		Mockito.when(player1.getId()).thenReturn(player1Id);
		Mockito.when(player1.getName()).thenReturn(player1Id);

		players.add(player1);
		Player player2 = Mockito.mock(Player.class);
		players.add(player2);
		Mockito.when(player2.getId()).thenReturn(player2Id);
		Mockito.when(player2.getName()).thenReturn(player2Id);

		SilentRunner gameRunner = Mockito.mock(SilentRunner.class);
		;
		Match match1 = Mockito.mock(Match.class);
		Match match2 = Mockito.mock(Match.class);

		List<ScoreItem> match1Scores = new ArrayList<ScoreItem>();
		match1Scores.add(new ScoreItem(player1Id, 2));
		match1Scores.add(new ScoreItem(player2Id, 1));

		List<ScoreItem> match2Scores = new ArrayList<ScoreItem>();
		match2Scores.add(new ScoreItem(player1Id, 3));
		match2Scores.add(new ScoreItem(player2Id, 2));

		Mockito.when(match1.getResults()).thenReturn(Optional.of(match1Scores));
		Mockito.when(match2.getResults()).thenReturn(Optional.of(match2Scores));

		List<Match> matchesToPlay = new ArrayList<Match>(Arrays.asList(match1, match2));
		Tournament tournament = new Tournament(players, matchesToPlay, gameRunner);

		List<Match> result = tournament.startTournament();
		// should been two matches
		assertEquals(2, result.size());
		// should have results
		assertEquals(true, result.get(0).getResults().isPresent());
		assertEquals(true, result.get(1).getResults().isPresent());

		// check scores:
		List<ScoreItem> scoreItems = new ArrayList<>();
		for (Match match : result) {
			scoreItems.addAll(match.getResults().get());
		}

		int sumPlayer1 = scoreItems.stream().filter(e -> e.getPlayerId() == player1Id).map(p -> p.getScore())
				.reduce(0, (soFar, score) -> score + soFar);
		int sumPlayer2 = scoreItems.stream().filter(e -> e.getPlayerId() == player2Id).map(p -> p.getScore())
				.reduce(0, (soFar, score) -> score + soFar);

		// printPlayerTotalScore(result, players);
		assertEquals(5, sumPlayer1);
		assertEquals(3, sumPlayer2);

	}
}
