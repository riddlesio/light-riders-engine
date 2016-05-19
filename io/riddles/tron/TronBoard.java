package io.riddles.tron;

import io.riddles.boardgame.model.SquareBoard;
import io.riddles.tron.TronPiece.PieceColor;

import java.util.Optional;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Field;

public class TronBoard extends SquareBoard {

	public TronBoard(int size) {
		super(size);
	}
	
	public Coordinate getLightcycleCoordinateWithColor(TronPiece.PieceColor pieceColor) {
    	for (int i = 0; i < fields.size(); i++) {
    			
    		Field f = fields.get(i);
    		if (f.getPiece().isPresent()) {
    			if (f.getPiece().get().getDirection() == 1) {
    				
    			}
    		}
    		fields.add(new Field(Optional.of(new TronPiece(PieceColor.CYAN))));
    	}
		return new Coordinate(1,1);
	}


}
