package io.riddles.boardgame.model;

/**
 * @author Niko van Meurs <niko@riddles.io>
 */
public final class Move {

    /**
     * Coordinate of the Field from which should be moved
     */
    private Coordinate from;

    /**
     * Coordinate of the Field to which a Piece should be moved
     */
    private Coordinate to;

    /**
     *
     * @param from Points to the field from which the move is executed
     * @param to   Points to the field to which the move is executed
     */
    public Move(Coordinate from, Coordinate to) {

        this.from = from;
        this.to = to;
    }

    /**
     * Returns the coordinate for the move's source Field
     * @return Coordinate
     */
    public Coordinate getFrom() {
        return from;
    }

    /**
     * Returns the coordinate for the move's target Field
     * @return Coordinate
     */
    public Coordinate getTo() {
        return to;
    }
}