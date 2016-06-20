package io.riddles.tron.transformer;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidDataException;
import io.riddles.game.io.IOResponse;
import io.riddles.tron.TronState;
import io.riddles.tron.TronLogic;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.io.TronIOResponseType;

public class IOResponseToMoveTransformer {
	
	public Move transform (TronState state, IOResponse r) throws InvalidDataException {
		TronLogic logic = new TronLogic();
		
		PieceColor c = state.getActivePieceColor();
		Coordinate coord1 = null;
		Coordinate coord2 = null;

		coord1 = logic.getLightcycleCoordinate(c, state);
		
		if (r.getType() == TronIOResponseType.MOVE) {
			Direction direction = logic.StringToDirectionTranformer(r.getValue());
			switch (direction) {
				case UP:
					coord2 = new Coordinate(coord1.getX(), coord1.getY()-1);
					break;
				case RIGHT:
					coord2 = new Coordinate(coord1.getX()+1, coord1.getY());
					break;
				case DOWN:
					coord2 = new Coordinate(coord1.getX(), coord1.getY()+1);
					break;
				case LEFT:
					coord2 = new Coordinate(coord1.getX()-1, coord1.getY());
					break;
				default:
					coord2 = coord1;
					break;
			}
		} else if (r.getType() == TronIOResponseType.PASS) {
			coord2 = logic.transformCoordinate(coord1, logic.getCurrentDirection(state));

		}
		

		
		return new Move(coord1, coord2);
	}
}
