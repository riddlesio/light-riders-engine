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

import io.riddles.javainterface.game.processor.PlayerResponseProcessor;
import io.riddles.javainterface.io.PlayerResponse;
import io.riddles.lightriders.engine.LightridersEngine;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.data.*;
import io.riddles.lightriders.game.move.*;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.game.processor.AbstractProcessor;

/**
 * io.riddles.catchfrauds.interface.LightridersProcessor - Created on 2-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersProcessor implements PlayerResponseProcessor<LightridersPlayer, LightridersState> {

    public LightridersProcessor() {
        super();
    }

    /**
     *
     *
     * Return
     * the LightridersState that will be the state for the next round.
     * @param roundNumber The current round number
     * @param LightridersState The current state
     * @return The LightridersState that will be the start of the next round
     */
    @Override
    public LightridersState processInput(LightridersState state, int roundNumber, PlayerResponse input) {

        /* Clone playerStates for next State */
        ArrayList<LightridersPlayerState> nextPlayerStates = clonePlayerStates(state.getPlayerStates());

        LightridersLogic logic = new LightridersLogic();
        LightridersState nextState = state.createNextState(roundNumber);
        nextState.setPlayerId(input.getPlayerId());
        LightridersPlayerState playerState = getActivePlayerState(nextPlayerStates, input.getPlayerId());
        playerState.setPlayerId(input.getPlayerId());


        LightridersBoard nextBoard = nextState.getBoard();

        // parse the response
        LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer();
        LightridersMove move = deserializer.traverse(input.getValue());


        // create the next move

        try {
            logic.transform(nextState, playerState);
        } catch (Exception e) {
            //LOGGER.info(String.format("Unknown response: %s", response));
        }
        return nextState;
    }

    private ArrayList<LightridersPlayerState> clonePlayerStates(ArrayList<LightridersPlayerState> playerStates) {
        ArrayList<LightridersPlayerState> nextPlayerStates = new ArrayList<>();
        for (LightridersPlayerState playerState : playerStates) {
            LightridersPlayerState nextPlayerState = playerState.clone();
            nextPlayerStates.add(nextPlayerState);
        }
        return nextPlayerStates;
    }

    @Override
    public void sendUpdates(LightridersState state, LightridersPlayer player) {
        player.sendUpdate("round", state.getRoundNumber());
        player.sendUpdate("field", state.getBoard().toString()); /* TODO: Need a representationstring? */
    }
    /**
     * The stopping condition for this game.
     * @param LightridersState the state to determine whether the game has ended.
     * @return True if the game is over, false otherwise
     */
    @Override
    public boolean hasGameEnded(LightridersState state) {
        long alivePlayers = this.players.stream().filter(LightridersPlayer::isAlive).count();
        int maxRounds = LightridersEngine.configuration.getInt("maxRounds");

        return alivePlayers < 2 || this.gameOver ||
                (maxRounds >= 0 && this.roundNumber >= maxRounds);
    }

    /**
     * Returns the winner of the game, if there is one.
     * @return null if there is no winner, a LightridersPlayer otherwise
     */
    @Override
    public LightridersPlayer getWinner() {
        int alivePlayers = 0;
        for (LightridersPlayer player : this.players)
            if (player.isAlive()) alivePlayers++;

        if (alivePlayers == 1) {
            /* There is a winner */
            for (LightridersPlayer player : this.players)
                if (player.isAlive()) return player;
        }
        return null;
    }
    /**
     * GetScore isn't used in Lightriders.
     * @return always return 0.
     */
    @Override
    public double getScore() {
        return 0;
    }



}
