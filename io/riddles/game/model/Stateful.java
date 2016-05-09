package io.riddles.game.model;
import java.util.Optional;

/**
 * io.riddles.game.model
 * <p>
 * This file is a part of chess
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public interface Stateful<State> {

    Optional<Exception> getException();
    Optional<State> getPreviousState();
}