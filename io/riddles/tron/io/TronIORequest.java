package io.riddles.tron.io;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IORequestType;
import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.tron.TronPiece.PieceColor;

import java.util.Optional;

/**
 * io.riddles.tron.io
 * <p>
 * This file is a part of tron
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class TronIORequest implements Traversible, IORequest {

    private IORequestType type;
    private PieceColor color;
    private Optional<Coordinate> coordinate;

    public TronIORequest(PieceColor color, IORequestType type) {
        coordinate = Optional.empty();
        this.color = color;
        this.type = type;
    }

    public TronIORequest(PieceColor color, IORequestType type, Coordinate coordinate) {
        this.color = color;
        this.coordinate = Optional.of(coordinate);
        this.type = type;
    }

    public PieceColor getColor() { return color; }

    @Override
    public 	IORequestType getType() {
        return type;
    }

    public Optional<Coordinate> getCoordinate() {
        return coordinate;
    }

    @Override
    public <ReturnType> ReturnType accept(Visitor<ReturnType> visitor) {
        return visitor.visit(this);
    }

}