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

import io.riddles.lightriders.game.move.LightridersMove;
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
        stateJson.put("move", state.getRoundNumber()-1);

        LightridersMove move = (LightridersMove) state.getMoves().get(0);
        /* TODO: What if there's multiple moves in a state? */

        if (move.getException() == null) {
            //stateJson.put("movetype", move.getMoveType());
            stateJson.put("exception", JSONObject.NULL);
            stateJson.put("field", state.getRepresentationString());
        } else {
            //stateJson.put("movetype", move.getMoveType());
            stateJson.put("exception", move.getException().getMessage());
            stateJson.put("field", state.getRepresentationString());
        }

        return stateJson;
    }
}
