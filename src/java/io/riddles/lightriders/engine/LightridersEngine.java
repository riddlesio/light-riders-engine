package io.riddles.lightriders.engine;

import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.board.LightridersBoard;

import io.riddles.lightriders.game.data.MoveType;
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

    protected int nrPlayers = 0, addedPlayers = 0;

    public LightridersEngine() {
        super();
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
        player.setColor(Color.values()[addedPlayers]); /* This limits the game to four players */
        player.setCoordinate(getStartCoordinate(addedPlayers));
        player.setDirection(MoveType.LEFT);
        if (player.getCoordinate().getX() < getInitialState().getBoard().getWidth()/2) player.setDirection(MoveType.RIGHT);
        addedPlayers++;
        return player;
    }

    @Override
    protected LightridersProcessor createProcessor() {
        return new LightridersProcessor(this.players);
    }

    @Override
    protected void sendGameSettings(LightridersPlayer player) {
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
            b.setFieldAt(player.getCoordinate(), player.getColor().toString().substring(0,1));
        }
        s.setBoard(b);
        return s;
    }


    protected Point getStartCoordinate(int playerNr) {
        LightridersBoard b = getInitialState().getBoard();

        int width = b.getWidth();
        int height = b.getHeight();
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
