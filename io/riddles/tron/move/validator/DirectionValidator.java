package io.riddles.tron.move.validator;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Move;
import io.riddles.game.move.MoveValidator;

public class DirectionValidator implements MoveValidator {

	@Override
	public Boolean isApplicable(Move move, Board board) {
		return true;
	}

	@Override
	public Boolean isValid(Move move, Board board) {
		return true;
	}


}
