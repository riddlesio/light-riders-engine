package io.riddles.tron;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;

public class TronMove extends Move {

    /**
     * The direction in which to turn.
     */
    private int direction;
    
	public TronMove(int direction) {
		this.direction = direction;
	}

}
