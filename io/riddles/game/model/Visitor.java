package io.riddles.game.model;

/**
 * ${PACKAGE_NAME}
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public interface Visitor {

    void visit(Traversible traversible);
}