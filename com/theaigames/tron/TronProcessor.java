package com.theaigames.tron;

import com.theaigames.engine.Processor;
import com.theaigames.engine.io.Command;

public class TronProcessor<State>  implements Processor<State> {

	@Override
	public Command getCommand(Object state) {
		return null;
	}

	@Override
	public boolean hasGameEnded(Object state) {
		return false;
	}

	@Override
	public State processException(Object state, Exception exception) {
		return null;
	}

	@Override
	public State processInput(Object state, String input) throws Exception {
		return null;
	}

}
