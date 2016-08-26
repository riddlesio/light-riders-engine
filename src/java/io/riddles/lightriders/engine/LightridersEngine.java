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

/**
 * Created by joost on 6/27/16.
 */
public class LightridersEngine extends AbstractEngine<LightridersProcessor, LightridersPlayer, LightridersState> {

    protected Coordinate[] startCoordinates;
    protected String bot_ids;
    protected int nrPlayers = 0;

    public LightridersEngine() {
        super();
        configuration.put("board_width", 16);
        configuration.put("board_height", 16);
        initialiseData();
    }

    public LightridersEngine(String wrapperFile, String[] botFiles) {
        super(wrapperFile, botFiles);
        configuration.put("board_width", 16);
        configuration.put("board_height", 16);
        initialiseData();
    }

    protected void initialiseData() {
        /* TODO: Calculate coordinates, directions */
        this.startCoordinates = new Coordinate[4];
        this.startCoordinates[0] = new Coordinate(1, 5);
        this.startCoordinates[1] = new Coordinate(19, 5);
        this.startCoordinates[2] = new Coordinate(1, 8);
        this.startCoordinates[3] = new Coordinate(19, 8);
    }

    @Override
    protected LightridersPlayer createPlayer(int id) {
        LightridersPlayer p = new LightridersPlayer(id);
        p.setColor(Color.values()[nrPlayers]); /* This limits the game to four players */
        p.setDirection(MoveType.LEFT);
        if (p.getCoordinate().getX() < getInitialState().getBoard().getWidth()/2) p.setDirection(MoveType.RIGHT);
        nrPlayers++;
        return p;
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
            for (int i = 0; i < ids.length; i++) {
                LightridersPlayer player = createPlayer(Integer.parseInt(ids[i]));
                if (this.botInputFiles != null)
                    player.setInputFile(this.botInputFiles[i]);
                player.setCoordinate(getStartCoordinate(i));
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

    protected Coordinate getStartCoordinate(int i) {
        return this.startCoordinates[i];
    }
}
