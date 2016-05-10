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

package io.riddles.tron;

import io.riddles.engine.io.IOPlayer;
import io.riddles.game.AbstractGame;
import io.riddles.game.player.AbstractPlayer;
import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.tron.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Tron extends AbstractGame {
	
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 200;
	private final int FIELD_HEIGHT = 64;
	private final int FIELD_WIDTH = 64;
	private List<Player> players;
	private List<Field> mFields;

	@Override
	public void setupGame(ArrayList<IOPlayer> ioPlayers) throws Exception {			
		// Create all the players and everything they need
		this.players = new ArrayList<Player>();

		for(int i=0; i<ioPlayers.size(); i++) {
			// Create the player
			String playerName = String.format("player%d", i+1);
			Player player = new Player(playerName, ioPlayers.get(i), TIMEBANK_MAX, TIME_PER_MOVE, i+1);
			this.players.add(player);

		}
		for(Player player : this.players) {
			sendSettings(player);
		}
		
		/* Initialise player positions */
		/*
		int counter = 0;
		for (Player player : this.players) {
			switch (counter) {
				case 0:
					player.setX(FIELD_WIDTH/4);
					player.setY(FIELD_HEIGHT/2);
					player.setDirection(Field.DIR_RIGHT);
					break;
				case 1:
					player.setX(FIELD_WIDTH/4*3);
					player.setY(FIELD_HEIGHT/2);
					player.setDirection(Field.DIR_LEFT);
					break;
				default:
					Random r = new Random();
					player.setX(r.nextInt(FIELD_WIDTH));
					player.setY(r.nextInt(FIELD_HEIGHT));
					player.setDirection(r.nextInt(4)*90);
			}
			counter ++;
		}
		*/

		// Create the playing field
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field());
		Board b = new SquareBoard(fields);
		
		// Create the processor
		super.processor = new TronGameHandler(this.players, b);
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
	}

	@Override
	protected void runEngine() throws Exception {
	    System.out.println("starting...");
	    
		super.engine.setLogic(this);
		super.engine.start();
	}
	
	public static void main(String args[]) throws Exception {
		Tron game = new Tron();
		AbstractGame.DEV_MODE = true;
		game.TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";
		game.NUM_TEST_BOTS = 4;
		game.setupEngine(args);
		game.runEngine();
	}

	@Override
	public void sendSettings(AbstractPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
