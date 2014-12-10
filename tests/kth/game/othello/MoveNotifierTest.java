package kth.game.othello;

import kth.game.othello.MoveNotifier;
import kth.game.othello.Othello;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Observer;

public class MoveNotifierTest {

    @Test
    public void testAddGameFinishedObserverShouldDelegate() throws Exception {
        Othello othello = Mockito.mock(Othello.class);
        MoveNotifier moveNotifier = new MoveNotifier();
        moveNotifier.initiateUnderlyingOthello(othello);

        moveNotifier.addGameFinishedObserver(null);
        Mockito.verify(othello).addGameFinishedObserver(null);
    }

    @Test
    public void testMoveNotification() throws Exception {
        Othello othello = Mockito.mock(Othello.class);
        MoveNotifier moveNotifier = new MoveNotifier();
        moveNotifier.initiateUnderlyingOthello(othello);

        Observer observer = Mockito.mock(Observer.class);
        List mockList = Mockito.mock(List.class);

        moveNotifier.addMoveObserver(observer);
        moveNotifier.moveWasMade(mockList);

        Mockito.verify(observer).update(moveNotifier, mockList);
    }
}