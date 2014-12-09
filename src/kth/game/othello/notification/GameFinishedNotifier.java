package kth.game.othello.notification;

import java.util.Observer;

/**
 * Responsible for keeping track of and notifying observers of game finished
 * events. This class has circular dependency with
 * {@link kth.game.othello.SimpleOthello} and may therefore be considered a part
 * of its implementation, existing only to get around the incorrect use of the
 * Observer type in the API.
 */
public class GameFinishedNotifier extends Notifier {

	/**
	 * Adds an observer. The observer will be called when the game has finished.
	 *
	 * @param observer
	 *            the observer
	 */
	@Override
	public void addGameFinishedObserver(Observer observer) {
		assert (getUnderlyingOthello() != null) : "The move notifier must be connected to the othello before it can be used.";
		// I can handle these observers myself!
		this.addObserver(observer);
	}

	/**
	 * Adds an observer. The observers update will be called when a move has
	 * finished including the nodes that have changed by the move.
	 *
	 * @param observer
	 *            the observer
	 */
	@Override
	public void addMoveObserver(Observer observer) {
		// I only care about game finished observers!
		super.getUnderlyingOthello().addMoveObserver(observer);
	}

    public void gameHasFinished() {
        setChanged();
        notifyObservers();
    }
}
