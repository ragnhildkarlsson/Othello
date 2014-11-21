package kth.game.othello.simple.api;

import kth.game.othello.board.Node;
import kth.game.othello.simple.board.ImmutableNode;

import java.util.Observable;
import java.util.Observer;

/**
 * // TODO
 */
public class NodeWrapper extends Observable implements Node {

    private ImmutableNode immutableNode;

    public NodeWrapper(ImmutableNode node) {
        immutableNode = node;
    }

    /**
     * Adds an observer to this node.
     *
     * @param observer an observer of this node
     */
    @Override
    public void addObserver(Observer observer) {

    }

    /**
     * The unique identifier of a node
     *
     * @return the id
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * To get the player id of the occupant player
     *
     * @return the id of the occupant player or null if the node is not marked
     */
    @Override
    public String getOccupantPlayerId() {
        return null;
    }

    /**
     * The x-coordinate of this node
     *
     * @return the x-coordinate
     */
    @Override
    public int getXCoordinate() {
        return 0;
    }

    /**
     * The y-coordinate of this node
     *
     * @return the y-coordinate
     */
    @Override
    public int getYCoordinate() {
        return 0;
    }

    /**
     * Determines of the node is occupied by any player
     *
     * @return true if the node is occupied by any player
     */
    @Override
    public boolean isMarked() {
        return false;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(java.util.Observable o, Object arg) {

    }



    //TODO Look at BoardAdapter (can we have protected methods instead?)

}
