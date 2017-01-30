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

package io.riddles.lightriders.game;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import io.riddles.lightriders.game.state.LightridersState;
import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.lightriders.game.state.LightridersStateSerializer;
import io.riddles.javainterface.game.AbstractGameSerializer;

import java.util.ArrayList;

/**
 * io.riddles.catchfrauds.game.GameSerializer - Created on 8-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersSerializer extends
        AbstractGameSerializer<LightridersProcessor, LightridersState> {


    public LightridersSerializer(PlayerProvider playerProvider) {
        super(playerProvider);
    }

    @Override
    public String traverseToString(LightridersProcessor processor, LightridersState initialState) {
        JSONObject game = new JSONObject();

        game = addDefaultJSON(initialState, game, processor);

        // add all states
        JSONArray states = new JSONArray();
        LightridersState state = initialState;
        LightridersStateSerializer stateSerializer = new LightridersStateSerializer();
        while (state.hasNextState()) {
            state = (LightridersState) state.getNextState();
            states.put(stateSerializer.traverseToJson(state));
        }
        game.put("states", states);

        return game.toString();
    }

}
