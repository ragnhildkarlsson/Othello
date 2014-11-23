package kth.game.othello.simple;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * TODO
 */
public class SimpleStrategy implements MoveStrategy {
    /**
     * @return the name of the strategy
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Determines which node the given player will move at.
     *
     * @param playerId the id of the player that will make a move
     * @param othello  the othello game on which to make the move
     * @return the node where the player wants to move. If the player is not able to move then null is returned.
     */
    @Override
    public Node move(String playerId, Othello othello) {
        return null;
    }
}
