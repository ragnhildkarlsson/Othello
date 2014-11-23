package kth.game.othello.simple;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

public class SimpleScore implements Score, Observer {

	public SimpleScore() {

	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPoints(String playerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
}
