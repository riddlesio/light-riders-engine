package io.riddles.lightriders;

import io.riddles.boardgame.model.Piece;

public class LightridersPiece extends Piece {

	
	public LightridersPiece(PieceType type, PieceColor color) {
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
