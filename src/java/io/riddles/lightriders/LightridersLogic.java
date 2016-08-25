package io.riddles.lightriders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.RectangularBoard;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.game.exception.InvalidDataException;
import io.riddles.lightriders.LightridersPiece.PieceColor;
import io.riddles.lightriders.LightridersPiece.PieceType;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Direction;

public final class LightridersLogic {


	
	/**
	 * Get Coordinate of Lightcycle piece with PieceColor
	 * @param : Lightridersstate, PieceColor
	 * @return : Coordinate or null;
	 * @throws InvalidDataException when no Lightcycle found.
	 */
	public Coordinate getLightcycleCoordinate(PieceColor c, LightridersState state) throws InvalidDataException {
		RectangularBoard b = state.getBoard();
		int size = b.size();
		
		List<Field> fields = b.getFields();
    	for (int i = 0; i < fields.size(); i++) {
    		Field f = fields.get(i);
    		if (f.getPiece().isPresent()) {
    			if (f.getPiece().get().getColor() == c && f.getPiece().get().hasType(PieceType.LIGHTCYCLE)) {
    				return new Coordinate(i%b.getWidth(), i/b.getWidth());
    			}
    		}
    	}
    	throw new InvalidDataException (String.format("Lightcycle %s not found.", c.toString()));
	}
	
	public Coordinate transformCoordinate(Coordinate c, Direction d) throws InvalidDataException {
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
	
	public Direction StringToDirectionTranformer(String s) throws InvalidDataException {
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
	
	public Direction getCurrentDirection(LightridersState state) throws InvalidDataException {
		Optional<LightridersState> prevState = state.getPreviousState();
		PieceColor c = state.getActivePieceColor();
		Coordinate coord1 = getLightcycleCoordinate(c, state);
		if (prevState.isPresent()) {
			Coordinate coord2 = getLightcycleCoordinate(c, prevState.get());
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
		System.out.println(coord1.getX() + " " + state.getBoard().getWidth()/2);
		if (coord1.getX() < state.getBoard().getWidth()/2) {
			return Direction.RIGHT;
		} else {
			return Direction.LEFT;
		}
	}
	
	
	public static SquareBoard StringToSquareBoardTransformer(String s) throws InvalidDataException {
		/* TODO: Implement this */
		throw new InvalidDataException("Not implemented.");
	}	
	
	public static RectangularBoard StringToRectangularBoardTransformer(String s) throws InvalidDataException {
		/* TODO: Implement this */
		throw new InvalidDataException("Not implemented.");
	}

	public List<PieceColor> getLivingPieceColors(LightridersState state) {
		// TODO: allColors should be gathered from the IOProvider
		List<PieceColor> allColors = new ArrayList<PieceColor>();
		allColors.add(PieceColor.YELLOW);
		allColors.add(PieceColor.PURPLE);
		allColors.add(PieceColor.CYAN);
		allColors.add(PieceColor.GREEN);
		
		List<PieceColor> livingColors = new ArrayList<PieceColor>();
		for (int i = 0; i < allColors.size(); i++) {
			PieceColor c = allColors.get(i);
			if (isColorAlive(c, state)) {
				livingColors.add(c);
			}
		}
		
		return livingColors;
	}
	
	public boolean isColorAlive(PieceColor c, LightridersState state) {
		try {
			getLightcycleCoordinate(c, state);
		} catch (InvalidDataException e) {
			return false;
		}
		return true;
	}
	
}
