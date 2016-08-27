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
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.javainterface.serialize.Deserializer;

/**
 * io.riddles.catchfrauds.game.move.LightridersMoveDeserializer - Created on 8-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersMoveDeserializer implements Deserializer<LightridersMove> {

    private LightridersPlayer player;

    public LightridersMoveDeserializer(LightridersPlayer player) {
        this.player = player;
    }

    @Override
    public LightridersMove traverse(String string) {
        try {
            return visitMove(string);
        } catch (InvalidInputException ex) {
            return new LightridersMove(this.player, ex);
        } catch (Exception ex) {
            return new LightridersMove(this.player, new InvalidInputException("Failed to parse move"));
        }
    }

    private LightridersMove visitMove(String input) throws InvalidInputException {
        String[] split = input.split(" ");

        switch (split[0]) {
            case "pass":
                return new LightridersMove(this.player, MoveType.PASS);
            case "move":
                MoveType type = visitAssessment(split[1]);
                return new LightridersMove(this.player, type);
        }
        throw new InvalidInputException("Failed to parse move");
        //return new LightridersMove(this.player, MoveType.PASS);
    }

    public MoveType visitAssessment(String input) throws InvalidInputException {
        switch (input) {
            case "up":
                return MoveType.UP;
            case "down":
                return MoveType.DOWN;
            case "left":
                return MoveType.LEFT;
            case "right":
                return MoveType.RIGHT;
            case "pass":
                return MoveType.PASS;
            default:
                throw new InvalidInputException("Move isn't valid");
        }
    }
}
