package io.riddles.game.io;

/**
 * io.riddles.game.io
 * <p>
 * This file is a part of chess
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public interface IOResponse {

    IORequest getRequest();
    Enum<?> getType();
    String getValue();
}