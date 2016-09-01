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

    private ArrayList<Record> records;
    private int roundNumber;
    private boolean gameOver;

    public LightridersProcessor(ArrayList<LightridersPlayer> players) {
        super(players);
        this.gameOver = false;
    }

    @Override
    public void preGamePhase() {

    }

    @Override
    public LightridersState playRound(int roundNumber, LightridersState state) {
        this.roundNumber = roundNumber;
        LOGGER.info(String.format("Playing round %d", roundNumber));

        ArrayList<LightridersMove> moves = new ArrayList();
        LightridersBoard board = state.getBoard();
        LightridersBoard newBoard = new LightridersBoard(
                board.getWidth(),
                board.getHeight(),
                board.toString());

        LightridersState nextState = new LightridersState(state, moves, roundNumber);
        nextState.setBoard(newBoard);

        for (LightridersPlayer player : this.players) {

            if (player.isAlive()) {
                player.sendUpdate("field", player, newBoard.toString()); /* TODO: Should use playerId's instead of Color letter */
                String response = player.requestMove(ActionType.MOVE.toString());

                // parse the response
                LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer(player);
                LightridersMove move = deserializer.traverse(response);

                // create the next state
                moves.add(move);

                LightridersLogic l = new LightridersLogic();

                try {
                    l.transform(nextState, player, move);
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

        newBoard.dump(this.players, nextState);

        return nextState;
    }


    @Override
    public boolean hasGameEnded(LightridersState state) {
        boolean returnVal = false;

        int alivePlayers = 0;
        for (LightridersPlayer player : this.players)
            if (player.isAlive()) alivePlayers++;

        if (alivePlayers < 2) returnVal = true;
        if (this.gameOver) returnVal = true;
        if (this.roundNumber >= LightridersEngine.configuration.getInt("maxRounds")) returnVal = true;
        return returnVal;
    }

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

    @Override
    public double getScore() {
        return 0;
    }



}
