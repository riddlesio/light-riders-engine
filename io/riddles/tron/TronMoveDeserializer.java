package io.riddles.tron;


import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidInputException;

public class TronMoveDeserializer {

    public Move traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private Move visit(String[] tokens) throws InvalidInputException {

    	if (tokens[0].equals("pass")) {
    		return new TronPassMove();
    	}
    	if (tokens[0].equals("turn_direction")) {
    		int direction = visit(tokens[1]);

    		System.out.println(direction);;
    		if (direction >= 0) {
    			return new TronMove(direction);
    		}
    	}
    	throw new InvalidInputException("Token has invalid format");
        
    }

    private int visit(String token) throws InvalidInputException {

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