package kth.game.othello.player;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PlayerHandlerTest {

	@Test
	public void testGetPlayersShouldReturnCopy() throws Exception {

		List<Player> players = new ArrayList<Player>();
		Player mockPlayer = mock(Player.class);
		when(mockPlayer.getId()).thenReturn("dummy-id");
		players.add(mockPlayer);

		PlayerHandler playerHandler = new PlayerHandler(players);

		List<Player> fetchedPlayers = playerHandler.getPlayers();

		// DEBUG HELP: Assert the PlayerHandler instance actually had players
		assertNotEquals(fetchedPlayers.size(), 0);
		fetchedPlayers.clear();

		// Assert the mutation above did not propagate into the PlayerHandler
		// instance
		assertNotEquals(playerHandler.getPlayers().size(), 0);
	}

}
