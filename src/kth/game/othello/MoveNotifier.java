package kth.game.othello;

import java.util.List;
import java.util.Observer;

import kth.game.othello.board.Node;

/**
 * Responsible for keeping track of and notifying observers of move events. This
 * class has circular dependency with {@link kth.game.othello.SimpleOthello} and
 * may therefore be considered a part of its implementation, existing only to
 * get around the incorrect use of the Observer type in the API.
 */
public class MoveNotifier extends SimpleOthelloNotifier {

	/**
	 * Adds an observer. The observer will be called when the game has finished.
	 *
	 * @param observer
	 *            the observer
	 */
	@Override
	public void addGameFinishedObserver(Observer observer) {
		// I only care about move observers!
		super.getUnderlyingOthello().addGameFinishedObserver(observer);
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
		assert (getUnderlyingOthello() != null) : "The move notifier must be connected to the othello before it can be used.";
		// I can handle these observers myself!
		this.addObserver(observer);
	}

	/**
	 * Notifies the observers of a move having been made, passing the swapped
	 * nodes as an argument to the notification.
	 * 
	 * @param swappedNodes
	 *            the nodes to attach to the notification.
	 */
	public void moveWasMade(List<Node> swappedNodes) {
		setChanged();
		notifyObservers(swappedNodes);
	}
}
