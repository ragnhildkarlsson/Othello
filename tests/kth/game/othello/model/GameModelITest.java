package kth.game.othello.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Integration test of the main class GameModel, including all classes in the
 * model package.
 * 
 * @author mikael
 *
 */
public class GameModelITest {

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

	/**
	 * Try that a game with 3 players and a non square board can be started, and
	 * that each player can perform a valid move according to the boards below:
	 * 
	 * <pre>
	 *    0 1 2 3 4       0 1 2 3 4       0 1 2 3 4       0 1 2 3 4
	 *  0 x	- - - x     0 x	- - - x     0 x	- - - x     0 x	- - - x 
	 *  1 - c a b -     1 - c a b -     1 B B B b -     1 b b b b -
	 *  2 - b c a - --> 2 - b c a - --> 2 - b c a - --> 2 - b c a -   
	 *  3 - a b c -     3 - a A c -     3 - a a c -     3 C C C c -
	 *  4 x - - - x     4 x A - - x     4 x a - - x	    4 x a - - x 
	 *  
	 *  where:
	 *  - marks unmarked node
	 * a,b and c represents nodes marked by player 1,2 and 3 respectively.
	 * Capital letters mark newly swapped nodes.
	 * 
	 * <pre>
	 */
	@Test
	public void TestWith3Players() {
		ImmutableBoard startingBoard = generateBoard();

		List<String> playerIds = new ArrayList<>();
		playerIds.add(player1Id);
		playerIds.add(player2Id);
		playerIds.add(player3Id);
		ModelRules rules = new ModelRules();
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

		// Check that the second game state is in order
		assertEquals("Player2 should be second in turn", player2Id, secondGameState.getPlayerInTurn());
		assertEquals("Node at (1,4) should be occupied", true,
				secondGameState.getBoard().getNodeAtCoordinates(new Coordinates(1, 4)).isMarked());
		assertEquals("Player2 should not be able to play at an occupied node", false,
				secondGameState.isMoveValid(player2Id, new Coordinates(1, 4)));

		// Perform a move with player 2 that should be valid
		Optional<GameState> optionalThirdGameState = secondGameState.tryMove(player2Id, new Coordinates(0, 1));
		assertEquals("Should get a new GameState", true, optionalThirdGameState.isPresent());
		GameState thirdGameState = optionalThirdGameState.get();

		// Check that player3 is now in turn
		assertEquals("Player 3 should be in turn", player3Id, thirdGameState.getPlayerInTurn());
		assertEquals("Should not be able to play at non existing node", false,
				thirdGameState.isMoveValid(player3Id, new Coordinates(0, 0)));
		Optional<GameState> optionalFourthGameState = thirdGameState.tryMove(player3Id, new Coordinates(0, 3));
		assertEquals("Player 3 should be able to play at (0,3)", true, optionalFourthGameState.isPresent());

		// Check that the game is still not over
		assertEquals(false, optionalFourthGameState.get().isGameOver());

	}

	@Test
	public void testThatGameOverCanBeReached() {
		// Generate a board consisting of one row:
		// a b -
		// where a and b is nodes marked by player 1 and 2 respectively, and "-"
		// is an unmarked node.
		Set<ImmutableNode> nodes = getRowOfNodes("a b -", 0);
		ImmutableBoard startingBoard = new ImmutableBoard(nodes);

		List<String> playerIds = new ArrayList<>();
		playerIds.add(player1Id);
		playerIds.add(player2Id);
		ModelRules rules = new ModelRules();
		GameModelFactory gameModelFactory = new GameModelFactory(startingBoard, playerIds, rules);
		GameModel gameModel = gameModelFactory.getNewGameModel(player1Id);

		// Get the first GameState and perform the only valid move for player 1
		GameState firstGameState = gameModel.getGameState();
		Optional<GameState> optionalSecondGameState = firstGameState.tryMove(player1Id, new Coordinates(2, 0));
		// Check that the new gameState has no player in turn and is game over
		GameState secondGameState = optionalSecondGameState.get();
		assertNull(secondGameState.getPlayerInTurn());
		assertEquals(true, secondGameState.isGameOver());

	}

	/**
	 * Given the following board
	 * 
	 * <pre>
	 * b * b a *
	 * where 
	 * a indicate a node marked by player a
	 * b indicate a node marked by player b
	 * * indicate a unmarked node.
	 * and the following moves are made
	 * b a a a *
	 * b b b b b
	 * then a second call to undo() will return 
	 * a game state with the following board  
	 * b * b a *
	 * </pre>
	 */
	@Test
	public void testThatUndoTwiceReturnCorrectState() {
		Set<ImmutableNode> nodes = getRowOfNodes("b - b a -", 0);
		ImmutableBoard startingBoard = new ImmutableBoard(nodes);
		List<String> playerIds = new ArrayList<>();
		String playerA = player1Id;
		String playerB = player2Id;
		playerIds.add(playerA);
		playerIds.add(playerB);
		ModelRules rules = new ModelRules();
		GameModelFactory gameModelFactory = new GameModelFactory(startingBoard, playerIds, rules);
		GameModel gameModel = gameModelFactory.getNewGameModel(playerA);
		gameModel.move(playerA, new Coordinates(1, 0));
		gameModel.move(playerB, new Coordinates(4, 0));

		gameModel.undo();
		GameState stateAfterTwiceUndo = gameModel.undo().get();
		ImmutableBoard boardAfterTwiceUndo = stateAfterTwiceUndo.getBoard();

		assertEquals(startingBoard, boardAfterTwiceUndo);
	}

	@Test
	public void testThatUndoOnStartStateReturnAnEmptyOptional() {
		GameState mockStartState = Mockito.mock(GameState.class);
		GameModel gameModel = new GameModel(mockStartState);
		assertEquals(false, gameModel.undo().isPresent());
	}

}
