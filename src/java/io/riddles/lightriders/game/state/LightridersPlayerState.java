/*
 *  Copyright 2016 riddles.io (developers@riddles.io)
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 *      For the full copyright and license information, please view the LICENSE
 *      file that was distributed with this source code.
 */

package io.riddles.lightriders.game.state;

import java.awt.*;

import io.riddles.javainterface.game.state.AbstractPlayerState;
import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.move.MoveType;

/**
 * io.riddles.lightriders.game.state.LightridersPlayerState
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersPlayerState extends AbstractPlayerState<LightridersMove> {

    private Point coordinate;
    private boolean isAlive;
    private MoveType direction;

    public LightridersPlayerState(int playerId) {
        super(playerId);
        this.coordinate = new Point(0, 0);
        this.isAlive = true;
        this.direction = null;
    }

    public LightridersPlayerState(LightridersPlayerState playerState) {
        super(playerState.getPlayerId());
        this.coordinate = new Point(playerState.coordinate);
        this.isAlive = playerState.isAlive;
        this.direction = playerState.direction;
    }

    public Point getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public MoveType getDirection() {
        return this.direction;
    }

    public void setDirection(MoveType direction) {
        this.direction = direction;
    }

    public void kill() {
        this.isAlive = false;
    }
}
