package io.riddles.engine;

import io.riddles.engine.io.Command;
import io.riddles.engine.io.IO;

/**
 * Implements of the GameLoop interface in a way which is suitable
 * for most games.
 *
 * The generic type State is provided so the you are free to implement any
 * type of state container while still being able to use this class.
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko van Meurs <niko@riddles.io>
 */
public class SimpleGameLoop<State> implements GameLoop<State> {

    /**
     * @inheritdoc
     */
    @Override
    public State run(IO io, Processor<State> processor, State initialState) {

        Command command;
        String input;
        State state = initialState;

        while (!processor.hasGameEnded(state)) {

            command = processor.getCommand(state);
            input = io.execute(command);

            try {
                state = processor.processInput(state, input);

            } catch (Exception exception) {

                state = processor.processException(state, exception);
            }
        }

        return state;
    }
}