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

	private Set<String> playerIds = new HashSet<>();
	private Map<String, String> occupiedNodes = new HashMap<>();

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
				playerIds.add(node.getOccupantPlayerId());
				occupiedNodes.put(node.getId(), node.getOccupantPlayerId());
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
		List<ScoreItem> scoreItems = playerIds.stream().map(playerId -> new ScoreItem(playerId, getPoints(playerId)))
				.collect(Collectors.toList());
		scoreItems.sort(ScoreComparator);
		return scoreItems;
	}

	public static Comparator<ScoreItem> ScoreComparator = (scoreItem1, scoreItem2) -> {

		int score1 = scoreItem1.getScore();
		int score2 = scoreItem2.getScore();

		return Integer.compare(score2, score1);
	};

	/**
	 * Get the score of a specific player
	 *
	 * @param requestedPlayerId
	 *            the id of the player
	 * @return the score
	 */
	@Override
	public int getPoints(String requestedPlayerId) {
		return (int) occupiedNodes.values().stream().filter(nodePlayerId -> nodePlayerId.equals(requestedPlayerId))
				.count();
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
				String oldPlayer = occupiedNodes.remove(node.getId());
				if (oldPlayer != null) {
					ArrayList<String> updatedPlayers = new ArrayList<>();
					updatedPlayers.add(oldPlayer);
					notifyObservers(updatedPlayers);
				}
			} else {

				String oldPlayer = occupiedNodes.put(node.getId(), node.getOccupantPlayerId());
				List<String> updatedPlayers = new ArrayList<>();
				if (oldPlayer != null) {
					updatedPlayers.add(oldPlayer);
				}
				updatedPlayers.add(node.getOccupantPlayerId());
				setChanged();
				notifyObservers(updatedPlayers);
			}
		}
	}
}
