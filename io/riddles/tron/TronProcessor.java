package io.riddles.tron;

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
		return state;
	}

}
