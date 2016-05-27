package io.riddles.tron.visitor;


import io.riddles.boardgame.model.Direction;
import io.riddles.game.exception.InvalidInputException;
import io.riddles.tron.TronPiece;

public class TronDirectionDeserializer {

    public Direction traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private Direction visit(String[] tokens) throws InvalidInputException {

    	if (tokens[0].equals("pass")) {
			return null;
    	}
    	if (tokens[0].equals("move")) {
    		Direction direction = visit(tokens[1]);

    		System.out.println(direction);
    		if (direction == null) {
    			return null;
    		}
    		return direction;
    	}
    	throw new InvalidInputException("Token has invalid format");
        
    }

    private Direction visit(String token) throws InvalidInputException {

		switch (token) {
			case "up":
				return Direction.UP;
			case "right":
				return Direction.RIGHT;
			case "down":
				return Direction.DOWN;
			case "left":
				return Direction.LEFT;
		}
		return null;
    }
}