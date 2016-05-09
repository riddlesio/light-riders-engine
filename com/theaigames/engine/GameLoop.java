package com.theaigames.engine;

import com.theaigames.engine.io.IO;
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
     * @param io - Provides engine-bot communication
     * @param processor - The game specific processor (ie. ChessGameProcessor<ChessState>)
     * @param initialState - The state to start the game with
     * @return {State}
     */
    State run(IO io, Processor<State> processor, State initialState);
}