package io.riddles.tron;

import io.riddles.boardgame.model.Piece;

public class TronPiece extends Piece {
	public static final int DIR_UP = 0;
	public static final int DIR_RIGHT = 90;
	public static final int DIR_DOWN = 180;
	public static final int DIR_LEFT = 270;
	public static final int DIR_NONE = -1;
	
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
