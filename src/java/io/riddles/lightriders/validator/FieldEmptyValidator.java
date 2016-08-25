package io.riddles.lightriders.validator;

import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.boardgame.model.Piece;
import io.riddles.game.move.MoveValidator;

public class FieldEmptyValidator implements MoveValidator {

	@Override
	public Boolean isApplicable(Move move, Board board) {
		return true;
	}

	@Override
	public Boolean isValid(Move move, Board board) {
		Optional<Piece> p =  board.getFieldAt(new Coordinate(move.getTo().getX(), move.getTo().getY())).getPiece();
		return !p.isPresent();
	}

}
