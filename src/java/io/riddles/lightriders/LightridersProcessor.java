package io.riddles.lightriders;

import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.game.exception.FieldNotEmptyException;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;
import io.riddles.game.move.MoveValidator;
import io.riddles.lightriders.LightridersPiece.PieceColor;
import io.riddles.lightriders.LightridersPiece.PieceType;
import io.riddles.lightriders.transformer.IOResponseToMoveTransformer;
import io.riddles.lightriders.transformer.LightridersStateToIORequestTransformer;
import io.riddles.lightriders.validator.LightridersMoveValidator;
import io.riddles.util.Util;


public class LightridersProcessor implements Processor<LightridersState> {

	@Override
	public boolean hasGameEnded(LightridersState state) {
		List<PieceColor> l = new LightridersLogic().getLivingPieceColors(state);
		return (l.size() < 2);
	}

	@Override
	public LightridersState processException(LightridersState state, Exception exception) {
        LightridersState newState = new LightridersState(state, exception);

		if (exception instanceof FieldNotEmptyException) {
			FieldNotEmptyException e = (FieldNotEmptyException) exception;
			PieceColor c = e.getPieceColor();


	        newState.getBoard().getFieldAt(
					e.getCoordinate())
			.setPiece(
					Optional.of(new LightridersPiece(PieceType.WALL, c)));

			System.out.println(c + " CRASHED AT " + e.getCoordinate());
		}
		return newState;
	}

	@Override
	public LightridersState processInput(LightridersState state, IOResponse input) throws Exception {
		
		MoveValidator validator = new LightridersMoveValidator();
		Move move = new IOResponseToMoveTransformer().transform(state, input);
        LightridersState newState = new LightridersState(state, move);

        if (validator.isValid(move, state.getBoard())) {
        	newState.getBoard().getFieldAt(move.getFrom()).setPiece(Optional.of(new LightridersPiece(PieceType.WALL, state.getActivePieceColor())));
        	newState.getBoard().getFieldAt(move.getTo()).setPiece(Optional.of(new LightridersPiece(PieceType.LIGHTCYCLE, state.getActivePieceColor())));
        } else {
        	/* Lightcycle crashed. */
        	FieldNotEmptyException e = new FieldNotEmptyException(String.format("Field(%s) contains wall or lightcycle.", move.getTo().toString()), move.getFrom(), state.getActivePieceColor());
            throw e;
        }
        
        PieceColor c = new LightridersStateToIORequestTransformer().getColorToMove(state);
    	newState.setActivePieceColor(c);
        Util.dumpBoard(newState.getBoard());

		return newState;
	}
	
	@Override
    public IORequest getRequest(LightridersState state) {
        LightridersStateToIORequestTransformer transformer = new LightridersStateToIORequestTransformer();
        return transformer.transform(state);
    }

}
