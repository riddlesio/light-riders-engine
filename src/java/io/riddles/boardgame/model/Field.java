package io.riddles.boardgame.model;

import java.util.Optional;

/**
 * ${PACKAGE_NAME}
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class Field extends AbstractModel {

    protected Optional<Piece> maybePiece;

    public Field(Optional<Piece> maybePiece) {

        this.maybePiece = maybePiece;
    }

    public Optional<Piece> getPiece() {

        return maybePiece;
    }

    public void setPiece(Optional<Piece> maybePiece) {
        this.maybePiece = maybePiece;
    }
}