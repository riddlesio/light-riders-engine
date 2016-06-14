package io.riddles.platform;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import io.riddles.game.io.RiddlesIOHandler;
import io.riddles.game.io.StringIdentifier;
import io.riddles.tron.game.TronGameEngine;

/**
 * 
 *
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Joost <joost@riddles.io>
 */
public class Platform {

	TronGameEngine engine;
	HashMap<String, Object> configuration;
	
    /**
     * Runs the game
     */
	public static void main(String args[]) throws Exception {
		Platform p = new Platform();
		p.engine = new TronGameEngine();
		
		p.preRun();
		p.run();
		p.postRun();
	}
	
	private void preRun() throws IOException {
		configuration = new HashMap<String, Object>();
		
		getConfiguration();

		configuration.put("playerids",  "1,2");
		configuration.put("dev_mode",  false);
		
		
		
		/* Get configuration via handler */
		
		int NUM_TEST_BOTS = 2;
		String TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";

		for(int i = 0; i < NUM_TEST_BOTS; i++) {
			engine.addPlayer(TEST_BOT, new StringIdentifier("ID_" + i));
		}
	}
	
	private void getConfiguration() {
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()) {
			String line = scan.nextLine();

			if(line.length() == 0) {
				continue;
			}

			String[] parts = line.split(" ");
			switch (parts[0]) {
				case "setting":
					configuration.put(parts[1], parts[2]);
					break;
				case "start":
					scan.close();
					return;
			}
		}
	}
	
	private void run() throws RuntimeException {
		if (configuration.get("board_size") == null) {
			throw new RuntimeException("Board size not set.");
		}
		engine.run(configuration, "");
		/* Handle configuration problems */
	}
	
	private void postRun() {
		System.out.println("Done.");
	}
}