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
        TronState newState = new TronState(state, exception);

		if (exception instanceof FieldNotEmptyException) {
			FieldNotEmptyException e = (FieldNotEmptyException) exception;
			PieceColor c = e.getPieceColor();


	        newState.getBoard().getFieldAt(
					e.getCoordinate())
			.setPiece(
					Optional.of(new TronPiece(PieceType.WALL, c)));

			System.out.println(c + " CRASHED AT " + e.getCoordinate());
		}
		return newState;
	}

	@Override
	public TronState processInput(TronState state, IOResponse input) throws Exception {
		
		MoveValidator validator = new TronMoveValidator();	
		Move move = new IOResponseToMoveTransformer().transform(state, input);
        TronState newState = new TronState(state, move);

        if (validator.isValid(move, state.getBoard())) {
        	newState.getBoard().getFieldAt(move.getFrom()).setPiece(Optional.of(new TronPiece(PieceType.WALL, state.getActivePieceColor())));
        	newState.getBoard().getFieldAt(move.getTo()).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, state.getActivePieceColor())));
        } else {
        	/* Lightcycle crashed. */
        	FieldNotEmptyException e = new FieldNotEmptyException(String.format("Field(%s) contains wall or lightcycle.", move.getTo().toString()), move.getFrom(), state.getActivePieceColor());
            throw e;
        }
        
        PieceColor c = new TronStateToIORequestTransformer().getColorToMove(state);
    	newState.setActivePieceColor(c);
        Util.dumpBoard(newState.getBoard());

		return newState;
	}
	
	@Override
    public IORequest getRequest(TronState state) {
        TronStateToIORequestTransformer transformer = new TronStateToIORequestTransformer();
        return transformer.transform(state);
    }

}
