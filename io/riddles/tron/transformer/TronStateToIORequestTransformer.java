package io.riddles.tron.transformer;

import io.riddles.boardgame.model.*;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IORequestType;
import io.riddles.game.transformer.Transformer;
import io.riddles.tron.TronPiece;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronState;
import io.riddles.tron.io.TronIORequest;
import io.riddles.tron.io.TronIORequestType;

import java.util.Optional;

/**
 * Transforms a ChessState into an IORequest
 */
public class TronStateToIORequestTransformer implements Transformer<TronState, IORequest> {

   // private SquareBoardLogic boardLogic;
   // private ChessPieceLogic pieceLogic;

    public TronStateToIORequestTransformer() {
       // boardLogic = new SquareBoardLogic();
       // pieceLogic = new ChessPieceLogic();
    }

    /**
     * @inheritDoc
     */
    @Override
    public IORequest transform(TronState state) {

        Optional<Move> optionalPreviousMove = state.getMove();

        // Handle game start
        if (!optionalPreviousMove.isPresent()) {
            return createInitialMoveRequest();
        }

        // Everything else is a simple move request
        return createMoveRequest(state);
    }

    /**
     * Creates an IORequest for the next player to move a piece,
     * based on the passed state and move
     * @param state The state upon which to base the next move
     * @return IORequest for the next player to move a piece
     */
    protected IORequest createMoveRequest(TronState state) {

        PieceColor colorToMove = PieceColor.YELLOW;;
        TronPiece movedPiece = getMovedPiece(state);

        /*
        if (movedPiece.hasColor(PieceColor.BLACK)) {
            colorToMove = PieceColor.YELLOW;
        } else {
            colorToMove = PieceColor.GREEN;
        }
        */

        return new TronIORequest(colorToMove, TronIORequestType.MOVE);
    }

    /**
     * Creates an IORequest for WHITE to move a piece (ie. first move of the game)
     * @return IORequest for WHITE to move a piece
     */
    protected IORequest createInitialMoveRequest() {

        return new TronIORequest(PieceColor.YELLOW, TronIORequestType.MOVE);
    }


    /**
     * Returns the PieceColor of the last moved piece
     * @param state The state from which to retrieve the last moved piece
     * @return ChessPieceColor of the last moved piece
     */
    protected PieceColor getColorOfMovedPiece(TronState state) {

        Piece piece = getMovedPiece(state);
        return (PieceColor) piece.getColor();
    }

    /**
     * Get the piece which was moved in the previous IOResponse
     * @param state The state from which to retrieve the last moved piece
     * @return The last moved piece
     */
    protected TronPiece getMovedPiece(TronState state) {

        Move move = getLastMove(state);
        Coordinate coordinate = move.getTo();

        Optional<Piece> optionalPiece = state
                .getBoard()
                .getFieldAt(coordinate)
                .getPiece();

        if(!optionalPiece.isPresent()) {
            throw new RuntimeException("No piece present at target coordinate");
        }

        return (TronPiece) optionalPiece.get();
    }

    /**
     * Gets the last executed move
     * @param state The state from which to retrieve the last move
     * @return The last executed move
     */
    protected Move getLastMove(TronState state) {

        Optional<Move> optionalPreviousMove = state.getMove();

        if(!optionalPreviousMove.isPresent()) {
            throw new RuntimeException("No move present in given state");
        }

        return optionalPreviousMove.get();
    }
}