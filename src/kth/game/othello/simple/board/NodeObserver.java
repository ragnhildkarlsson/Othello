package kth.game.othello.simple.board;

/**
 * Created by spike on 11/21/14.
 */
public interface NodeObserver {

    public void nodeUpdated(ImmutableNode lastValue, ImmutableNode newValue);

}
