package io.riddles.game.exception;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.lightriders.LightridersPiece.PieceColor;

public class FieldNotEmptyException extends InvalidMoveException {
	private Coordinate coord;
	private PieceColor c;
	
	public FieldNotEmptyException(String message, Coordinate coord, PieceColor c) {
        super(message);
		this.coord = coord; this.c = c;
	}
    
	public Coordinate getCoordinate() { return this.coord; }
	public PieceColor getPieceColor() { return this.c; }
	
    
}
