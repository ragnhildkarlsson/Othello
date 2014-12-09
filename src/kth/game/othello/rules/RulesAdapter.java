package kth.game.othello.rules;

import java.util.List;

import kth.game.othello.board.Node;

public class RulesAdapter implements Rules {

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasValidMove(String playerId) {
		// TODO Auto-generated method stub
		return false;
	}

    public boolean isGameOver() {
        // TODO
        return false;
    }
}
