package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.score.ScoreItem;

public interface RunMatchStrategy {

	public List<ScoreItem> runMatch(Othello othello);

}
