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
import io.riddles.javainterface.game.state.AbstractState;

import java.awt.*;
import java.util.ArrayList;

/**
 * io.riddles.lightriders.game.state.LightridersState
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersState extends AbstractState<LightridersPlayerState> {

    private LightridersBoard board;
//    private HashMap<LightridersPlayer, Boolean> playerAlive;
//    private HashMap<LightridersPlayer, Point> playerCoordinates;

    // For initial state only
    public LightridersState(ArrayList<LightridersPlayerState> playerStates, LightridersBoard board) {
        super(null, playerStates, 0);
        this.board = board;
    }

    public LightridersState(LightridersState previousState, ArrayList<LightridersPlayerState> playerStates, int roundNumber) {
        super(previousState, playerStates, roundNumber);
        this.board = new LightridersBoard(previousState.getBoard());
    }

    public LightridersState createNextState(int roundNumber) {
        ArrayList<LightridersPlayerState> playerStates = new ArrayList<>();
        for (LightridersPlayerState playerState : this.getPlayerStates()) {
            playerStates.add(new LightridersPlayerState(playerState));
        }

        return new LightridersState(this, playerStates, roundNumber);
    }

    public void setPlayerCoordinate(int playerId, Point coordinate) {
        this.board.setFieldAt(coordinate, playerId + "");
        this.getPlayerStates().get(playerId).setCoordinate(coordinate);
    }

    public ArrayList<Integer> getAlivePlayerIds() {
        ArrayList<Integer> alivePlayerIds = new ArrayList<>();

        for (LightridersPlayerState playerState : this.getPlayerStates()) {
            if (playerState.isAlive()) {
                alivePlayerIds.add(playerState.getPlayerId());
            }
        }

        return alivePlayerIds;
    }

    public LightridersBoard getBoard() {
        return this.board;
    }
}
