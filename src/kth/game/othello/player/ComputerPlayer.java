package kth.game.othello.player;

import kth.game.othello.SimpleRules;
import kth.game.othello.board.Node;
import kth.game.othello.board.SimpleBoard;

/**
 * Created by spike on 11/10/14.
 */
public class ComputerPlayer implements Player {

	/**
	 * The id is a unique identifier of this player in the context of all
	 * players
	 * 
	 * @return the id
	 */
	@Override
	public String getId() {
		return null;
	}

	/**
	 * The name of the player
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return null;
	}

	/**
	 * The {@link kth.game.othello.player.Player.Type} of the player
	 * 
	 * @return the type
	 */
	@Override
	public Type getType() {
		return null;
	}

	protected Node makeMove(SimpleRules rules, SimpleBoard board) {
		// TODO Method stub
		return null;
	}
}
