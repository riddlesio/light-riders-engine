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

package io.riddles.lightriders.game.data;

import java.awt.*;

/**
 * io.riddles.catchfrauds.game.move.Enemy - Created on 3-6-16
 *
 * [description]
 *
 * @author jim
 */
public class Enemy {


    private Point coordinate;
    private Point prevCoordinate;
    private MoveType direction;
    private boolean killMe = false;

    public Enemy(Point c, MoveType d) {
        this.coordinate = c;
        this.direction = d;
    }

    public Point getCoordinate() { return this.coordinate; }
    public Point getPreviousCoordinate() { return this.prevCoordinate; }

    public void setCoordinate(Point c) {
        if (prevCoordinate != null) {
            if (!c.equals(prevCoordinate)) {
                prevCoordinate = coordinate;
            }
        } else {
            prevCoordinate = coordinate;
        }
        this.coordinate = c;
        System.out.println("setCoordinate " + prevCoordinate + " " + coordinate);

    }


    public MoveType getDirection() { return this.direction; }
    public void setDirection(MoveType d) { this.direction = d; }

    public String toString() {
        return "Enemy " + this.coordinate;
    }

    public void die() {this.killMe = true;}
    public boolean isDead() { return this.killMe; }
}
