// Copyright 2016 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package com.theaigames.go;

import java.util.ArrayList;
import java.util.List;

import com.theaigames.engine.io.IOPlayer;
import com.theaigames.game.AbstractGame;
import com.theaigames.game.player.AbstractPlayer;
import com.theaigames.go.field.Field;
import com.theaigames.go.player.Player;

public class Go extends AbstractGame {
	
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 200;
	private final int FIELD_HEIGHT = 19;
	private final int FIELD_WIDTH = 19;
	private final double KOMI = 7.5;
	private static final int MAX_ROUNDS = 250;
	private List<Player> players;
	private Field mField;

	@Override
	public void setupGame(ArrayList<IOPlayer> ioPlayers) throws Exception {			
		// create all the players and everything they need
		this.players = new ArrayList<Player>();

		for(int i=0; i<ioPlayers.size(); i++) {
			// create the player
			String playerName = String.format("player%d", i+1);
			Player player = new Player(playerName, ioPlayers.get(i), TIMEBANK_MAX, TIME_PER_MOVE, i+1);
			this.players.add(player);

		}
		for(Player player : this.players) {
			sendSettings(player);
		}
		
		// create the playing field
        this.mField = new Field(FIELD_WIDTH, FIELD_HEIGHT, KOMI, this.players);
		
		// create the processor
		super.processor = new Processor(this.players, this.mField);
	}

	public void sendSettings(Player player) {
		String playerString = this.players.get(0).getName() + "," + this.players.get(1).getName();
		player.sendSetting("timebank", TIMEBANK_MAX);
		player.sendSetting("time_per_move", TIME_PER_MOVE);
		player.sendSetting("player_names", playerString);
		player.sendSetting("your_bot", player.getName());
		player.sendSetting("your_botid", player.getId());
		player.sendSetting("field_width", FIELD_WIDTH);
		player.sendSetting("field_height", FIELD_HEIGHT);
		player.sendSetting("max_rounds", MAX_ROUNDS);
	}

	@Override
	protected void runEngine() throws Exception {
	    System.out.println("starting...");
	    
		super.engine.setLogic(this);
		super.engine.start();
	}
	
	public static void main(String args[]) throws Exception {
		Go game = new Go();
//		AbstractGame.DEV_MODE = true;
//		game.TEST_BOT = "java -cp /home/jim/workspace/go-starterbot/bin/ bot.BotStarter";
//		game.NUM_TEST_BOTS = 2;
		game.setupEngine(args);
		game.maxRounds = MAX_ROUNDS;
		game.runEngine();
	}

	@Override
	public void sendSettings(AbstractPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
