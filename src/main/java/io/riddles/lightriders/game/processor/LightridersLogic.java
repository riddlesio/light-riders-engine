package io.riddles.lightriders.game.processor;

import io.riddles.javainterface.game.data.Point;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;
/**
 * io.riddles.lightriders.game.processor.LightridersLogic - Created on 6/27/16
 *
 * Provides logic to transform a LightridersState with a LightridersMove.
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersLogic {


    public LightridersLogic() {
    }

    /**
     * Takes a LightridersState and transforms it with a LightridersMove.
     *
     * Return
     * Returns nothing, but transforms the given LightridersState.
     * @param LightridersState The initial state
     * @param LightridersPlayerState The playerState involved
     * @return
     */
    public void transform(LightridersState state, LightridersPlayerState playerState) {
        LightridersMove move = playerState.getMove();

        int pId = playerState.getPlayerId();

        LightridersBoard board = state.getBoard();

        if (move.getMoveType() != null) { /* MoveType = null when it has an Exception */
            switch (move.getMoveType()) {
                case UP:
                    playerState.setDirection(move.getMoveType());
                    break;
                case DOWN:
                    playerState.setDirection(move.getMoveType());
                    break;
                case RIGHT:
                    playerState.setDirection(move.getMoveType());
                    break;
                case LEFT:
                    playerState.setDirection(move.getMoveType());
                    break;
            }
        }

        Point c = playerState.getCoordinate();
        Point newC = c;

        switch(playerState.getDirection()) {
            case UP:
                if (c.getY() > 0) {
                    if (board.isEmpty(new Point(c.x, c.y - 1))) {
                        newC = new Point(c.x, c.y - 1);
                    }
                } else {
                    playerState.kill();
                }
                break;
            case DOWN:
                if (c.getY() < board.getHeight()-1) {
                    if (board.isEmpty(new Point(c.x, c.y + 1))) {
                        newC = new Point(c.x, c.y + 1);
                    }
                } else {
                    playerState.kill();
                }
                break;
            case RIGHT:
                if (c.getX() < board.getWidth()-1) {
                    if (board.isEmpty(new Point(c.x+1, c.y))) {
                        newC = new Point(c.x + 1, c.y);
                    }
                } else {
                    playerState.kill();
                }
                break;
            case LEFT:
                if (c.getX() > 0) {
                    if (board.isEmpty(new Point(c.x - 1, c.y))) {
                        newC = new Point(c.x - 1, c.y);
                    }
                } else {
                    playerState.kill();
                }
                break;
        }

        if (board.isEmpty(newC)) {
            board.setFieldAt(newC, String.valueOf(playerState.getPlayerId()));
            playerState.setCoordinate(newC);
        } else {
            playerState.kill();
        }
    }
}
