package kth.game.othello;

import kth.game.othello.GameFinishedNotifier;
import kth.game.othello.Othello;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Observer;

public class GameFinishedNotifierTest {

    @Test
    public void testAddMoveObserverShouldDelegate() throws Exception {
        Othello othello = Mockito.mock(Othello.class);
        GameFinishedNotifier gameFinishedNotifier = new GameFinishedNotifier();
        gameFinishedNotifier.initiateUnderlyingOthello(othello);

        gameFinishedNotifier.addMoveObserver(null);
        Mockito.verify(othello).addMoveObserver(null);
    }

    @Test
    public void testMoveNotification() throws Exception {
        Othello othello = Mockito.mock(Othello.class);
        GameFinishedNotifier gameFinishedNotifier = new GameFinishedNotifier();
        gameFinishedNotifier.initiateUnderlyingOthello(othello);

        Observer observer = Mockito.mock(Observer.class);

        gameFinishedNotifier.addGameFinishedObserver(observer);
        gameFinishedNotifier.gameDidFinish();

        Mockito.verify(observer).update(gameFinishedNotifier, null);
    }

}