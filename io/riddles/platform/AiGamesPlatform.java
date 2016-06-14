package io.riddles.platform;

import java.io.IOException;
import java.util.HashMap;

import io.riddles.engine.Engine;
import io.riddles.game.engine.GameEngine;
import io.riddles.game.exception.InvalidInputException;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.StringIdentifier;
import io.riddles.tron.game.TronGameEngine;

public class AiGamesPlatform<AiGamesEngine> implements Platform {
	private String args[];
	private HashMap<String, Object> configuration;
	private AiGamesIOHandler handler;
	private GameEngine engine;

	public AiGamesPlatform(String args[]) {
		this.args = args;
	}
	
	public IOHandler getHandler() {
		return handler;
	}
	
	public HashMap<String, Object> getConfiguration() {
		return configuration;
	}
	
	public void setEngine(TronGameEngine engine) {
		this.engine = engine;
	}
	
	public void preRun() throws IOException {
		configuration = new HashMap<String, Object>();
		
		buildConfiguration();

		configuration.put("board_size",  "16");
		configuration.put("timebank",  "10000");
		configuration.put("time_per_move",  "200");
		
		configuration.put("playerids",  "1,2");
		configuration.put("dev_mode",  false);
		
		/* Get configuration via handler */
		
		int NUM_TEST_BOTS = 2;
		String TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";

		for(int i = 0; i < NUM_TEST_BOTS; i++) {
			this.engine.addPlayer(TEST_BOT, new StringIdentifier("ID_" + i));
		}
	}
	
	private void buildConfiguration() throws IOException {
		/*
		String line = h.getNextMessage();
		while(line != null) {
			System.out.println(line);
			if(line.length() == 0) {
				continue;
			}

			String[] parts = line.split(" ");
			switch (parts[0]) {
				case "setting":
					configuration.put(parts[1], parts[2]);
					break;
				case "start":
					return;
			}
		}
		line = h.getNextMessage();
		*/
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
		System.out.println("Done.");
	}

	@Override
	public void setEngine(Object engine) {
		// TODO Auto-generated method stub
		
	}
}