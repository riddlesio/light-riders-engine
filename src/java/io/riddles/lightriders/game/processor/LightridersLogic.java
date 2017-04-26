/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.lightriders.game.processor;

import io.riddles.javainterface.exception.InvalidMoveException;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.move.MoveType;

import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;

import java.awt.*;

/**
 * io.riddles.lightriders.game.processor.LightridersLogic
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersLogic {

    /**
     * Takes a LightridersState and transforms it with a LightridersMove.
     * @param state The initial state
     */
    public static void transform(LightridersState state) {
        LightridersBoard board = state.getBoard();

        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            if (!playerState.isAlive()) continue;

            board.setBlocked(playerState.getCoordinate());

            Point newCoordinate = getNewPlayerCoordinate(playerState, state.getRoundNumber());
            playerState.setCoordinate(newCoordinate);
        }

        setPlayersOnBoard(state);
    }

    private static Point getNewPlayerCoordinate(LightridersPlayerState playerState, int roundNumber) {
        LightridersMove move = playerState.getMove();
        MoveType moveType = move.getMoveType();

        if (moveType != null && moveType != MoveType.PASS) {
            if (roundNumber > 1 && moveType.getOpposite() == playerState.getDirection()) {
                move.setException(new InvalidMoveException(
                        "Can't move opposite of current direction"));
            } else {
                playerState.setDirection(moveType);
            }
        }

        Point playerCoordinate = playerState.getCoordinate();
        Point direction = playerState.getDirection().getDirection();

        return new Point(playerCoordinate.x + direction.x, playerCoordinate.y + direction.y);
    }

    /**
     * Stores the player on the board and kills the player if
     * it crashed
     */
    private static void setPlayersOnBoard(LightridersState state) {
        LightridersBoard board = state.getBoard();

        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            if (!playerState.isAlive()) continue;

            Point coordinate = playerState.getCoordinate();
            long count = state.getPlayerStates().stream()
                    .filter(otherPlayerState ->
                            otherPlayerState.getCoordinate().equals(coordinate))
                    .count();

            // player is on same coordinate as other player or non-empty/outside field
            if (count > 1 || !board.isEmpty(coordinate)) {
                playerState.kill();

                if (count > 1) {
                    board.setBlocked(coordinate);
                }
            } else {
                board.setFieldAt(coordinate, playerState.getPlayerId() + "");
            }
        }
    }
}
