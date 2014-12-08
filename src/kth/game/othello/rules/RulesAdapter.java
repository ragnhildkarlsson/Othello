package kth.game.othello.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kth.game.othello.board.BoardAdapter;
import kth.game.othello.board.Node;
import kth.game.othello.model.Coordinates;
import kth.game.othello.model.ImmutableNode;
import kth.game.othello.model.ModelRules;

/**
 * This class adapts the {@link kth.game.othello.model.Rules} class to the
 * {@link kth.game.othello.rules.Rules} API.
 */
public class RulesAdapter implements Rules {

	private final ModelRules modelRules;
	private final BoardAdapter boardAdapter;

	/**
	 * Generate a new RulesAdapter given a boardAdapter and the corresponding
	 * modelRules.
	 * 
	 * @param modelRules
	 *            the rules of the board.
	 * @param boardAdapter
	 *            the board to keep the rules for.
	 */
	public RulesAdapter(ModelRules modelRules, BoardAdapter boardAdapter) {
		this.modelRules = modelRules;
		this.boardAdapter = boardAdapter;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the id of the node where the move is made
	 * @return the list of nodes that will be swapped for the given move
	 */
	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {

		Optional<Node> node = boardAdapter.getNodeById(nodeId);
		Coordinates nodeCoordinates = possiblyGetCoordinates(node);
		List<Node> nodeAdapters = new ArrayList<>();

		if (node.isPresent()) {
			Set<ImmutableNode> nodeSet = this.modelRules.getNodesToSwap(boardAdapter.getImmutableBoard(),
					nodeCoordinates, playerId);

			for (ImmutableNode immutableNode : nodeSet) {
				nodeAdapters.add(boardAdapter.getNode(immutableNode.getCoordinates()));
			}
		}
		return nodeAdapters;

	}

	/**
	 * @return the coordinates of the node or null if no node is present.
	 */
	private Coordinates possiblyGetCoordinates(Optional<Node> node) {
		Coordinates nodeCoordinates = null;
		if (node.isPresent()) {
			nodeCoordinates = new Coordinates(node.get().getXCoordinate(), node.get().getYCoordinate());
		}
		return nodeCoordinates;
	}

	/**
	 * Determines if a player is allowed to make a move at the given node.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the node id where the player wants to play
	 * @return true if the move is valid
	 */
	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		Optional<Node> node = boardAdapter.getNodeById(nodeId);
		Coordinates nodeCoordinates = possiblyGetCoordinates(node);
		return this.modelRules.validMove(boardAdapter.getImmutableBoard(), nodeCoordinates, playerId);
	}

	/**
	 * Determines if a player has any valid move.
	 *
	 * @param playerId
	 *            the id of the player
	 * @return true if the player has a valid move
	 */
	@Override
	public boolean hasValidMove(String playerId) {
		return this.modelRules.hasValidMove(boardAdapter.getImmutableBoard(), playerId);
	}

}
