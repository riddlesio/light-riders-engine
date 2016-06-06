package io.riddles.game.engine;

import io.riddles.engine.Processor;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.IOResponse;

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

    @Override
    public State run(IOProvider ioProvider,
                     Processor<State> processor,
                     State initialState) {

        IORequest request;
        IOResponse response;
        State state = initialState;

        while (!processor.hasGameEnded(state)) {

            request = processor.getRequest(state);
            
            try {
            	response = ioProvider.execute(request);
                state = processor.processInput(state, response);
            } catch (Exception exception) {
                state = processor.processException(state, exception);
            }
            
        }
        return state;
    }
}