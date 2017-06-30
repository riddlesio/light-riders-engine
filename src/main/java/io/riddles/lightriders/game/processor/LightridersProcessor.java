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

package io.riddles.lightriders.game.processor;

import java.util.ArrayList;

import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.processor.SimpleProcessor;
import io.riddles.lightriders.engine.LightridersEngine;
import io.riddles.lightriders.game.move.*;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;

/**
 * io.riddles.lightriders.game.processor.LightridersProcessor
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersProcessor extends SimpleProcessor<LightridersState, LightridersPlayer> {

    private LightridersMoveDeserializer moveDeserializer;

    public LightridersProcessor(PlayerProvider<LightridersPlayer> playerProvider) {
        super(playerProvider);
        this.moveDeserializer = new LightridersMoveDeserializer();
    }

    @Override
    public boolean hasGameEnded(LightridersState state) {
        int maxRounds = LightridersEngine.configuration.getInt("maxRounds");
        long alivePlayers = state.getPlayerStates().stream()
                .filter(LightridersPlayerState::isAlive)
                .count();

        return alivePlayers <= 1 || (maxRounds > 0 && state.getRoundNumber() >= maxRounds);
    }

    @Override
    public Integer getWinnerId(LightridersState state) {
        ArrayList<Integer> alivePlayerIds = state.getAlivePlayerIds();

        if (alivePlayerIds.size() == 1) {
            return alivePlayerIds.get(0);
        }

        return null;
    }

    @Override
    public double getScore(LightridersState state) {
        return state.getRoundNumber();
    }

    @Override
    public LightridersState createNextState(LightridersState inputState, int roundNumber) {
        LightridersState nextState = inputState.createNextState(roundNumber);

        for (LightridersPlayerState playerState : nextState.getPlayerStates()) {
            LightridersPlayer player = this.getPlayer(playerState.getPlayerId());

            if (!playerState.isAlive()) continue;

            sendUpdatesToPlayer(inputState, player);
            LightridersMove move = getPlayerMove(player);

            playerState.setMove(move);
        }

        LightridersLogic.transform(nextState);

        // Send exceptions to players
        for (LightridersPlayerState playerState : nextState.getPlayerStates()) {
            LightridersPlayer player = this.getPlayer(playerState.getPlayerId());
            LightridersMove move = playerState.getMove();

             if (move != null && move.getException() != null) {
                player.sendWarning(move.getException().getMessage());
            }
        }

        return nextState;
    }

    private void sendUpdatesToPlayer(LightridersState state, LightridersPlayer player) {
        player.sendUpdate("round", state.getRoundNumber());
        player.sendUpdate("field", state.getBoard().toString());
    }

    private LightridersMove getPlayerMove(LightridersPlayer player) {
        String response = player.requestMove(ActionType.MOVE);
        return this.moveDeserializer.traverse(response);
    }

    private LightridersPlayer getPlayer(int id) {
        return this.playerProvider.getPlayerById(id);
    }
}
