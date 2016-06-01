package io.riddles.tron;

import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.engine.io.Command;
import io.riddles.game.exception.FieldNotEmptyException;
import io.riddles.game.exception.InvalidMoveException;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.io.TronIORequest;
import io.riddles.tron.io.TronIOResponse;
import io.riddles.tron.transformer.TronStateToIORequestTransformer;
import io.riddles.tron.validator.TronMoveValidator;
import io.riddles.tron.visitor.TronDirectionDeserializer;


public class TronProcessor implements Processor<TronState> {

	@Override
	public boolean hasGameEnded(TronState state) {
		
		return false;
	}

	@Override
	public TronState processException(TronState state, Exception exception) {
		return state;
	}

	@Override
	public TronState processInput(TronState state, IOResponse input) throws Exception {

		MoveValidator validator = new TronMoveValidator();

		//TronDirectionDeserializer moveDeserializer = new TronDirectionDeserializer();
        //Direction direction = moveDeserializer.traverse(input);
		
        //Move move = TronLogic.DirectionToMoveTransformer(state, direction);
		
		Move move = TronLogic.MoveTransformer(state, input);
        
        TronState newState = state;
        Coordinate coord1 = TronLogic.getLightcycleCoordinate(state, state.getActivePieceColor());
        Coordinate coord2 = null;
        
        if (move == null) { /* Pass */
        	Direction d = TronLogic.getCurrentDirection(state);
        	System.out.println("getCurrentDirection gave " + d);
        	//TronLogic.DirectionToMoveTransformer(state, d);
        	//coord2 = TronLogic.transformCoordinate(coord1, Direction.DOWN);
        } else {
	        if (!validator.isValid(move, state.getBoard())) {
	            // FIXME: throw a more descriptive error
	            throw new InvalidMoveException("Move not valid");
	        }
	        
	        System.out.println(state.getActivePieceColor() + " " + coord1);
	        if (coord1 == null) {
	        	/* No player lightcycle */
	        } else {
		        coord2 = TronLogic.transformCoordinate(coord1, Direction.UP);
	        }
        }
        System.out.println(state.getActivePieceColor() + " coord2: " + coord2);

        Board b = state.getBoard();
        Field f = b.getFieldAt(coord2);
        if (f.getPiece().isPresent()) {
        	throw new FieldNotEmptyException(String.format("Field(%s) contains wall or lightcycle.", coord2.toString()));
        }
    	b.getFieldAt(coord1).setPiece(Optional.of(new TronPiece(PieceType.WALL, state.getActivePieceColor())));
    	b.getFieldAt(coord2).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, state.getActivePieceColor())));

        newState = new TronState(state, state.getBoard(), move);
		return newState;
	}
	
	@Override
    public IORequest getRequest(TronState state) {

        TronStateToIORequestTransformer transformer = new TronStateToIORequestTransformer();
        return transformer.transform(state);
    }

}
