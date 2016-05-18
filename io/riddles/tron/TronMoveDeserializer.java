package io.riddles.tron;

import java.util.ArrayList;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidInputException;
import io.riddles.tron.field.Field;

public class TronMoveDeserializer {

    public TronMove traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private TronMove visit(String[] tokens) throws InvalidInputException {

        if (tokens.length != 1) {
            throw new InvalidInputException("Input contains more than one token");
        }
        
        int direction = 0;
        for (String token : tokens) {
        	System.out.println(token);
            direction  = this.visit(token);
        }
        return new TronMove(direction);
    }

    private int visit(String token) throws InvalidInputException {
    	
        if (!token.matches("^[A-Za-z][0-9]$")) {
            throw new InvalidInputException("Token has invalid format");
        }
		switch (token) {
			case "up":
				return TronPiece.DIR_UP;
			case "right":
				return TronPiece.DIR_RIGHT;
			case "down":
				return TronPiece.DIR_DOWN;
			case "left":
				return TronPiece.DIR_LEFT;
		}
		return -1;
    }
}