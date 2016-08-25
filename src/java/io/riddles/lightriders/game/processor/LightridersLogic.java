package io.riddles.lightriders.game.processor;

import io.riddles.lightriders.engine.LightridersEngine;
import io.riddles.lightriders.game.data.*;
import io.riddles.lightriders.game.player.LightridersPlayer;

import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.exception.InvalidInputException;

import java.util.ArrayList;

/**
 * Created by joost on 3-7-16.
 */
public class LightridersLogic {


    public LightridersLogic() {
    }

    public void transform(LightridersState state, LightridersPlayer player, LightridersMove move) {

        int pId = player.getId();
        LightridersBoard board = state.getBoard();

        switch(move.getMoveType()) {
            case UP:
                player.setDirection(move.getMoveType());
                break;
            case DOWN:
                player.setDirection(move.getMoveType());
                break;
            case RIGHT:
                player.setDirection(move.getMoveType());
                break;
            case LEFT:
                player.setDirection(move.getMoveType());
                break;
        }

        Coordinate c = player.getCoordinate();
        Coordinate newC = c;

        switch(player.getDirection()) {
            case UP:
                if (c.getY() > 0) {
                    if (board.isEmpty(new Coordinate(c.getX(), c.getY() - 1))) {
                        newC = new Coordinate(c.getX(), c.getY() - 1);
                    }
                } else {
                    player.kill();
                }
                break;
            case DOWN:
                if (c.getY() < board.getHeight()-1) {
                    if (board.isEmpty(new Coordinate(c.getX(), c.getY() + 1))) {
                        newC = new Coordinate(c.getX(), c.getY() + 1);
                    }
                } else {
                    player.kill();
                }
                break;
            case RIGHT:
                if (c.getX() < board.getWidth()-1) {
                    if (board.isEmpty(new Coordinate(c.getX()+1, c.getY()))) {
                        newC = new Coordinate(c.getX() + 1, c.getY());
                    }
                } else {
                    player.kill();
                }
                break;
            case LEFT:
                if (c.getX() > 0) {
                    if (board.isEmpty(new Coordinate(c.getX() - 1, c.getY()))) {
                        newC = new Coordinate(c.getX() - 1, c.getY());
                    }
                } else {
                    player.kill();
                }
                break;
        }

        if (board.isEmpty(newC)) {
            board.setFieldAt(newC, player.getColor().toString().substring(0,1));
            player.setCoordinate(newC);
        } else {
            player.kill();
        }
    }
}
