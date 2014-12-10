package kth.game.othello.tournament;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;
import org.mockito.Mockito;

public class MatchTest {

	@Test
	public void testNoResultAtStart() {
		Match match = new Match(null, null);

		assertEquals(false, match.getResults().isPresent());
	}

	@Test
	public void testRunMatch() {
		List<Player> players = new ArrayList<>();
		Othello othello = Mockito.mock(Othello.class);
		RunMatchStrategy runMatchStrategy = Mockito.mock(RunMatchStrategy.class);
		List<ScoreItem> matchFinalScore = new ArrayList<>();

		Mockito.when(runMatchStrategy.runMatch(othello)).thenReturn(matchFinalScore);

		Match match = new Match(players, othello);
		match.runMatch(runMatchStrategy);

		// should now exist match results
		assertEquals(true, match.getResults().isPresent());
		// check that its the correct results
		assertEquals(matchFinalScore, match.getResults().get());
	}

}
