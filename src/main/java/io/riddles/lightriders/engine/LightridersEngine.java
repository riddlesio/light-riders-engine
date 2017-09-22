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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

/**
 * io.riddles.lightriders.engine.LightridersEngine
 *
 * - Creates a Processor, the Players and an initial State
 * - Parses the setup input
 * - Sends settings to the players
 * - Runs a game
 * - Returns the played game at the end of the game
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    public static SecureRandom RANDOM;

    public LightridersEngine(PlayerProvider<LightridersPlayer> playerProvider, IOHandler ioHandler) throws TerminalException {
        super(playerProvider, ioHandler);
    }

    @Override
    protected Configuration getDefaultConfiguration() {
        Configuration config = new Configuration();

        config.put("maxRounds", -1);
        config.put("fieldWidth", 16);
        config.put("fieldHeight", 16);
        config.put("seed", UUID.randomUUID().toString());

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
        setRandomSeed();

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
        ArrayList<Point> startCoordinates = getStartCoordinates(width, height);

        System.err.println(startCoordinates);

        for (LightridersPlayerState playerState : state.getPlayerStates()) {
            Point startCoordinate = startCoordinates.get(playerState.getPlayerId());

            state.setPlayerCoordinate(playerState.getPlayerId(), startCoordinate);
            playerState.setDirection(getStartDirection(startCoordinate, width));
        }

        return state;
    }

    /**
     * Gets a random starting point for the first player, then gets rotational
     * symmetric points for the other players. 2 or 4 players possible.
     * @param width Field width
     * @param height Field height
     * @return Starting coordinates for each player
     */
    private ArrayList<Point> getStartCoordinates(int width, int height) {
        ArrayList<Point> startCoordinates = new ArrayList<>();

        int initialX = RANDOM.nextInt((width / 2) - 2) + 1;
        int initialY;

        switch (this.playerProvider.getPlayers().size()) {
            case 2:
                initialY = RANDOM.nextInt(height - 2) + 1;
                startCoordinates.add(new Point(initialX, initialY));
                startCoordinates.add(new Point((width - 1) - initialX, initialY));
                break;
            case 4:
                initialY = RANDOM.nextInt((height / 2) - 2) + 1;
                startCoordinates.add(new Point(initialX, initialY));
                startCoordinates.add(new Point((width - 1) - initialX, initialY));
                startCoordinates.add(new Point((width - 1) - initialX, (height - 1) - initialY));
                startCoordinates.add(new Point(initialX, (height - 1) - initialY));
                break;
            default:
                throw new RuntimeException("Can only run this game with 2 or 4 players");
        }

        return startCoordinates;
    }

    private MoveType getStartDirection(Point coordinate, int boardWidth) {
        if (coordinate.x < boardWidth / 2) {
            return MoveType.RIGHT;
        }

        return MoveType.LEFT;
    }

    private void setRandomSeed() {
        try {
            RANDOM = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.severe("Not able to use SHA1PRNG, using default algorithm");
            RANDOM = new SecureRandom();
        }
        String seed = configuration.getString("seed");
        LOGGER.info("RANDOM SEED IS: " + seed);
        RANDOM.setSeed(seed.getBytes());
    }
}
