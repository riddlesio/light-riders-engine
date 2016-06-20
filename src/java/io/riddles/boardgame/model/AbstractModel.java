package io.riddles.boardgame.model;

import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;

/**
 * ${PACKAGE_NAME}
 *
 * This file is a part of tron
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public abstract class AbstractModel implements Traversible {

    @Override
    public <ReturnType> ReturnType accept(Visitor<ReturnType> visitor) {
        return visitor.visit(this);
    }
}