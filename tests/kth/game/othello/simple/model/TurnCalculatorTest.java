package kth.game.othello.simple.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class TurnCalculatorTest {

	/**
	 * Given that the game is active, check that getPlayerInTurn finds the next
	 * player with a valid move.
	 */
	@Test
	public void testGetPlayerInTurnWithActiveGame() {
		String player1Id = "1";
		String player2Id = "2";
		String player3Id = "3";
		List<String> playerIds = new ArrayList<>();
		playerIds.add(player1Id);
		playerIds.add(player2Id);
		playerIds.add(player3Id);

		Rules rules = Mockito.mock(Rules.class);
		Mockito.when(rules.hasValidMove(null, player1Id)).thenReturn(true);
		Mockito.when(rules.hasValidMove(null, player2Id)).thenReturn(true);
		Mockito.when(rules.hasValidMove(null, player3Id)).thenReturn(false);

		TurnCalculator turnCalculator = new TurnCalculator(playerIds);

		String playerInTurn = turnCalculator.getPlayerInTurn(player2Id, null, rules);
		// Should return player1 since player2 was the previous player.
		assertEquals(player1Id, playerInTurn);
		//Check that the turncalculator can jump to next player if this player
		//does not have a valid move
		
		Mockito.when(rules.hasValidMove(null, player1Id)).thenReturn(true);
		Mockito.when(rules.hasValidMove(null, player2Id)).thenReturn(false);
		Mockito.when(rules.hasValidMove(null, player3Id)).thenReturn(true);
		playerInTurn = turnCalculator.getPlayerInTurn(player1Id, null, rules);
		// Should return player3 since player2 can not do any move.
		assertEquals(player1Id, playerInTurn);
				


	}

	/**
	 * Check that null is returned when no player has a valid move.
	 */
	@Test
	public void testgetPlayerInTurnWithGameOver() {
		String player1Id = "1";
		String player2Id = "2";
		String player3Id = "3";
		List<String> playerIds = new ArrayList<>();
		playerIds.add(player1Id);
		playerIds.add(player2Id);
		playerIds.add(player3Id);

		Rules rules = Mockito.mock(Rules.class);
		Mockito.when(rules.hasValidMove(null, player1Id)).thenReturn(false);
		Mockito.when(rules.hasValidMove(null, player2Id)).thenReturn(false);
		Mockito.when(rules.hasValidMove(null, player3Id)).thenReturn(false);

		TurnCalculator turnCalculator = new TurnCalculator(playerIds);

		String playerInTurn = turnCalculator.getPlayerInTurn(player1Id, null, rules);

		assertEquals(null, playerInTurn);

	}

}
