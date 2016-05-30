package io.riddles.game.model;

/**
 * ${PACKAGE_NAME}
 *
 * This file is a part of chess
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public interface Traversible {

    <ReturnType> ReturnType accept(Visitor<ReturnType> visitor);
}