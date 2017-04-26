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

package io.riddles.lightriders.engine;

import io.riddles.javainterface.configuration.Configuration;
import io.riddles.javainterface.engine.GameLoopInterface;
import io.riddles.javainterface.engine.SimpleGameLoop;
import io.riddles.javainterface.exception.TerminalException;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.io.IOHandler;
import io.riddles.lightriders.game.board.LightridersBoard;

import io.riddles.lightriders.game.move.MoveType;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.lightriders.game.LightridersSerializer;

import java.awt.*;
import java.util.ArrayList;

/**
 * io.riddles.lightriders.engine.LightridersEngine
 *
 * - Creates a Processor, the Players and an initial State
 * - Parses the setup input
 * - Sends settings to the players
 * - Runs a game
 * - Returns the played game at the end of the game
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    public LightridersEngine(PlayerProvider<LightridersPlayer> playerProvider, IOHandler ioHandler) throws TerminalException {
        super(playerProvider, ioHandler);
    }

    @Override
    protected Configuration getDefaultConfiguration() {
        Configuration config = new Configuration();

        config.put("maxRounds", -1);
        config.put("fieldWidth", 16);
        config.put("fieldHeight", 16);

        return config;
    }

    @Override
    protected LightridersProcessor createProcessor() {
        return new LightridersProcessor(this.playerProvider);
    }

    @Override
    protected GameLoopInterface createGameLoop() {
        return new SimpleGameLoop();
    }

    @Override
    protected LightridersPlayer createPlayer(int id) {
        return new LightridersPlayer(id);
    }

    @Override
    protected void sendSettingsToPlayer(LightridersPlayer player) {
        player.sendSetting("your_botid", player.getId());
        player.sendSetting("field_width", configuration.getInt("fieldWidth"));
        player.sendSetting("field_height", configuration.getInt("fieldHeight"));

        int maxRounds = configuration.getInt("maxRounds");
        if (maxRounds > 0) {
            player.sendSetting("max_rounds", maxRounds);
        }
    }

    @Override
    protected String getPlayedGame(LightridersState initialState) {
        LightridersSerializer serializer = new LightridersSerializer();
        return serializer.traverseToString(this.processor, initialState);
    }


    @Override
    protected LightridersState getInitialState() {
        int width = configuration.getInt("fieldWidth");
        int height = configuration.getInt("fieldHeight");

        LightridersBoard board = new LightridersBoard(width, height);

        // Create initial player states
        ArrayList<LightridersPlayerState> playerStates = new ArrayList<>();
        for (LightridersPlayer player : this.playerProvider.getPlayers()) {
            LightridersPlayerState playerState = new LightridersPlayerState(player.getId());
            playerStates.add(playerState);
        }

        // Create initial state
        LightridersState state = new LightridersState(playerStates, board);

        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            Point startCoordinate = getStartCoordinate(playerState.getPlayerId(), width, height);

            state.setPlayerCoordinate(playerState.getPlayerId(), startCoordinate);
            playerState.setDirection(getStartDirection(startCoordinate, width));
        }

        return state;
    }

    private Point getStartCoordinate(int playerNr, int width, int height) {
        if (this.playerProvider.getPlayers().size() == 2) {
            switch (playerNr) {
                case 0:
                    return new Point((int) Math.floor(width / 4) - 1, (height / 2) - 1);
                case 1:
                    return new Point((int) Math.floor(width / 4) * 3, (height / 2) - 1);
            }
        }

        switch (playerNr) {
            case 0:
                return new Point((int) Math.floor(width / 4), height / 4);
            case 1:
                return new Point((int) Math.floor(width / 4) * 3, height / 4);
            case 2:
                return new Point((int) Math.floor(width / 4), height / 4 * 3);
            case 3:
                return new Point((int) Math.floor(width / 4) * 3, height / 4 * 3);
        }

        throw new RuntimeException("Can't run engine with more than 4 players");
    }

    private MoveType getStartDirection(Point coordinate, int boardWidth) {
        if (coordinate.x < boardWidth / 2) {
            return MoveType.RIGHT;
        }

        return MoveType.LEFT;
    }
}
