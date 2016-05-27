package io.riddles.tron;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;

public final class TronLogic {

	public static Move DirectionToMoveTransformer(TronState state, int direction) {
		/* TODO: implement this */
		return new Move(new Coordinate(1,1), new Coordinate(2,2));
	}
}
