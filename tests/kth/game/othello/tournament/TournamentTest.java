package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.OthelloFactory;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;
import org.mockito.Mockito;

public class TournamentTest {

	@Test
	public void test() {

		List<Player> players = new ArrayList<Player>();
		Player player1 = Mockito.mock(Player.class);
		players.add(player1);
		Player player2 = Mockito.mock(Player.class);
		players.add(player2);
		Player player3 = Mockito.mock(Player.class);
		players.add(player3);
		Player player4 = Mockito.mock(Player.class);
		players.add(player4);

		OthelloFactory othelloFactory = Mockito.mock(OthelloFactory.class);
		Square squareBoardFactory = Mockito.mock(Square.class);
		GameRunner gameRunner = Mockito.mock(GameRunner.class);

		Tournament tournament = new Tournament(players, othelloFactory, squareBoardFactory, gameRunner);

		// TODO call start and mock all the stuff fully.

	}

	private void printPlayerTotalScore(List<Match> completedMatches, List<Player> players) {
		// Get all score items:
		List<ScoreItem> scoreItems = new ArrayList<>();
		for (Match match : completedMatches) {
			scoreItems.addAll(match.getResults());
		}
		for (Player player : players) {

			int sum = scoreItems.stream().filter(e -> e.getPlayerId() == player.getId()).map(p -> p.getScore())
					.reduce(0, (soFar, score) -> score + soFar);
			System.out.println("Player " + player.getName() + " got a total score of " + sum);
		}

	}

}
