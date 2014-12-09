package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;

public class GameRunner {

	public List<ScoreItem> runMatch(Othello othello) {
		// run the game till no more moves can be made
		while (othello.isActive()) {
			othello.move();
		}

		return othello.getScore().getPlayersScore();
	}

}
