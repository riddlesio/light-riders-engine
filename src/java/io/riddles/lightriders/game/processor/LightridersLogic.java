package io.riddles.lightriders.game.processor;

import io.riddles.javainterface.exception.InvalidMoveException;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.move.MoveType;
import io.riddles.lightriders.game.player.LightridersPlayer;

import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.state.LightridersState;

import java.awt.*;

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
     * @param state The initial state
     * @param player The player involved
     * @param move The move of the player
     * @return
     */
    public LightridersMove transform(LightridersState state, LightridersPlayer player, LightridersMove move) {

        LightridersBoard board = state.getBoard();
        MoveType moveType = move.getMoveType();

        if (moveType != null && moveType != MoveType.PASS) { /* MoveType = null when it has an Exception */
            if (state.getRoundNumber() > 1 && moveType.getOpposite() == player.getDirection()) { // Can't do opposite move
                String warning = "Can't move opposite of current direction.";
                player.sendWarning(warning);
                move = new LightridersMove(player, new InvalidMoveException(warning));
                moveType = MoveType.PASS;
            }

            switch (moveType) {
                case UP:
                    player.setDirection(moveType);
                    break;
                case DOWN:
                    player.setDirection(moveType);
                    break;
                case RIGHT:
                    player.setDirection(moveType);
                    break;
                case LEFT:
                    player.setDirection(moveType);
                    break;
            }
        }

        Point c = player.getCoordinate();
        Point newC = c;

        switch (player.getDirection()) {
            case UP:
                newC = new Point(c.x, c.y - 1);
                break;
            case DOWN:
                newC = new Point(c.x, c.y + 1);
                break;
            case RIGHT:
                newC = new Point(c.x + 1, c.y);
                break;
            case LEFT:
                newC = new Point(c.x - 1, c.y);
                break;
        }

        player.setCoordinate(newC);

        if (board.isEmpty(newC)) {
            board.setFieldAt(newC, player.getColor().toString().substring(0,1));
        } else {
            player.kill();
        }

        return move;
    }
}
