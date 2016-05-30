package io.riddles.tron.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.engine.Engine;
import io.riddles.engine.Processor;
import io.riddles.engine.io.IOPlayer;
import io.riddles.game.engine.GameEngine;
import io.riddles.game.engine.GameLoop;
import io.riddles.game.engine.SimpleGameLoop;
import io.riddles.game.io.IOHandler;
import io.riddles.tron.TronPiece;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.TronProcessor;
import io.riddles.tron.TronState;
import io.riddles.tron.io.TronIOProvider;
import io.riddles.tron.player.Player;

/**
 * This class is the connecting instance between the Tron game and the
 * encapsulating framework. It should implement all methods required for
 * the Riddles.io framework to retrieve the necessary game data.
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko van Meurs <niko@riddles.io>
 */
public class TronGameEngine implements GameEngine {

    private GameLoop<TronState> gameLoop;
    private Processor<TronState> processor;
    private TronState finalState;
    private ArrayList<IOPlayer> players; // ArrayList containing player handlers
    
    public final int BOARD_SIZE = 64;
	public static boolean DEV_MODE = false; // turn this on for local testing
	public String TEST_BOT; // command for the test bot in DEV_MODE
	public int NUM_TEST_BOTS; // number of bots for this game
	
    public TronGameEngine() {
        this.players = new ArrayList<IOPlayer>();

        gameLoop  = new SimpleGameLoop<>();
        processor = new TronProcessor();
    }

    /**
     * Deserializes the initialState string and runs the GameLoop
     *
     * @param initialStateString - String representation of the initial State
     */
    public void run() {
    	/* TODO: initialState not used */

		IOHandler handler = new IOHandler(players.get(0).getProcess());
    	TronIOProvider provider = new TronIOProvider(handler);
        finalState = gameLoop.run(provider, processor, getInitialState());
    }

    /**
     * TODO: expose all functions necessary to have the match data
     *       consumed by the Riddles.io and TheAIGames libraries.
     */
    
    public TronState getInitialState() {
    	
    	Board b = new SquareBoard(BOARD_SIZE);
    	TronState s = new TronState(b);
    	
		/* Initialise player positions */
		int counter = 0;
		/*
		for (Player player : this.players) {
			switch (counter) {
				case 0:
					player.setX(BOARD_SIZE/4);
					player.setY(BOARD_SIZE/2);
					player.setDirection(Direction.RIGHT);
					player.setPieceColor(PieceColor.CYAN);
					break;
				case 1:
					player.setX(BOARD_SIZE/4*3);
					player.setY(BOARD_SIZE/2);
					player.setDirection(Direction.LEFT);
					player.setPieceColor(PieceColor.PURPLE);
					break;
				default:
					Random r = new Random();
					player.setX(r.nextInt(BOARD_SIZE));
					player.setY(r.nextInt(BOARD_SIZE));
					player.setDirection(Direction.RIGHT);
					player.setPieceColor(PieceColor.YELLOW);
			}
			b.getFieldAt(new Coordinate(player.getX(),player.getY())).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, player.getPieceColor())));
			counter ++;
		}
		*/
	    return s;
    }
    
    /**
	 * Partially sets up the engine
	 * @param args : command line arguments passed on running of application
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public void setupEngine(String args[]) throws IOException, RuntimeException {
		
        // create engine
        
        // add the test bots if in DEV_MODE
        if(DEV_MODE) {
            if(TEST_BOT.isEmpty()) {
                throw new RuntimeException("DEV_MODE: Please provide a command to start the test bot by setting 'TEST_BOT' in your main class.");
            }
            if(NUM_TEST_BOTS <= 0) {
                throw new RuntimeException("DEV_MODE: Please provide the number of bots in this game by setting 'NUM_TEST_BOTS' in your main class.");
            }
            
            for(int i = 0; i < NUM_TEST_BOTS; i++) {
                addPlayer(TEST_BOT, "ID_" + i);
            }
            
            return;
        }
        
        // add the bots from the arguments if not in DEV_MODE
        List<String> botDirs = new ArrayList<String>();
        List<String> botIds = new ArrayList<String>();
        
        try {
            //this.gameIdString = args[0];
        } catch(Exception e) {
            throw new RuntimeException("No arguments provided.");
        }
        
        // get the bot id's and location of bot program
        for(int i=1; i <= (args.length - 1) / 2; i++) { // first arguments are the bot ids
            botIds.add(args[i]);
        }
        for(int i=((args.length - 1) / 2) + 1; i < args.length; i++) { // last arguments are the bot dirs
            botDirs.add(args[i]);
        }
        
        // check is the starting arguments are passed correctly
        if(botIds.isEmpty() || botDirs.isEmpty() || botIds.size() != botDirs.size())
            throw new RuntimeException("Missing some arguments.");
        
        // add the players
        for(int i=0; i < botIds.size(); i++) {
            addPlayer(String.format("/opt/aigames/scripts/run_bot.sh aiplayer%d %s", i + 1, botDirs.get(i)), botIds.get(i));
        }
	}

    

    /**
     * Start up the bot process and add the player to the game
     * @param command : command to start a bot process
     */
    public void addPlayer(String command, String idString) throws IOException {

        // Create new process
    	Process process = Runtime.getRuntime().exec(command);

        // Attach IO to process
        IOPlayer player = new IOPlayer(process, idString);
        
        // Add player
        this.players.add(player);

        // Start running
        player.run();
    }

	@Override
	public void run(String initialStateString) {
		/* TODO: initialStateString not used */
		run();
	}
}