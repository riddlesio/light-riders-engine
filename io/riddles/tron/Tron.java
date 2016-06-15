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

import io.riddles.game.io.IOHandler;
import io.riddles.platform.AiGamesPlatform;
import io.riddles.platform.Platform;
//import io.riddles.platform.RiddlesPlatform;
import io.riddles.tron.game.TronGameEngine;
import io.riddles.tron.player.Player;

import java.util.HashMap;
import java.util.List;


public class Tron {
	
	private static TronGameEngine engine;
	private static HashMap<String, Object> configuration;
	private static IOHandler handler;
	private final static String PLATFORM = "aigames";
	private static Platform platform;


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
		
		if (PLATFORM == "aigames") {
			platform = new AiGamesPlatform(args);
		} else {
			//platform = new RiddlesPlatform();
		}
		
		handler = platform.getHandler();
		engine = new TronGameEngine(handler);
	
		platform.setEngine(engine);
		platform.preRun();
		platform.run();
		platform.postRun();
	}
}
