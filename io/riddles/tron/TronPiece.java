package io.riddles.tron;

import io.riddles.boardgame.model.Piece;

public class TronPiece extends Piece {

	public TronPiece(PieceColor color) {
		super(PieceType.LIGHTCYCLE, color);
	}
    
    public enum PieceColor {
        YELLOW, GREEN, CYAN, PURPLE
    }
    
    public enum PieceType {
    	LIGHTCYCLE
    }
    
    public String toString() {
    	return this.color.toString();
    }
}
