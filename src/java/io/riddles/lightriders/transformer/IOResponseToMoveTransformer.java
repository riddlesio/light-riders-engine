package io.riddles.lightriders.transformer;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidDataException;
import io.riddles.game.io.IOResponse;
import io.riddles.lightriders.LightridersState;
import io.riddles.lightriders.LightridersLogic;
import io.riddles.lightriders.LightridersPiece.PieceColor;
import io.riddles.lightriders.io.LightridersIOResponseType;

public class IOResponseToMoveTransformer {
	
	public Move transform (LightridersState state, IOResponse r) throws InvalidDataException {
		LightridersLogic logic = new LightridersLogic();
		
		PieceColor c = state.getActivePieceColor();
		Coordinate coord1 = null;
		Coordinate coord2 = null;

		coord1 = logic.getLightcycleCoordinate(c, state);
		
		if (r.getType() == LightridersIOResponseType.MOVE) {
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
		} else if (r.getType() == LightridersIOResponseType.PASS) {
			coord2 = logic.transformCoordinate(coord1, logic.getCurrentDirection(state));

		}
		

		
		return new Move(coord1, coord2);
	}
}
