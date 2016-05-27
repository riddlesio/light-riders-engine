package io.riddles.tron.visitor;


import io.riddles.game.exception.InvalidInputException;
import io.riddles.tron.TronPiece;

public class TronDirectionDeserializer {

    public int traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private int visit(String[] tokens) throws InvalidInputException {

    	if (tokens[0].equals("pass")) {
			return -1;
    	}
    	if (tokens[0].equals("move")) {
    		int direction = visit(tokens[1]);

    		System.out.println(direction);
    		if (direction >= 0) {
    			return 0;
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