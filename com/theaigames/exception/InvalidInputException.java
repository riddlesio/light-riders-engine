package com.theaigames.exception;

/**
 * io.riddles.game.exception
 * <p>
 * This file is a part of chess
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Invalid input: " + super.getMessage();
    }
}