package io.riddles.tron;

import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.game.exception.FieldNotEmptyException;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.transformer.IOResponseToMoveTransformer;
import io.riddles.tron.transformer.TronStateToIORequestTransformer;
import io.riddles.tron.validator.TronMoveValidator;
import io.riddles.util.Util;


public class TronProcessor implements Processor<TronState> {

	@Override
	public boolean hasGameEnded(TronState state) {
		List<PieceColor> l = new TronLogic().getLivingPieceColors(state);
		return (l.size() < 2);
	}

	@Override
	public TronState processException(TronState state, Exception exception) {
		if (exception instanceof FieldNotEmptyException) {
			System.out.println(state.getActivePieceColor() + " CRASHED!");
	    	/* Lightcycle crashed. */
		} else {
			exception.printStackTrace();
			System.exit(0);
		}
		
		return state;
	}

	@Override
	public TronState processInput(TronState state, IOResponse input) throws Exception {

		
		MoveValidator validator = new TronMoveValidator();	
		Move move = new IOResponseToMoveTransformer().transform(state, input);
        
        if (validator.isValid(move, state.getBoard())) {
        	state.getBoard().getFieldAt(move.getFrom()).setPiece(Optional.of(new TronPiece(PieceType.WALL, state.getActivePieceColor())));
        	state.getBoard().getFieldAt(move.getTo()).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, state.getActivePieceColor())));
        	Util.dumpBoard(state.getBoard());
        } else {
        	/* Lightcycle crashed. */
        	state.getBoard().getFieldAt(move.getFrom()).setPiece(Optional.of(new TronPiece(PieceType.WALL, state.getActivePieceColor())));
            throw new FieldNotEmptyException(String.format("Field(%s) contains wall or lightcycle.", move.getTo().toString()));
        }

        TronState newState = state;
        newState = new TronState(state, move);
        
        PieceColor c = new TronStateToIORequestTransformer().getColorToMove(state);
        System.out.println("Next: " + c);
    	newState.setActivePieceColor(c);

        //Util.dumpBoard(newState.getBoard());
		return newState;
	}
	
	@Override
    public IORequest getRequest(TronState state) {
		System.out.println("getRequest " + state.getActivePieceColor());
        TronStateToIORequestTransformer transformer = new TronStateToIORequestTransformer();
        return transformer.transform(state);
    }

}
