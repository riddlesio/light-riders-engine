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

package io.riddles.lightriders.game.move;

import io.riddles.lightriders.game.data.MoveType;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.javainterface.game.move.AbstractMove;
import io.riddles.lightriders.game.player.LightridersPlayer;

/**
 * io.riddles.lightriders.game.move.LightridersMove - Created on 2-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersMove extends AbstractMove {

    private MoveType type;

    public LightridersMove(MoveType type) {
        super();
        this.type = type;
    }

    public LightridersMove(Exception exception) {
        super(exception);
    }

    public void setMoveType(MoveType type) {
        this.type = type;
    }
    public MoveType getMoveType() {
        return this.type;
    }

    public String toString() {
        return "LightridersMove " + this.type;
    }

}
