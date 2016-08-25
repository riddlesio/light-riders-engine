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

import io.riddles.lightriders.game.data.LightridersBoard;
import io.riddles.lightriders.game.data.Coordinate;
import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.data.Enemy;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.javainterface.game.state.AbstractState;

import java.util.ArrayList;

/**
 * io.riddles.lightriders.game.state.LightridersState - Created on 2-6-16
 *
 * [description]
 *
 * @author joost
 */
public class LightridersState extends AbstractState<LightridersMove> {

    private LightridersBoard board;
    private String representationString;


    public LightridersState() {
        super();
    }

    public LightridersState(LightridersState previousState, LightridersMove move, int roundNumber) {
        super(previousState, move, roundNumber);
    }

    public LightridersState(LightridersState previousState, ArrayList<LightridersMove> moves, int roundNumber) {
        super(previousState, moves, roundNumber);
    }

    public LightridersBoard getBoard() {
        return this.board;
    }
    public void setBoard(LightridersBoard b) {
        this.board = b;
    }


    public void setRepresentationString(String s) {
        this.representationString = s;
    }

    public String getRepresentationString() {
        return this.representationString;
    }

}
