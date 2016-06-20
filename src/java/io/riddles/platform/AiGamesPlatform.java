package io.riddles.platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

import io.riddles.game.engine.GameEngine;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.StringIdentifier;
import io.riddles.tron.TronState;
import io.riddles.tron.visitor.TronStateToJSONVisitor;

public class AiGamesPlatform implements Platform {
	private String args[];
	private HashMap<String, Object> configuration;
	private AiGamesIOHandler handler;
	private GameEngine engine;
	private final Boolean DEV_MODE = true;

	public AiGamesPlatform(String args[]) throws IOException, RuntimeException {
		this.args = args;
		this.handler = new AiGamesIOHandler(); 
	}
	
	public IOHandler getHandler() {
		return handler;
	}
	
	public HashMap<String, Object> getConfiguration() {
		return configuration;
	}
	
	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}
	
	public void preRun() throws IOException {
		configuration = new HashMap<String, Object>();
		
		if (DEV_MODE) {
			configuration.put("board_size",  "16");
			configuration.put("timebank",  "10000");
			configuration.put("time_per_move",  "200");
			configuration.put("playerids",  "1,2");
			int NUM_TEST_BOTS = 2;
			String TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";
			//String TEST_BOT = "java -cp /media/joost/5c2fc3a1-c9fa-4c17-a054-b2da1b1fac0e/workspace/tronbot/bin/ bot.BotStarter";

			for(int i = 0; i < NUM_TEST_BOTS; i++) {
				this.engine.addPlayer(TEST_BOT, new StringIdentifier("ID_" + i));
			}
		} else {
			setupEngine(args);
			buildConfiguration();
		}

		
		/* Get configuration via handler */
		

	}
	
	private void buildConfiguration() throws IOException {
        List<String> botDirs = new ArrayList<String>();
        List<String> botIds = new ArrayList<String>();
        
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			String[] parts = line.split(" ");
			switch (parts[0]) {
				case "setting":
					configuration.put(parts[1], parts[2]);
					break;
				case "start":
					scanner.close();
					return;
			}
		}
	}
	
	public void run() throws Exception {
		
		if (configuration.get("board_size") == null) {
			throw new RuntimeException("board_size not set.");
		}
		if (configuration.get("timebank") == null) {
			throw new RuntimeException("timebank not set.");
		}
		if (configuration.get("time_per_move") == null) {
			throw new RuntimeException("time_per_move not set.");
		}
		/* Send settings, board op basis van state */
		engine.run(configuration, "");
	}
	
	public void postRun() {
		/* riddles: wait 'ask game / details' */
		/* output correct data */
		/* See Viewer Adyen */

        if(DEV_MODE) { // print the game file when in DEV_MODE
        	/* Get final state from engine */
        	TronState finalState = (TronState) this.engine.getFinalState();
            JSONObject output = new JSONObject();
            
            
            TronStateToJSONVisitor v = new TronStateToJSONVisitor();
            v.visit(finalState);
            JSONArray states = v.getJSONArray();
            output.put("states", states);
            System.out.println(output.toString());
            
        } else { // save the game to database
            try {
                this.saveGame();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Done.");
        
        System.exit(0);
	}
	
	 /**
     * Partially sets up the engine
     * @param args : command line arguments passed on running of application
     * @throws IOException
     * @throws RuntimeException
     */
    public void setupEngine(String args[]) throws IOException, RuntimeException {
        // add the bots from the arguments if not in DEV_MODE
        List<String> botDirs = new ArrayList<String>();
        List<String> botIds = new ArrayList<String>();

        
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
			this.engine.addPlayer(String.format("/opt/aigames/scripts/run_bot.sh aiplayer%d %s", i + 1, botDirs.get(i)), new StringIdentifier("ID_" + i));
        }
    }
    
    /**
     * Does everything that is needed to store the output of a game
     */
    public void saveGame() {
        
        //AbstractPlayer winner = this.processor.getWinner();
        /*
        ObjectId winnerId = null;
        int score = this.processor.getRoundNumber() - 1;
        BasicDBObject errors = new BasicDBObject();
        BasicDBObject dumps = new BasicDBObject();
        String gamePath = "games/" + this.gameIdString;

        if(winner != null) {
            System.out.println("winner: " + winner.getName());
            winnerId = new ObjectId(winner.getBot().getIdString());
        } else {
            System.out.println("winner: draw");
        }
        
        System.out.println("Saving the game...");
        
        // save the visualization file to amazon
        Amazon.connectToAmazon();
        String savedFilePath = Amazon.saveToAmazon(this.processor.getPlayedGame(), gamePath + "/visualization");
        
        // save errors and dumps to amazon and create object for database
        int botNr = 0;
        for(IOPlayer ioPlayer : this.engine.getPlayers()) {
            botNr++;
            errors.append(ioPlayer.getIdString(), Amazon.saveToAmazon(ioPlayer.getStderr(), String.format("%s/bot%dErrors", gamePath, botNr)));
            dumps.append(ioPlayer.getIdString(), Amazon.saveToAmazon(ioPlayer.getDump(), String.format("%s/bot%dDump", gamePath, botNr)));
        }
        
        // store everything in the database
        Database.connectToDatabase();
        Database.storeGameInDatabase(this.gameIdString, winnerId, score, savedFilePath, errors, dumps);
        */
    }
}