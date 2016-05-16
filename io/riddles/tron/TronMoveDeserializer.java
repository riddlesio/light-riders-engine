package io.riddles.tron;

import java.util.ArrayList;

import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Move;
import io.riddles.game.exception.InvalidInputException;

public class TronMoveDeserializer {

    public TronMove traverse(String input) throws InvalidInputException {

        String[] tokens = input.trim().split(" ");

        return this.visit(tokens);
    }

    private TronMove visit(String[] tokens) throws InvalidInputException {

        if (tokens.length != 2) {
            throw new InvalidInputException("Input contains more than two tokens");
        }

        ArrayList<Coordinate> coordinates = new ArrayList<>();

        for (String token : tokens) {
        	System.out.println(token);
            //Coordinate coordinate = this.visit(token);
            //coordinates.add(coordinate);
        }

        Coordinate from = coordinates.get(0);
        Coordinate to   = coordinates.get(1);

        return new TronMove();
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