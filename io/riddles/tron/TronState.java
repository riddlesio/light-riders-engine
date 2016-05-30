package io.riddles.tron;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.AbstractModel;
import io.riddles.game.model.Stateful;
import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.boardgame.model.Move;

import java.util.Optional;

/**
 * ${PACKAGE_NAME}
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public final class TronState extends AbstractModel implements Stateful<TronState>, Traversible {

    private Board board;
    private Optional<Exception> exception;
    private Optional<Move> move;
    private int moveNumber;
    private Optional<TronState> previousState;
    private PieceColor pieceColor;
    
    
    public TronState(Board board) {
        this.board = board;
        exception       = Optional.empty();
        move            = Optional.empty();
        moveNumber      = -1;
        previousState   = Optional.empty();
    }

    public TronState(TronState previousState, Exception exception) {

        this.exception      = Optional.of(exception);
        this.previousState  = Optional.of(previousState);

        board       = previousState.getBoard();
        move        = Optional.empty();
        moveNumber  = previousState.getMoveNumber();
    }

    public TronState(TronState previousState, Board board, Move move) {

        this.board          = board;
        this.previousState  = Optional.of(previousState);
        this.move           = Optional.of(move);

        exception      = Optional.empty();
        moveNumber     = previousState.getMoveNumber() + 1;
    }
    
    public TronState(TronState previousState) {

        this.previousState  = Optional.of(previousState);

        exception      = Optional.empty();
        moveNumber     = previousState.getMoveNumber() + 1;
    }


    public Board getBoard() { return board; }

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
    public Optional<TronState> getPreviousState() {
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