package io.riddles.tron;


import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidInputException;

public class TronMoveDeserializer {

    public Move traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private Move visit(String[] tokens) throws InvalidInputException {

    	if (tokens[0].equals("pass")) {
			return new Move(new Coordinate(1,1), new Coordinate(2,2));
    	}
    	if (tokens[0].equals("move")) {
    		int direction = visit(tokens[1]);

    		System.out.println(direction);
    		if (direction >= 0) {
    			return TronLogic.DirectionToMoveTransformer(state, direction);
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