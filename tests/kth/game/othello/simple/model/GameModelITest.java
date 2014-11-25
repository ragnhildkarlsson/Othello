package kth.game.othello.simple.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

/**
 * Integration test of the main class GameModel, including all classes in the
 * model package.
 * 
 * @author mikael
 *
 */
public class GameModelITest {

	public static void main(String[] args) {
		GameModelITest test = new GameModelITest();
		test.TestWith3Players();
	}

	private final String player1Id = "a";
	private final String player2Id = "b";
	private final String player3Id = "c";

	/**
	 * Generate a board with three players according to:
	 * 
	 * <pre>
	 *    0 1 2 3 4
	 *  0 x	- - - x
	 *  1 - c a b -
	 *  2 - b c a -
	 *  3 - a b c -
	 *  4 x - - - x 
	 *  
	 *  where:
	 *  - marks unmarked node
	 * a,b and c represents nodes marked by player 1,2 and 3 respectively.
	 * 
	 * <pre>
	 */
	private ImmutableBoard generateBoard() {

		// Create each row:
		Set<ImmutableNode> nodes = new HashSet<>();
		nodes.addAll(getRowOfNodes("x - - - x", 0));
		nodes.addAll(getRowOfNodes("- c a b -", 1));
		nodes.addAll(getRowOfNodes("- b c a -", 2));
		nodes.addAll(getRowOfNodes("- a b c -", 3));
		nodes.addAll(getRowOfNodes("x - - - x", 4));

		return new ImmutableBoard(nodes);
	}

	private Set<ImmutableNode> getRowOfNodes(String rowDescription, int rowNumber) {
		String[] characters = rowDescription.split(" ");

		Set<ImmutableNode> nodeRow = new HashSet<>();
		for (int i = 0; i < characters.length; i++) {
			String character = characters[i];

			Coordinates coord = new Coordinates(i, rowNumber);
			switch (character) {
			case "-":
				nodeRow.add(new ImmutableNode(coord, null));
				break;
			case "a":
				nodeRow.add(new ImmutableNode(coord, player1Id));
				break;
			case "b":
				nodeRow.add(new ImmutableNode(coord, player2Id));
				break;
			case "c":
				nodeRow.add(new ImmutableNode(coord, player3Id));
				break;
			default:
				break;
			}
		}
		return nodeRow;
	}

	@Test
	public void TestWith3Players() {
		ImmutableBoard startingBoard = generateBoard();

		List<String> playerIds = new ArrayList<>();
		playerIds.add(player1Id);
		playerIds.add(player2Id);
		playerIds.add(player3Id);
		Rules rules = new Rules();
		GameModelFactory gameModelFactory = new GameModelFactory(startingBoard, playerIds, rules);
		GameModel gameModel = gameModelFactory.getNewGameModel(player1Id);

		// Get the first GameState and check that its correct and immutable
		GameState firstGameState = gameModel.getGameState();
		assertEquals("Player1 should be first in turn", player1Id, firstGameState.getPlayerInTurn());
		assertEquals("There should be 21 nodes on the board", 21, firstGameState.getBoard().getNodes().size());

		// Check that the move at (1,4) is valid for player1 then perform it
		assertEquals(true, firstGameState.isMoveValid(player1Id, new Coordinates(1, 4)));
		Optional<GameState> optionalSecondGameState = firstGameState.tryMove(player1Id, new Coordinates(1, 4));
		assertEquals("Should get a new GameState", true, optionalSecondGameState.isPresent());
		GameState secondGameState = optionalSecondGameState.get();

		// Check that the second game state has player2 in turn
		assertEquals("Player2 should be second in turn", player2Id, secondGameState.getPlayerInTurn());

	}
}
