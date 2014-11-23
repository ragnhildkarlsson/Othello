package kth.game.othello.simple;

import kth.game.othello.simple.model.ImmutableNode;

/**
 * Created by spike on 11/21/14.
 */
public interface BoardObserver {

    public void nodeUpdated(ImmutableNode lastValue, ImmutableNode newValue);

}
