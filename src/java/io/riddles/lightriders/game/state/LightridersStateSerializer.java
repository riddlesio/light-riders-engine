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

import io.riddles.javainterface.game.data.Point;
import io.riddles.lightriders.game.move.LightridersMove;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.javainterface.game.state.AbstractStateSerializer;

/**
 * io.riddles.catchfrauds.game.state.CatchFraudsStateSerializer - Created on 3-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersStateSerializer extends AbstractStateSerializer<LightridersState> {


    @Override
    public String traverseToString(LightridersState state) {
        return visitState(state).toString();
    }

    @Override
    public JSONObject traverseToJson(LightridersState state) throws NullPointerException {
        return visitState(state);
    }

    private JSONObject visitState(LightridersState state) throws NullPointerException {
        JSONObject stateJson = new JSONObject();
        stateJson.put("round", state.getRoundNumber()-1);

        JSONArray playersJson = new JSONArray();
        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            JSONObject playerJson = new JSONObject();
            LightridersMove playerMove = playerState.getMove();
            boolean hasError = false;
            if (playerMove != null) {
                if (playerMove.getException() != null) {
                    hasError = true;
                    playerJson.put("error", playerMove.getException().getMessage());
                }
            }
            playerJson.put("hasError", hasError);
            Point playerCoordinate = playerState.getCoordinate();
            playerJson.put("position", playerCoordinate.x + "," + playerCoordinate.y);
            playerJson.put("isCrashed", !playerState.isAlive());
            playerJson.put("id", playerState.getPlayerId());
            playersJson.put(playerJson);
        }
        stateJson.put("players", playersJson);
        return stateJson;
    }
}
