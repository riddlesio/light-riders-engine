package io.riddles.boardgame.model;

/**
 * ${PACKAGE_NAME}
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public abstract class Piece<PieceType extends Enum<PieceType>, PieceColor extends Enum<PieceColor>> extends AbstractModel {

    protected PieceColor color;
    protected PieceType type;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public Boolean hasColor(PieceColor color) {
        return this.color == color;
    }

    public Boolean hasType(PieceType type) {
        return this.type == type;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }
}