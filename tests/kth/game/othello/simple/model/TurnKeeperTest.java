package kth.game.othello.simple.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class TurnKeeperTest {

	/**
	 * Given that we create a turnKeeper with a starting player with no valid
	 * move, check that getPlayerInTurn finds the player with a valid move.
	 * 
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
		Mockito.when(rules.hasValidMove(null, player2Id)).thenReturn(false);
		Mockito.when(rules.hasValidMove(null, player3Id)).thenReturn(false);

		TurnKeeper turnKeeper = new TurnKeeper(playerIds, player2Id);

		String playerInTurn = turnKeeper.getPlayerInTurn(rules, null);

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

		TurnKeeper turnKeeper = new TurnKeeper(playerIds, player2Id);

		String playerInTurn = turnKeeper.getPlayerInTurn(rules, null);

		assertEquals(null, playerInTurn);

	}

}
