package io.riddles.tron;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Move;
import io.riddles.engine.Processor;
import io.riddles.engine.io.Command;


public class TronProcessor<State>  implements Processor<State> {

	@Override
	public Command getCommand(State state) {
		return null;
	}

	@Override
	public boolean hasGameEnded(State state) {
		return false;
	}

	@Override
	public State processException(State state, Exception exception) {
		return null;
	}

	@Override
	public State processInput(State state, String input) throws Exception {
		System.out.println("input: " + input);
		/*
		MoveValidator validator = new TronMoveValidator();

        Board board = state.getBoard();

        BoardGameMoveDeserializer moveDeserializer = new SimpleBoardGameMoveDeserializer();
        Move move = moveDeserializer.traverse(input);

        if (!validator.isValid(move, board)) {
            // FIXME: throw a more descriptive error
            throw new InvalidMoveException("Move not valid");
        }
        */
		return state;
	}

}
