package kth.game.othello.simple;

import java.util.*;
import java.util.stream.Collectors;

import kth.game.othello.board.Node;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

/**
 * A simple implementation of the {@link kth.game.othello.score.Score}
 * interface. Keeps the score of all nodes that it observes.
 */
public class SimpleScore extends Observable implements Score, Observer {

	private Map<Node, String> occupiedNodes = new HashMap<>();

	/**
	 * Creates a SimpleScore object, initiating its score to that in the given
	 * starting nodes. Also ads itself as an observer of these nodes, it is
	 * therefore appropriate to pass all the nodes of a board as starting nodes.
	 * 
	 * @param startingNodes
	 *            the starting nodes containing any score-giving starting state.
	 *            These nodes are also observed for future changes, it is
	 *            therefore appropriate to pass all the nodes of a board as
	 *            starting nodes.
	 */
	public SimpleScore(Set<Node> startingNodes) {
		startingNodes.forEach(node -> {
			node.addObserver(this);
			if (node.getOccupantPlayerId() != null) {
				occupiedNodes.put(node, node.getOccupantPlayerId());
			}
		});
	}

	/**
	 * A list of the score of all players. The list is sorted in decreasing
	 * order regarding the score.
	 *
	 * @return a map where the keys are the id of the players and the values are
	 *         the score for that player.
	 */
	@Override
	public List<ScoreItem> getPlayersScore() {
		Set<String> playerIds = occupiedNodes.values().stream().distinct().collect(Collectors.toSet());
		List<ScoreItem> scoreItems = playerIds.stream().map(playerId -> new ScoreItem(playerId, getPoints(playerId)))
				.collect(Collectors.toList());
		return scoreItems;
	}

	/**
	 * Get the score of a specific player
	 *
	 * @param playerId
	 *            the id of the player
	 * @return the score
	 */
	@Override
	public int getPoints(String playerId) {
		return (int) occupiedNodes.values().stream().filter(nodePlayerId -> nodePlayerId == playerId).count();
	}

	/**
	 * This method is called whenever the observed object is changed. An
	 * application calls an <tt>Observable</tt> object's
	 * <code>notifyObservers</code> method to have all the object's observers
	 * notified of the change.
	 *
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            an argument passed to the <code>notifyObservers</code>
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Node) {
			Node node = (Node) o;
			if (node.getOccupantPlayerId() == null) {
				occupiedNodes.remove(node);
			} else {
				occupiedNodes.put(node, node.getOccupantPlayerId());
			}
		}
	}
}
