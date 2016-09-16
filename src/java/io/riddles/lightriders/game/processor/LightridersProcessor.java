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

import io.riddles.lightriders.engine.LightridersEngine;
import io.riddles.lightriders.game.board.LightridersBoard;
import io.riddles.lightriders.game.data.*;
import io.riddles.lightriders.game.move.*;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.game.processor.AbstractProcessor;

/**
 * io.riddles.catchfrauds.interface.LightridersProcessor - Created on 2-6-16
 *
 * [description]
 *
 * @author jim
 */
public class LightridersProcessor extends AbstractProcessor<LightridersPlayer, LightridersState> {

    private int roundNumber;
    private boolean gameOver;

    public LightridersProcessor(ArrayList<LightridersPlayer> players) {
        super(players);
        this.gameOver = false;
    }

    @Override
    public void preGamePhase() {

    }

    /**
     * Play one round of the game. It takes a LightridersState,
     * asks all living players for a response and delivers a new LightridersState.
     *
     * Return
     * the LightridersState that will be the state for the next round.
     * @param roundNumber The current round number
     * @param LightridersState The current state
     * @return The LightridersState that will be the start of the next round
     */
    @Override
    public LightridersState playRound(int roundNumber, LightridersState state) {
        LOGGER.info(String.format("Playing round %d", roundNumber));
        this.roundNumber = roundNumber;

        LightridersLogic logic = new LightridersLogic();
        LightridersState nextState = state.createNextState(roundNumber);
        LightridersBoard nextBoard = nextState.getBoard();

        for (LightridersPlayer player : this.players) {

            if (player.isAlive()) {
                player.sendUpdate("round", roundNumber);
                player.sendUpdate("field", nextBoard.toRepresentationString(this.players));
                String response = player.requestMove(ActionType.MOVE.toString());

                // parse the response
                LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer(player);
                LightridersMove move = deserializer.traverse(response);

                // create the next move
                nextState.getMoves().add(move);

                try {
                    logic.transform(nextState, player, move);
                } catch (Exception e) {
                    LOGGER.info(String.format("Unknown response: %s", response));
                }

                // stop game if bot returns nothing
                if (response == null) {
                    this.gameOver = true;
                }
            }
            nextState.setPlayerData(player);
        }

        return nextState;
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
