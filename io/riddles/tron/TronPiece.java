package io.riddles.tron;

import io.riddles.boardgame.model.Piece;

public class TronPiece extends Piece {
	
    protected int direction;

	
	public TronPiece(PieceColor color) {
		super(PieceType.LIGHTCYCLE, color);
	}
    
    public enum PieceColor {
        YELLOW, GREEN, CYAN, PURPLE
    }
    
    public enum PieceType {
    	LIGHTCYCLE, WALL
    } 
    
    public String toString() {
    	return this.color.toString();
    }
    
    public void setDirection(int direction) {
    	this.direction = direction;
    }
    
    public int getDirection() {
    	return this.direction;
    }
}
