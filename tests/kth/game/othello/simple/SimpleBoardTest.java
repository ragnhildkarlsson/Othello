package kth.game.othello.simple;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SimpleBoardTest {

    private final String dummyID = "dummyID";
    private final SimpleBoard dummy8x8Board = generateBoardWithSide(8);

    private List<Node> generateNDummyNodes(int numberOfNodes) {

        // Mock dummy node
        Node dummyNode = Mockito.mock(Node.class);
        Mockito.when(dummyNode.getId()).thenReturn(dummyID);

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
                fail("SimpleBoard should be immutable but could be mutated through its returned list of nodes.");
            }
        }

	}


    @Test(expected = IllegalArgumentException.class)
    public void testGetNodeOutOfRightBoundsShouldReturnNull() throws Exception {
        assertNull(dummy8x8Board.getNodeAtCoordinates(8,0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNodeOutOfBottomBoundsShouldReturnNull() throws Exception {
        assertNull(dummy8x8Board.getNodeAtCoordinates(0,-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNodeOutOfTopBoundsShouldReturnNull() throws Exception {
        assertNull(dummy8x8Board.getNodeAtCoordinates(0,8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNodeOutOfLeftBoundsShouldReturnNull() throws Exception {
        assertNull(dummy8x8Board.getNodeAtCoordinates(-1,0));
    }

    @Test
	public void testCoordinatesShouldBeCartesian() throws Exception {

        final int boardSide = 8;
        final int boardSize = boardSide * boardSide;

        // Mock upper right node to look for
		Node upperLeftNode = Mockito.mock(Node.class);
		Mockito.when(upperLeftNode.getId()).thenReturn("dskjjhd687");

        // Mock lower left node to look for
        Node lowerRightNode = Mockito.mock(Node.class);
        Mockito.when(lowerRightNode.getId()).thenReturn("dsjkhds728563");

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

    @Test
    public void testGetNodeByID() throws Exception {

        SimpleBoard board = generateBoardWithSide(1);

        assertNotNull(board.getNodeById(dummyID));

        assertNull(board.getNodeById("some random ID that shouldn't exist in board 37892783"));

    }

    @Test
    public void testGetNextNodeInDirection() throws Exception {

        final int boardSide = 3;
        final int boardSize = boardSide * boardSide;

        // Construct a board with nodeIDs like these:
        // NW N NE
        //  W D E       Where NW, N, SE etc. are the 8 directions and D is the starting node.
        // SW S SE
        List<Node> nodes = new ArrayList<Node>();
        String middleID = "middle";
        String[] directions = new String[] {
            SimpleBoard.Direction.NORTHWEST.name(),
            SimpleBoard.Direction.NORTH.name(),
            SimpleBoard.Direction.NORTHEAST.name(),
            SimpleBoard.Direction.WEST.name(),
            middleID,
            SimpleBoard.Direction.EAST.name(),
            SimpleBoard.Direction.SOUTHWEST.name(),
            SimpleBoard.Direction.SOUTH.name(),
            SimpleBoard.Direction.SOUTHEAST.name()
        };
        for (String nodeID : directions) {
            Node node = Mockito.mock(Node.class);
            Mockito.when(node.getId()).thenReturn(nodeID);
            Mockito.when(node.getXCoordinate()).thenReturn(1);
            Mockito.when(node.getYCoordinate()).thenReturn(1);
            nodes.add(node);
        }

        // Create board
        SimpleBoard board = new SimpleBoard(nodes);

        Node middleNode = board.getNodeAtCoordinates(1,1);
        assertEquals(middleID, middleNode.getId());

        for (SimpleBoard.Direction direction : SimpleBoard.Direction.values()) {
            Node nodeInDirection = board.getNextNodeInDirection(middleNode, direction);
            assertEquals(direction.name(), nodeInDirection.getId());
        }

    }
}