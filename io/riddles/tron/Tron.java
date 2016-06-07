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

import io.riddles.tron.game.TronGameEngine;
import io.riddles.tron.player.Player;

import java.util.List;


public class Tron {
	
	public static final Boolean DEV_MODE = true;
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 200;
	private final int BOARD_SIZE = 64;
	private List<Player> players;


	public void sendSettings(Player player) {
		/*
		String playerString = this.players.get(0).getName() + "," + this.players.get(1).getName();
		player.sendSetting("timebank", TIMEBANK_MAX);
		player.sendSetting("time_per_move", TIME_PER_MOVE);
		player.sendSetting("player_names", playerString);
		player.sendSetting("your_bot", player.getName());
		player.sendSetting("your_botid", player.getId());
		player.sendSetting("board_size", BOARD_SIZE);
		*/
	}

	
	public static void main(String args[]) throws Exception {
		Tron game = new Tron();
		
		TronGameEngine engine = new TronGameEngine();
		
		engine.DEV_MODE = true;
		//engine.TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";
		engine.TEST_BOT = "java -cp /media/joost/5c2fc3a1-c9fa-4c17-a054-b2da1b1fac0e/workspace/tronbot/bin/ bot.BotStarter";
		engine.NUM_TEST_BOTS = 2;
		engine.setupEngine(args);
		engine.run("test");
	}
}
