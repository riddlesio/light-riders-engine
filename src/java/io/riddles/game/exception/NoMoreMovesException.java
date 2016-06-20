package io.riddles.game.exception;

/**
 * io.riddles.game.exception
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class NoMoreMovesException extends Exception {

    public NoMoreMovesException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "No more legal moves.";
    }
}