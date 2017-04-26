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

import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.javainterface.exception.InvalidInputException;
import io.riddles.javainterface.serialize.Deserializer;

/**
 * io.riddles.lightriders.game.move.LightridersMoveDeserializer
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersMoveDeserializer implements Deserializer<LightridersMove> {

    @Override
    public LightridersMove traverse(String string) {
        try {
            return visitMove(string);
        } catch (InvalidInputException ex) {
            return new LightridersMove(ex);
        } catch (Exception ex) {
            return new LightridersMove(new InvalidInputException("Failed to parse move"));
        }
    }

    private LightridersMove visitMove(String input) throws InvalidInputException {
        MoveType type = visitMoveType(input);
        return new LightridersMove(type);
    }

    private MoveType visitMoveType(String input) throws InvalidInputException {
        switch (input.toLowerCase()) {
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
