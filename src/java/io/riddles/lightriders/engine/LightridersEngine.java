package io.riddles.lightriders.engine;

import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.data.LightridersBoard;
import io.riddles.lightriders.game.data.Coordinate;

import io.riddles.lightriders.game.data.MoveType;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.processor.LightridersProcessor;
import io.riddles.lightriders.game.state.LightridersState;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.lightriders.game.LightridersSerializer;

import java.util.Random;

/**
 * Created by joost on 6/27/16.
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    protected Coordinate[] startCoordinates;
    protected String bot_ids;
    protected int nrPlayers = 0, addedPlayers = 0;

    public LightridersEngine() {
        super();
        configuration.put("board_width", 16);
        configuration.put("board_height", 16);
    }

    public LightridersEngine(String wrapperFile, String[] botFiles) {
        super(wrapperFile, botFiles);
        configuration.put("board_width", 16);
        configuration.put("board_height", 16);
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
        /* TODO: Send all settings needed */
        LightridersState initialState = getInitialState();
        player.sendSetting("player_names", this.bot_ids);
        player.sendSetting("field_width", initialState.getBoard().getWidth());
        player.sendSetting("field_height", initialState.getBoard().getHeight());
        player.sendSetting("time_per_move", 5000); /* TODO: find this value */
        player.sendSetting("your_botid", player.getId());
    }

    @Override
    protected String getPlayedGame(LightridersState initialState) {
        LightridersSerializer serializer = new LightridersSerializer();
        return serializer.traverseToString(this.processor, initialState);
    }


    @Override
    protected LightridersState getInitialState() {
        LightridersState s = new LightridersState();
        LightridersBoard b = new LightridersBoard(configuration.get("board_width"), configuration.get("board_height"));
        for (LightridersPlayer player : this.players) {
            b.setFieldAt(player.getCoordinate(), player.getColor().toString().substring(0,1));
        }
        s.setBoard(b);
        return s;
    }

    @Override
    protected void parseSetupInput(String input) {
        String[] split = input.split(" ");
        String command = split[0];
        if (command.equals("bot_ids")) {
            this.bot_ids = split[1];
            String[] ids = split[1].split(",");
            this.nrPlayers = ids.length;
            for (int i = 0; i < ids.length; i++) {
                LightridersPlayer player = createPlayer(Integer.parseInt(ids[i]));
                if (this.botInputFiles != null)
                    player.setInputFile(this.botInputFiles[i]);
                this.players.add(player);
            }
        } else if (command.equals("board_width")) {
            configuration.put("board_width", Integer.parseInt(split[1]));
        } else if (command.equals("board_height")) {
            configuration.put("board_height", Integer.parseInt(split[1]));
        } else if (command.equals("max_rounds")) {
            configuration.put("max_rounds", Integer.parseInt(split[1]));
        }
    }


    protected Coordinate getStartCoordinate(int playerNr) {
        LightridersBoard b = getInitialState().getBoard();

        int width = b.getWidth();
        int height = b.getHeight();
        if (this.nrPlayers == 2) {
            switch (playerNr) {
                case 0:
                    return new Coordinate((int) Math.floor(width / 4), height / 2);
                case 1:
                    return new Coordinate((int) Math.floor(width / 4) * 3, height / 2);
            }
        } else {
            switch (playerNr) {
                case 0:
                    return new Coordinate((int) Math.floor(width / 4), height / 4);
                case 1:
                    return new Coordinate((int) Math.floor(width / 4) * 3, height / 4);
                case 2:
                    return new Coordinate((int) Math.floor(width / 4), height / 4 * 3);
                case 3:
                    return new Coordinate((int) Math.floor(width / 4) * 3, height / 4 * 3);
            }
        }
        Random r = new Random();
        return new Coordinate(r.nextInt(width), r.nextInt(height));
    }
}
