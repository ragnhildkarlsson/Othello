package kth.game.othello.board;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleBoardTest {

    @Test
    public void testGetNodes() throws Exception {
        final int boardSize = 8; // TODO Ugly

        Node upperRight = Mockito.mock(Node.class);
        Mockito.when(upperRight.getXCoordinate()).thenReturn(boardSize-1);
        Mockito.when(upperRight.getYCoordinate()).thenReturn(0);

        List<Node> mockNodes = Mockito.mock(ArrayList.class);
        Mockito.when(mockNodes.get(0)).thenReturn(upperRight);

        SimpleBoard board = new SimpleBoard(mockNodes);

        fail("Not implemented");
    }

    @Test
    public void testGetNodeAtCoordinates() throws Exception {

    }
}