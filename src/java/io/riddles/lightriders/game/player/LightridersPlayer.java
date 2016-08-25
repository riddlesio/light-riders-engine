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

package io.riddles.lightriders.game.player;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.data.Coordinate;
import io.riddles.lightriders.game.data.MoveType;

/**
 * io.riddles.catchfrauds.game.player.BookingGameMovePlayer - Created on 3-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersPlayer extends AbstractPlayer {

    private Coordinate c;
    private boolean alive;
    private Color color;
    private MoveType direction;

    public LightridersPlayer(int id) {
        super(id);
        this.alive = true;
        this.c = new Coordinate (0,0);
    }

    public Coordinate getCoordinate() { return this.c; }

    public void setCoordinate(Coordinate c) {
        this.c = c;
    }

    public Color getColor() { return this.color; }

    public void setColor(Color color) {
        this.color = color;
    }

    public MoveType getDirection() { return this.direction; }

    public void setDirection(MoveType direction) {
        this.direction = direction;
    }

    public String toString() {
        return "Player " + this.getId() + " " + this.color + " coord " + this.getCoordinate() + " alive " + this.alive;
    }

    public boolean isAlive() { return this.alive; }
    public void kill() { this.alive = false; }
}
