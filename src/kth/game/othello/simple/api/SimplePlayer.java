package kth.game.othello.simple.api;

import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * Created by spike on 11/22/14.
 */
public class SimplePlayer implements Player {
    /**
     * The id is a unique identifier of this player in the context of all players
     *
     * @return the id
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * The current move strategy of the player
     *
     * @return the move strategy
     * @throws UnsupportedOperationException if the player is of {@link kth.game.othello.player.Player.Type} HUMAN
     */
    @Override
    public MoveStrategy getMoveStrategy() {
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

    /**
     * Sets a new move strategy on the player. The player must be of {@link kth.game.othello.player.Player.Type} COMPUTER
     *
     * @param moveStrategy
     * @throws UnsupportedOperationException if the player is of {@link kth.game.othello.player.Player.Type} HUMAN
     */
    @Override
    public void setMoveStrategy(MoveStrategy moveStrategy) {

    }
}
