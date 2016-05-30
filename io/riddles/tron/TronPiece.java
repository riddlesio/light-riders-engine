package io.riddles.tron;

import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Piece;

public class TronPiece extends Piece {

	
	public TronPiece(PieceType type, PieceColor color) {
		super(type, color);
	}
    
    public enum PieceColor {
        YELLOW, GREEN, CYAN, PURPLE
    }
    
    public enum PieceType {
    	LIGHTCYCLE, WALL
    }
    
    public String toString() {
    	return new String(this.type.toString().charAt(0) + "" +  this.color.toString().charAt(0));
    }
}
