package io.riddles.tron;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.engine.io.Command;
import io.riddles.game.exception.InvalidMoveException;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.move.validator.TronMoveValidator;
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
        int direction = moveDeserializer.traverse(input);
        
        Move move = TronLogic.DirectionToMoveTransformer(state, direction);

        
        if (!validator.isValid(move, state.getBoard())) {
            // FIXME: throw a more descriptive error
            throw new InvalidMoveException("Move not valid");
        }
		return state;
	}

}
