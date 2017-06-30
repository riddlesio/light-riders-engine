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

import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.javainterface.game.state.AbstractStateSerializer;

import java.awt.*;

/**
 * io.riddles.lightriders.LightridersStateSerializer
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersStateSerializer extends AbstractStateSerializer<LightridersState> {

    @Override
    public String traverseToString(LightridersState state) {
        return visitState(state).toString();
    }

    @Override
    public JSONObject traverseToJson(LightridersState state) {
        return visitState(state);
    }

    private JSONObject visitState(LightridersState state) {
        JSONObject stateJson = new JSONObject();
        stateJson.put("round", state.getRoundNumber());

        JSONArray players = new JSONArray();
        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            JSONObject playerObj = new JSONObject();
            playerObj.put("id", playerState.getPlayerId());
            playerObj.put("position", visitPoint(playerState.getCoordinate()));
            playerObj.put("isCrashed", !playerState.isAlive());

            if (playerState.getMove() != null && playerState.getMove().getException() != null) {
                playerObj.put("error", playerState.getMove().getException().getMessage());
            } else {
                playerObj.put("error", JSONObject.NULL);
            }

            players.put(playerObj);
        }

        stateJson.put("players", players);
        return stateJson;
    }

    private JSONObject visitPoint(Point point) {
        JSONObject pointObj = new JSONObject();

        pointObj.put("x", point.x);
        pointObj.put("y", point.y);

        return pointObj;
    }
}
