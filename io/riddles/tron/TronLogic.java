package io.riddles.tron;

import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.boardgame.model.Piece;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.game.exception.InvalidDataException;
import io.riddles.game.io.IOResponse;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.io.TronIOResponseType;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Direction;

public final class TronLogic {

	public static Move MoveTransformer(TronState state, IOResponse r) throws InvalidDataException {
		PieceColor c = state.getActivePieceColor();
		Coordinate coord1 = null;
		Coordinate coord2 = null;

		try {
			coord1 = getLightcycleCoordinate(state, c);
		} catch (Exception e) {
			if (e instanceof InvalidDataException) {
				/* No lightcycle found */
			}
		}
		
		if (coord1 == null) {
			throw new InvalidDataException(String.format("Lightcycle " + c  + " not found."));
		}

		if (r.getType() == TronIOResponseType.MOVE) {
			Direction direction = StringToDirectionTranformer(r.getValue());
			switch (direction) {
				case UP:
					coord2 = new Coordinate(coord1.getX(), coord1.getY()-1);
					break;
				case RIGHT:
					coord2 = new Coordinate(coord1.getX()+1, coord1.getY());
					break;
				case DOWN:
					coord2 = new Coordinate(coord1.getX(), coord1.getY()+1);
					break;
				case LEFT:
					coord2 = new Coordinate(coord1.getX()-1, coord1.getY());
					break;
				default:
					coord2 = coord1;
					break;
			}
		} else if (r.getType() == TronIOResponseType.PASS) {
			coord2 = new Coordinate(coord1.getX()+1, coord1.getY());

		}
		

		
		return new Move(coord1, coord2);
	}
	
	/**
	 * Get Coordinate of Lightcycle piece with PieceColor
	 * @param args : Tronstate, PieceColor
	 * @return : Coordinate or null;
	 * @throws InvalidDataException when no Lightcycle found.
	 */
	public static Coordinate getLightcycleCoordinate(TronState state, PieceColor c) throws InvalidDataException {
		Board b = state.getBoard();
		int size = b.size();
		
		List<Field> fields = b.getFields();
    	for (int i = 0; i < fields.size(); i++) {
    		Field f = fields.get(i);
    		if (f.getPiece().isPresent()) {
    			if (f.getPiece().get().getColor() == c && f.getPiece().get().hasType(PieceType.LIGHTCYCLE)) {
    				return new Coordinate(i%size, i/size);
    			}
    		}
    	}
    	throw new InvalidDataException (String.format("Lightcycle %s not found.", c.toString()));
	}
	
	public static Coordinate transformCoordinate(Coordinate c, Direction d) throws InvalidDataException {
		switch (d) {
			case UP:
				return new Coordinate(c.getX(), c.getY()-1);
			case RIGHT:
				return new Coordinate(c.getX()+1, c.getY());
			case DOWN:
				return new Coordinate(c.getX(), c.getY()+1);			
			case LEFT:
				return new Coordinate(c.getX()-1, c.getY());
		}
		throw new InvalidDataException("Direction unknown");
	}
	
	public static Direction StringToDirectionTranformer(String s) throws InvalidDataException {
		s = s.toLowerCase();
		switch (s) {
			case "up":
				return Direction.UP;
			case "right":
				return Direction.RIGHT;
			case "down":
				return Direction.DOWN;
			case "left":
				return Direction.LEFT;
		}
		throw new InvalidDataException("Direction unknown");
	}
	
	public static Direction getCurrentDirection(TronState state) throws InvalidDataException {
		Optional<TronState> prevState = state.getPreviousState();
		PieceColor c = state.getActivePieceColor();
		Coordinate coord1 = getLightcycleCoordinate(state, c);
		if (prevState.isPresent()) {
			Coordinate coord2 = getLightcycleCoordinate(prevState.get(), c);
			if (coord1.getX() < coord2.getX()) {
				return Direction.LEFT;
			}
			if (coord1.getX() > coord2.getX()) {
				return Direction.RIGHT;
			}
			if (coord1.getY() < coord2.getY()) {
				return Direction.UP;
			}
			if (coord1.getY() > coord2.getY()) {
				return Direction.DOWN;
			}
		}
		
		/* No previous state, figure out direction by player position */
		/* TODO: this could be more sophisticated */
		if (coord1.getX() < state.getBoard().size()/2) {
			return Direction.RIGHT;
		} else {
			return Direction.LEFT;
		}
	}
	
	
	public static SquareBoard StringToSquareBoardTransformer(String s) throws InvalidDataException {
		/* TODO: Implement this */
		throw new InvalidDataException("Not implemented.");
	}

	public static List<PieceColor> getLivingPieceColors(TronState state) {
		/* Gather all player colors */
		/* For each ask state which are alive */
		
		// TODO Auto-generated method stub
		return null;
	}
	
}
