package io.riddles.boardgame.visitor;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidInputException;

import java.util.ArrayList;

/**
 * io.riddles.boardgame.visitor
 * <p>
 * This file is a part of chess
 * <p>
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public class SimpleBoardGameMoveDeserializer implements BoardGameMoveDeserializer {

    public Move traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private Move visit(String[] tokens) throws InvalidInputException {

        if (tokens.length != 2) {
            throw new InvalidInputException("Input contains more than two tokens");
        }

        ArrayList<Coordinate> coordinates = new ArrayList<>();

        for (String token : tokens) {
            Coordinate coordinate = this.visit(token);
            coordinates.add(coordinate);
        }

        Coordinate from = coordinates.get(0);
        Coordinate to   = coordinates.get(1);

        return new Move(from, to);
    }

    private Coordinate visit(String token) throws InvalidInputException {

        if (!token.matches("^[A-Za-z][0-9]$")) {
            throw new InvalidInputException("Token has invalid format");
        }

        ArrayList<Integer> digits = new ArrayList<>();

        for (Character character : token.toCharArray()) {
            digits.add(this.visit(character));
        }

        Integer x = digits.get(0);
        Integer y = digits.get(1);

        return new Coordinate(x, y);
    }

    private Integer visit(Character c) {

        if (Character.isDigit(c)) {
            return c.hashCode() - "0".hashCode();
        }

        c = Character.toLowerCase(c);

        return c.hashCode() - "a".hashCode();
    }
}