package io.riddles.lightriders.io;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.game.io.IORequest;
import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.lightriders.LightridersPiece.PieceColor;

import java.util.Optional;

/**
 * io.riddles.lightriders.io
 * <p>
 * This file is a part of lightriders
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class LightridersIORequest implements Traversible, IORequest {

    private LightridersIORequestType type;
    private PieceColor color;
    private Optional<Coordinate> coordinate;

    public LightridersIORequest(PieceColor color, LightridersIORequestType type) {
        coordinate = Optional.empty();
        this.color = color;
        this.type = type;
    }

    public LightridersIORequest(PieceColor color, LightridersIORequestType type, Coordinate coordinate) {
        this.color = color;
        this.coordinate = Optional.of(coordinate);
        this.type = type;
    }

    public PieceColor getColor() { return color; }

    @Override
    public LightridersIORequestType getType() {
        return type;
    }

    public Optional<Coordinate> getCoordinate() {
        return coordinate;
    }

    @Override
    public <ReturnType> ReturnType accept(Visitor<ReturnType> visitor) {
        return visitor.visit(this);
    }
    
    public String toString() {
    	return "action " +  type.toString().toLowerCase();
    }
}