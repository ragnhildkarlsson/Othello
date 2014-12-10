package kth.game.othello.tournament;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;
import org.mockito.Mockito;

public class SilentRunnerTest {

	@Test
	public void testRunMatch() {
		Othello othello = Mockito.mock(Othello.class);
		Mockito.when(othello.isActive()).thenReturn(true, true, false);
		Score Score = Mockito.mock(Score.class);
		Mockito.when(othello.getScore()).thenReturn(Score);
		List<ScoreItem> scores = new ArrayList<ScoreItem>();
		Mockito.when(Score.getPlayersScore()).thenReturn(scores);
		SilentRunner gameRunner = new SilentRunner();

		List<ScoreItem> result = gameRunner.runMatch(othello);
		// move should have been called twice.
		Mockito.verify(othello, Mockito.times(2)).move();
		// check that the correct score list is returned
		assertEquals(scores, result);

	}

}
