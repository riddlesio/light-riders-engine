package io.riddles.game.io;

import java.io.IOException;

import io.riddles.game.exception.InvalidInputException;

/**
 * The functions needed by the GameLoop to
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko van Meurs <niko@riddles.io>
 */
public interface IOProvider {
	
    IOResponse execute(IORequest request) throws IOException, InvalidInputException;

}