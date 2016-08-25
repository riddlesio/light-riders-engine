package io.riddles.lightriders;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.AbstractModel;
import io.riddles.game.model.Stateful;
import io.riddles.game.model.Traversible;
import io.riddles.lightriders.LightridersPiece.PieceColor;
import io.riddles.boardgame.model.Move;
import io.riddles.boardgame.model.RectangularBoard;

import java.util.Optional;
/**
 * ${PACKAGE_NAME}
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public final class LightridersState extends AbstractModel implements Stateful<LightridersState>, Traversible {

    private RectangularBoard board;
    private Optional<Exception> exception;
    private Optional<Move> move;
    private int moveNumber;
    private Optional<LightridersState> previousState;
    private PieceColor pieceColor;
    
    
    public LightridersState(RectangularBoard board) {
        this.board = board;
        exception       = Optional.empty();
        move            = Optional.empty();
        moveNumber      = -1;
        previousState   = Optional.empty();
    }

    public LightridersState(LightridersState previousState, Exception exception) {

        this.exception      = Optional.of(exception);
        this.previousState  = Optional.of(previousState);

        board       = new RectangularBoard(previousState.getBoard().getFields(), previousState.getBoard().getWidth(), previousState.getBoard().getHeight());
        move        = Optional.empty();
        moveNumber  = previousState.getMoveNumber() + 1;
    }

    public LightridersState(LightridersState previousState, Board board, Move move) {

        board       = new RectangularBoard(previousState.getBoard().getFields(), previousState.getBoard().getWidth(), previousState.getBoard().getHeight());
        this.previousState  = Optional.of(previousState);
        this.move           = Optional.of(move);

        exception      = Optional.empty();
        moveNumber     = previousState.getMoveNumber() + 1;
    }

    public LightridersState(LightridersState previousState, Move move) {

        board       = new RectangularBoard(previousState.getBoard().getFields(), previousState.getBoard().getWidth(), previousState.getBoard().getHeight());
        this.previousState  = Optional.of(previousState);
        this.move           = Optional.of(move);

        exception      = Optional.empty();
        moveNumber     = previousState.getMoveNumber() + 1;
    }
    
    public LightridersState(LightridersState previousState) {

        this.previousState  = Optional.of(previousState);

        exception      = Optional.empty();
        moveNumber     = previousState.getMoveNumber() + 1;
    }


    public RectangularBoard getBoard() { return board; }

    public Optional<Move> getMove() {
        return move;
    }

    @Override
    public Optional<Exception> getException() {
        return exception;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    @Override
    public Optional<LightridersState> getPreviousState() {
        return previousState;
    }

    public Boolean hasException() {
    	return exception.map(exception -> true).orElse(false);
    }

    public Boolean hasMove() {
        return move.map(move -> true).orElse(false);
    }

    public Boolean hasPreviousState() {
        return previousState.map(previousState -> true).orElse(false);
    }

	public PieceColor getActivePieceColor() {
		return pieceColor;
	}
	
	public void setActivePieceColor(PieceColor pieceColor) {
		this.pieceColor = pieceColor;
	}
}