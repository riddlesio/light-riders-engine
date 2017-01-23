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

package io.riddles.lightriders.game.state;

import io.riddles.javainterface.game.player.PlayerBound;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.lightriders.game.player.LightridersPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * io.riddles.lightriders.game.state.LightridersState - Created on 2-6-16
 *
 * This State stores Lightriders specific details, such as player coordinates, and the LightridersBoard.
 *
 * @author joost
 */
public class LightridersState extends AbstractState<LightridersPlayerState> implements PlayerBound {

    private LightridersBoard board;
    private int playerId;

    public LightridersState() {
        super();
    }

    /**
     * the LightridersState that will be the state for the this round.
     * @param LightridersState previous state to be stored
     * @param ArrayList<LightridersPlayerState> ArrayList with playerStates for this round
     * @param int roundNumber
     * @return The LightridersState that will be the start of the next round
     */
    public LightridersState(LightridersState previousState, ArrayList<LightridersPlayerState> playerStates, int roundNumber) {
        super(previousState, playerStates, roundNumber);
        this.board = previousState.getBoard().clone();
        this.playerId = previousState.getPlayerId();
    }

    public LightridersState(LightridersState previousState, LightridersPlayerState playerState, int roundNumber) {
        super(previousState, playerState, roundNumber);
        this.board = previousState.getBoard().clone();
        this.playerId = previousState.getPlayerId();
    }




    /**
     * createNextState creates new objects needed for a new state.
     * @param int roundNumber
     * @return New LightridersState based on this state.
     */
    public LightridersState createNextState(int roundNumber) {
        LightridersState nextState = new LightridersState(this, new ArrayList<>(), roundNumber);
        LightridersBoard nextBoard = new LightridersBoard(
                board.getWidth(),
                board.getHeight(),
                board.toString());
        nextState.setBoard(nextBoard);
        return nextState;
    }

    public LightridersBoard getBoard() {
        return this.board;
    }
    public void setBoard(LightridersBoard b) {
        this.board = b;
    }

    /**
     * setPlayerData takes a LightridersPlayer, stores it's coordinate and aliveness
     * @param LightridersPlayer
     */
    public void setPlayerData(LightridersPlayer p) {
        playerAlive.put(p, p.isAlive());
        playerCoordinates.put(p, p.getCoordinate());
    }

    /**
     * getPlayerCoordinate returns player's coordinates.
     * @param LightridersPlayer concerned
     * @return Point
     */
    public Point getPlayerCoordinate(LightridersPlayer p) {
        if (playerCoordinates.get(p) != null) {
            return playerCoordinates.get(p);
        }
        return null;
    }

    /**
     * getPlayerCoordinate returns player's aliveness.
     * @param LightridersPlayer concerned
     * @return boolean
     */
    public boolean isPlayerAlive(LightridersPlayer p) {
        if (playerAlive.get(p) != null) {
            return playerAlive.get(p);
        }
        return false;
    }

    public int getPlayerId() { return this.playerId; }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
