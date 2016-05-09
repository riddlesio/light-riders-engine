package io.riddles.engine;

import io.riddles.engine.io.Command;
import io.riddles.exception.InvalidMoveException;


/**
 * This interface describes the functions required for consumption by
 * a GameLoop instance.
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
public interface Processor<State> {

    /**
     * Returns the next command according to the provided state
     * @param state - The state to be interpreted to generate the Command
     * @return {Command}
     */
    Command getCommand(State state);

    /**
     * Determines whether the game has ended for the provided state
     * @param state - The state to be interpreted
     * @return {boolean}
     */
    boolean hasGameEnded(State state);

    /**
     * Returns a new error state
     * @param state - The previous game state
     * @param exception - The exception thrown when trying to execute Processor.processInput
     * @return {State}
     */
    State processException(State state, Exception exception);

    /**
     * Processes the input and returns the new state
     * @param state - The previous state
     * @param input - The input to process
     * @return {State}
     * @throws Exception - When input cannot be converted to a move or the move is invalid
     */
    State processInput(State state, String input) throws Exception;
}