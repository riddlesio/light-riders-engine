package io.riddles.lightriders.engine;

import io.riddles.javainterface.configuration.CheckedConfiguration;
import io.riddles.javainterface.configuration.Configuration;
import io.riddles.javainterface.engine.GameLoop;
import io.riddles.javainterface.engine.TurnBasedGameLoop;
import io.riddles.javainterface.exception.TerminalException;
import io.riddles.javainterface.game.data.Point;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.io.BotIO;
import io.riddles.javainterface.io.IO;
import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.board.LightridersBoard;

import io.riddles.lightriders.game.data.MoveType;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import io.riddles.lightriders.game.state.LightridersPlayerState;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.lightriders.game.LightridersSerializer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by joost on 6/27/16.
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    protected int nrPlayers = 0;

    public LightridersEngine(PlayerProvider<LightridersPlayer> playerProvider, IO ioHandler) throws TerminalException {
        super(playerProvider, ioHandler);
    }

    @Override
    protected CheckedConfiguration getConfiguration() {
        CheckedConfiguration cc = new CheckedConfiguration();
        cc.addRequiredKey("maxRounds");
        cc.addRequiredKey("fieldWidth");
        cc.addRequiredKey("fieldHeight");

        cc.put("maxRounds", 10);
        cc.put("fieldWidth", 19);
        cc.put("fieldHeight", 19);

        return cc;
    }

    @Override
    protected LightridersPlayer createPlayer(int id, BotIO ioHandler) {
        LightridersPlayer player = new LightridersPlayer(id);
        player.setIoHandler(ioHandler);
        return player;
    }

    @Override
    protected LightridersProcessor createProcessor() {

        return new LightridersProcessor();
    }

    @Override
    protected GameLoop createGameLoop() {

        return new TurnBasedGameLoop(this.playerProvider);
    }

    @Override
    protected void sendSettingsToPlayer(LightridersPlayer player, Configuration configuration) {
        player.sendSetting("your_botid", player.getId());
        player.sendSetting("field_width", configuration.getInt("fieldWidth"));
        player.sendSetting("field_height", configuration.getInt("fieldHeight"));
        player.sendSetting("max_rounds", configuration.getInt("maxRounds"));
    }

    @Override
    protected String getPlayedGame(LightridersState initialState) {
        LightridersSerializer serializer = new LightridersSerializer(this.playerProvider);
        return serializer.traverseToString(this.processor, initialState);
    }


    @Override
    protected LightridersState getInitialState(Configuration configuration) {
        LightridersState s = new LightridersState();




        int fieldWidth = configuration.getInt("fieldWidth");
        int fieldHeight = configuration.getInt("fieldHeight");

        LightridersBoard board = new LightridersBoard(fieldWidth, fieldHeight);
        board.clear();

        ArrayList<LightridersPlayerState> playerStates = new ArrayList<>();
        int counter = 0;

        for (LightridersPlayer player : this.playerProvider.getPlayers()) {
            LightridersPlayerState playerState = new LightridersPlayerState();
            playerState.setPlayerId(player.getId());
            playerState.setColor(Color.values()[counter]); /* This limits the game to four players */

            playerState.setCoordinate(getStartCoordinate(counter, 4, fieldWidth, fieldHeight));

            playerState.setDirection(MoveType.LEFT);
            if (playerState.getCoordinate().getX() < fieldWidth/2) playerState.setDirection(MoveType.RIGHT);

            playerStates.add(playerState);
            board.setFieldAt(playerState.getCoordinate(), String.valueOf(playerState.getPlayerId()));

            counter++;
        }

        s.setBoard(board);
        return s;
    }


    protected Point getStartCoordinate(int playerNr, int nrPlayers, int width, int height) {

        if (this.nrPlayers == 2) {
            switch (playerNr) {
                case 0:
                    return new Point((int) Math.floor(width / 4), height / 2);
                case 1:
                    return new Point((int) Math.floor(width / 4) * 3, height / 2);
            }
        } else {
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
        }
        Random r = new Random();
        return new Point(r.nextInt(width), r.nextInt(height));
    }
}
