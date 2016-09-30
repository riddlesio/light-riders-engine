package io.riddles.lightriders.engine;

import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.board.LightridersBoard;

import io.riddles.lightriders.game.move.MoveType;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.lightriders.game.LightridersSerializer;

import java.awt.*;
import java.util.Random;

/**
 * Created by joost on 6/27/16.
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    public LightridersEngine(String args[]) throws Exception {
        super(args);
        setDefaults();
    }

    public LightridersEngine(String wrapperFile, String[] botFiles) {
        super(wrapperFile, botFiles);
        setDefaults();
    }
    private void setDefaults() {
        configuration.put("maxRounds", -1);
        configuration.put("fieldWidth", 16);
        configuration.put("fieldHeight", 16);
    }


    @Override
    protected LightridersPlayer createPlayer(int id) {
        LightridersPlayer player = new LightridersPlayer(id);
        player.setColor(Color.values()[id]); /* This limits the game to four players */
        return player;
    }

    @Override
    protected LightridersProcessor createProcessor() {
        return new LightridersProcessor(this.players);
    }

    @Override
    protected void sendGameSettings(LightridersPlayer player) {
        player.sendSetting("your_botid", player.getId());
        player.sendSetting("field_width", configuration.getInt("fieldWidth"));
        player.sendSetting("field_height", configuration.getInt("fieldHeight"));
    }

    @Override
    protected String getPlayedGame(LightridersState initialState) {
        LightridersSerializer serializer = new LightridersSerializer();
        return serializer.traverseToString(this.processor, initialState);
    }


    @Override
    protected LightridersState getInitialState() {
        LightridersState s = new LightridersState();
        LightridersBoard b = new LightridersBoard(configuration.getInt("fieldWidth"), configuration.getInt("fieldHeight"));
        b.clear();
        for (LightridersPlayer player : this.players) {
            player.setCoordinate(getStartCoordinate(player.getId(), b.getWidth(), b.getHeight()));

            player.setDirection(MoveType.LEFT);
            if (player.getCoordinate().getX() < b.getWidth() / 2) {
                player.setDirection(MoveType.RIGHT);
            }

            b.setFieldAt(player.getCoordinate(), player.getColor().toString().substring(0, 1));

            s.setPlayerData(player);
        }
        s.setBoard(b);

        return s;
    }


    protected Point getStartCoordinate(int playerNr, int width, int height) {
        if (this.players.size() == 2) {
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
            default:
                Random r = new Random();
                return new Point(r.nextInt(width), r.nextInt(height));
        }
    }
}
