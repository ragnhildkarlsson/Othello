package kth.game.othello.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleBoardTest {

    private List<Node> generateNDummyNodes(int numberOfNodes) {

        // Mock dummy node
        Node dummyNode = Mockito.mock(Node.class);
        Mockito.when(dummyNode.getId()).thenReturn("dummy");

        // Construct a list of dummy nodes
        List<Node> dummyNodes = new ArrayList<Node>();
        for (int i = 0; i < numberOfNodes; i++) {
            dummyNodes.add(dummyNode);
        }

        return dummyNodes;
    }

    private SimpleBoard generateBoardWithSide(int boardSide) {

        final int boardSize = boardSide * boardSide;

        // Mock dummy nodes
        List<Node> dummyNodes = generateNDummyNodes(boardSize);

        // Create board and retrieve its nodes
        SimpleBoard board = new SimpleBoard(dummyNodes);

        return board;

    }

	@Test
	public void testGetNodesShouldReturnCopy() throws Exception {

        SimpleBoard board = generateBoardWithSide(2);
        List<Node> retrievedNodes = board.getNodes();

        // Mock one node to try and insert
        Node specificNode = Mockito.mock(Node.class);
        Mockito.when(specificNode.getId()).thenReturn("specificNode");

        retrievedNodes.set(0, specificNode);

        for (Node node : board.getNodes()) {
            if (node.getId() == specificNode.getId()) {
                fail("SimpleBoard was not immutable.");
            }
        }

	}

    @Test
    public void testGetNodeOutOfBoundsShouldReturnNull() throws Exception {

        final int boardSide = 8;

        SimpleBoard board = generateBoardWithSide(boardSide);

        Node nodeOutOfRightRange = board.getNodeAtCoordinates(8,0);
        Node nodeOutOfLeftRange = board.getNodeAtCoordinates(-1,0);
        Node nodeOutOfTopRange = board.getNodeAtCoordinates(0,8);
        Node nodeOutOfBottomRange = board.getNodeAtCoordinates(0,-1);

        assertNull(nodeOutOfBottomRange);
        assertNull(nodeOutOfTopRange);
        assertNull(nodeOutOfRightRange);
        assertNull(nodeOutOfLeftRange);

    }

    @Test
	public void testGetNodeAtCoordinatesShouldBeCartesian() throws Exception {

        final int boardSide = 8;
        final int boardSize = boardSide * boardSide;

        // Mock upper right node to look for
		Node upperLeftNode = Mockito.mock(Node.class);
		Mockito.when(upperLeftNode.getId()).thenReturn("upperRight");

        // Mock lower left node to look for
        Node lowerRightNode = Mockito.mock(Node.class);
        Mockito.when(lowerRightNode.getId()).thenReturn("lowerLeft");

        // Get dummy nodes
        List<Node> dummyNodes = generateNDummyNodes(boardSize);

        // Carefully insert specific nodes at expected positions
        int lastIndex = boardSize - 1;
        dummyNodes.set(0,upperLeftNode);
        dummyNodes.set(lastIndex, lowerRightNode);

        // Create board
		SimpleBoard board = new SimpleBoard(dummyNodes);

        // Test board
        assertEquals(board.getNodeAtCoordinates(0, 7).getId(), upperLeftNode.getId());
        assertEquals(board.getNodeAtCoordinates(7, 0).getId(), lowerRightNode.getId());

	}
}