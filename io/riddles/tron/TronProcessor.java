package io.riddles.tron;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Move;
import io.riddles.boardgame.visitor.BoardGameMoveDeserializer;
import io.riddles.boardgame.visitor.SimpleBoardGameMoveDeserializer;
import io.riddles.engine.Processor;
import io.riddles.engine.io.Command;
import io.riddles.game.exception.InvalidMoveException;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.move.validator.TronMoveValidator;


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
		System.out.println("input: " + input);
		

		MoveValidator validator = new TronMoveValidator();

        Board board = state.getBoard();

		TronMoveDeserializer moveDeserializer = new TronMoveDeserializer();
        TronMove move = moveDeserializer.traverse(input);

        if (!validator.isValid(move, board)) {
            // FIXME: throw a more descriptive error
            throw new InvalidMoveException("Move not valid");
        }
		return state;
	}

}
