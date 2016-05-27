package io.riddles.tron;

import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.engine.io.Command;
import io.riddles.game.exception.InvalidMoveException;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.validator.TronMoveValidator;
import io.riddles.tron.visitor.TronDirectionDeserializer;


public class TronProcessor implements Processor<TronState> {

	@Override
	public Command getCommand(TronState state) {
		return null;
	}

	@Override
	public boolean hasGameEnded(TronState state) {
		
		return false;
	}

	@Override
	public TronState processException(TronState state, Exception exception) {
		return null;
	}

	@Override
	public TronState processInput(TronState state, String input) throws Exception {

		MoveValidator validator = new TronMoveValidator();

		TronDirectionDeserializer moveDeserializer = new TronDirectionDeserializer();
        Direction direction = moveDeserializer.traverse(input);
        Move move = TronLogic.DirectionToMoveTransformer(state, direction);
        TronState newState = state;
        
        if (move == null) { /* Pass */
        	
        } else {
	        if (!validator.isValid(move, state.getBoard())) {
	            // FIXME: throw a more descriptive error
	            throw new InvalidMoveException("Move not valid");
	        }
	        
	        Coordinate coord1 = TronLogic.getLightcycleCoordinate(state, state.getActivePieceColor());
	        System.out.println(state.getActivePieceColor() + " " + coord1);
	        if (coord1 == null) {
	        	/* No player lightcycle */
	        } else {
		        Coordinate coord2 = TronLogic.transformCoordinate(coord1, direction);
		        System.out.println(state.getActivePieceColor() + " coord2: " + coord2);

		        Board b = state.getBoard();
		        Field f = b.getFieldAt(coord2);
		        if (f.getPiece().isPresent()) {
		        	/* Player crash, remove lightcycle */
		        	f.setPiece(Optional.empty());
		        } else {
		        	b.getFieldAt(coord1).setPiece(Optional.of(new TronPiece(PieceType.WALL, state.getActivePieceColor())));
		        	b.getFieldAt(coord2).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, state.getActivePieceColor())));
		        	
		        	b.getFieldAt(new Coordinate(23, 23)).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, PieceColor.GREEN)));
		        }
		        newState = new TronState(state, state.getBoard(), move);
	        }
        }
		return newState;
	}

}
