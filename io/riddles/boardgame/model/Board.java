package io.riddles.boardgame.model;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Field;

import java.util.List;

/**
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public interface Board {

    List<Field> getFields();
    Field getFieldAt(Coordinate coordinate) throws IndexOutOfBoundsException;
    void setFieldAt(Coordinate coordinate) throws IndexOutOfBoundsException;
    int size();
}