package kth.game.othello.tournament;

import java.util.List;
import java.util.Set;

import kth.game.othello.OthelloFactory;
import kth.game.othello.SimpleOthelloFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

public class TournamentFactory {

	public Tournament generateTournament(List<Player> computerPlayers) {

		OthelloFactory othelloFactory = new SimpleOthelloFactory();
		Set<NodeData> nodesData = new Square().getNodes(8, computerPlayers);
		SilentRunner runMatchStrategy = new SilentRunner();
		MatchFactory matchFactory = new MatchFactory();

		Tournament tournament = new Tournament(computerPlayers, othelloFactory, nodesData, runMatchStrategy,
				matchFactory);
		return tournament;
	}
}
