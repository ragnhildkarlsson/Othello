package kth.game.othello.simple;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * This class is responsible for interaction with the given players
 * 
 * @author ragnhild
 * 
 */

public class PlayerHandler {
	List<Player> players;

	/**
	 * TODO
	 * 
	 * @param players
	 * @param othello
	 *            the othello where the game is played
	 */
	public PlayerHandler(List<Player> players) {
		this.players = players;
	}

	public Player getPlayerById(String playerId) {
		// TODO implement
		return null;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Node getMove(String playerId, Othello othello) {
		Player player = getPlayerById(playerId);
		MoveStrategy moveStrategi = player.getMoveStrategy();
		return moveStrategi.move(playerId, othello);
	}

}
