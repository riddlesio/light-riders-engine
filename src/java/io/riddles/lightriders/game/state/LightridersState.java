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
 * [description]
 *
 * @author joost
 */
public class LightridersState extends AbstractState<LightridersMove> {

    private LightridersBoard board;
    private HashMap<LightridersPlayer, Boolean> playerAlive;
    private HashMap<LightridersPlayer, Point> playerCoordinates;

    public LightridersState() {
        super();
    }

    public LightridersState(LightridersState previousState, LightridersMove move, int roundNumber) {
        super(previousState, move, roundNumber);
        playerAlive = new HashMap<LightridersPlayer, Boolean>();
        playerCoordinates = new HashMap<LightridersPlayer, Point> ();

    }

    public LightridersState(LightridersState previousState, ArrayList<LightridersMove> moves, int roundNumber) {
        super(previousState, moves, roundNumber);
        playerAlive = new HashMap<LightridersPlayer, Boolean>();
        playerCoordinates = new HashMap<LightridersPlayer, Point> ();
    }

    public LightridersBoard getBoard() {
        return this.board;
    }
    public void setBoard(LightridersBoard b) {
        this.board = b;
    }

    public boolean isPlayerAlive(LightridersPlayer p) {
        if (playerAlive.get(p) != null) {
            return playerAlive.get(p);
        }
        return false;
    }

    public void setPlayerData(LightridersPlayer p) {
        playerAlive.put(p, p.isAlive());
        playerCoordinates.put(p, p.getCoordinate());
    }


    public Point getPlayerCoordinate(LightridersPlayer p) {
        if (playerCoordinates.get(p) != null) {
            return playerCoordinates.get(p);
        }
        return null;
    }

}
