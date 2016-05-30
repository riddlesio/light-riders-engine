package io.riddles.game.engine;

/**
 * This interface describes the functions required for consumption of
 * GameEngine implementations.
 *
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko van Meurs <niko@riddles.io>
 */
public interface GameEngine {

    /**
     * Runs the game
     * @param initialStateString - String representation of the initial State
     */
    void run(String initialStateString);
    void run();

    /**
     * TODO: extend this interface to contain all functions necessary
     *       to have the match data consumed by the Riddles.io and
     *       TheAIGames libraries.
     */
}