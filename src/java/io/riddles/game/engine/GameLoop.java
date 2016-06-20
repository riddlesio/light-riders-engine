package io.riddles.game.engine;

import io.riddles.engine.Processor;
import io.riddles.game.io.IOProvider;

/**
 * This interface describes the functions required for consumption by
 * a GameEngine instance.
 *
 * The generic type State is provided so you are free to implement any
 * type of state container while still being able to adhere to this
 * interface.
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko van Meurs <niko@riddles.io>
 */
public interface GameLoop<State> {

    /**
     * Runs the game from the passed initialState and returns the final state.
     * @param ioProvider   Provides engine-bot communication
     * @param processor    The game specific processor (ie. ChessGameProcessor<ChessState>)
     * @param initialState The state to start the game with
     * @return {State}
     */
    State run(IOProvider ioProvider, Processor<State> processor, State initialState);
}