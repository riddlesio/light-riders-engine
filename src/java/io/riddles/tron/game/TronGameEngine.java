package io.riddles.tron.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.RectangularBoard;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.engine.Processor;
import io.riddles.game.engine.GameEngine;
import io.riddles.game.engine.GameLoop;
import io.riddles.game.engine.SimpleGameLoop;
import io.riddles.game.exception.InvalidDataException;
import io.riddles.game.exception.InvalidInputException;
import io.riddles.game.exception.TerminalException;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.Identifier;
import io.riddles.game.io.StringIdentifier;
import io.riddles.tron.TronLogic;
import io.riddles.tron.TronPiece;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.TronProcessor;
import io.riddles.tron.TronState;
import io.riddles.tron.io.TronIOProvider;
import io.riddles.tron.player.Player;
import io.riddles.util.Util;

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
public class TronGameEngine implements GameEngine<TronState> {

    private GameLoop<TronState> gameLoop;
    private Processor<TronState> processor;
    private TronState finalState;
    private ArrayList<Player> players; // ArrayList containing player handlers
    
    private int boardWidth = 16, boardHeight = 16;
	public static boolean DEV_MODE = false; // turn this on for local testing
	public String TEST_BOT; // command for the test bot in DEV_MODE
	public int NUM_TEST_BOTS; // number of bots for this game
	
	private IOHandler handler;
	private IOProvider provider;
	
    public TronGameEngine(IOHandler handler) {
        this.players = new ArrayList<Player>();
        this.handler = handler;
    	
        gameLoop  = new SimpleGameLoop<TronState>();
        processor = new TronProcessor();
    }

    /**
     * Deserializes the initialState string and runs the GameLoop
     *
     * @param initialStateString - String representation of the initial State
     * @throws InvalidInputException 
     */
    public void run(HashMap configuration) throws TerminalException {	
    	TronState initialState = getInitialState(null);
    	this.run(configuration, initialState);
    }
    
    public TronState getInitialState(String initialStateString) {
    	RectangularBoard b = new RectangularBoard(boardWidth, boardHeight);
		
		if (initialStateString != null) {
			try {
				b = TronLogic.StringToRectangularBoardTransformer(initialStateString);
			} catch (InvalidDataException e) {
				System.err.println("Could not initialise Board from initialStateString");
			}
		}
    	TronState s = new TronState(b);

		/* Initialise player positions */
		int counter = 0;
		for (Player player : this.players) {
			switch (counter) {
				case 0:
					player.setX(boardWidth/4);
					player.setY(boardHeight/2);
					player.setDirection(Direction.RIGHT);
					break;
				case 1:
					player.setX(boardWidth/4*3);
					player.setY(boardHeight/2);
					player.setDirection(Direction.LEFT);
					break;
				default:
					Random r = new Random();
					player.setX(r.nextInt(boardWidth));
					player.setY(r.nextInt(boardHeight));
					player.setDirection(Direction.RIGHT);
			}
			player.setPieceColor(PieceColor.values()[counter]);
			b.getFieldAt(new Coordinate(player.getX(),player.getY())).setPiece(Optional.of(new TronPiece(PieceType.LIGHTCYCLE, player.getPieceColor())));
			counter ++;
		}
		s.setActivePieceColor(this.players.get(0).getPieceColor());
				
		//Util.dumpBoard(b);
		return s;
	}

	public TronState getFinalState() {
		return this.finalState;
	}
    
    /**
	 * Partially sets up the engine
	 * @param args : command line arguments passed on running of application
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public void setupEngine(String args[]) throws IOException, RuntimeException {
    	
        // add the test bots if in DEV_MODE
        if(DEV_MODE) {
            if(TEST_BOT.isEmpty()) {
                throw new RuntimeException("DEV_MODE: Please provide a command to start the test bot by setting 'TEST_BOT' in your main class.");
            }
            if(NUM_TEST_BOTS <= 0) {
                throw new RuntimeException("DEV_MODE: Please provide the number of bots in this game by setting 'NUM_TEST_BOTS' in your main class.");
            }
            
            for(int i = 0; i < NUM_TEST_BOTS; i++) {
                addPlayer(TEST_BOT, new StringIdentifier("ID_" + i));
            }
            
        } else {
	        
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
	            addPlayer(String.format("/opt/aigames/scripts/run_bot.sh aiplayer%d %s", i + 1, botDirs.get(i)), new StringIdentifier(botIds.get(i)));
	        }
        }
	}

    

    /**
     * Start up the bot process and add the player to the game
     * @param command : command to start a bot process
     */
    public void addPlayer(String command, Identifier id) throws IOException {

        // Create new process
    	Process process = Runtime.getRuntime().exec(command);
  	
        Player player = new Player("name", id);
    	((AiGamesIOHandler) handler).addPlayerProcess(player, process);
    	
        // Add player
        this.players.add(player);
    }

	@Override
	public void run(HashMap configuration, String initialStateString) throws TerminalException {
		TronState initialState = this.getInitialState(initialStateString);
		this.run(configuration, initialState);
	}
	
	public void run(HashMap configuration, TronState initialState) throws TerminalException {
		setConfiguration(configuration);
        provider = new TronIOProvider(handler);
    	
        try {
    		finalState = gameLoop.run(provider, processor, initialState);
    	} catch (TerminalException exception) {
    		
    		// TODO: Serialize traversible into usable debug data and save this stuff
//    		TronState state = (TronState) exception.getTraversible();
    		
//    		TronTerminalExceptionSerializer serializer = new TronTerminalExceptionSerializer();
//    		String serializedData = serializer.traverse(state);
//    		exception.setSerializedData(serializedData);
    		
    		throw exception;
//    		
    	}
	}
	
	private Boolean setConfiguration(HashMap configuration) throws RuntimeException {
		if (configuration.get("board_width") == null) {
			throw new RuntimeException("Invalid configuration: please set board_width");
		} else {
			boardWidth = Integer.parseInt((String)configuration.get("board_width"));
		}
		if (configuration.get("board_height") == null) {
			throw new RuntimeException("Invalid configuration: please set board_height");
		} else {
			boardHeight = Integer.parseInt((String)configuration.get("board_height"));
		}
		return true;
	}
}