package io.riddles.game.move;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Move;

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
public interface MoveValidator {

    Boolean isApplicable(Move move, Board board);

    Boolean isValid(Move move, Board board);
}