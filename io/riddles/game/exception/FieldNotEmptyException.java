package io.riddles.game.exception;

public class FieldNotEmptyException extends InvalidMoveException {
    public FieldNotEmptyException(String message) {
        super(message);
    }
}
