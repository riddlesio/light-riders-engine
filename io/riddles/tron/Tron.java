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
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.game.TronGameEngine;
import io.riddles.tron.player.Player;
import io.riddles.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Tron extends AbstractGame {
	
	private final int TIMEBANK_MAX = 10000;
	private final int TIME_PER_MOVE = 200;
	private final int BOARD_SIZE = 64;
	private List<Player> players;

	@Override
	public void setupGame(ArrayList<IOPlayer> ioPlayers) throws Exception {		
		// Create all the players and everything they need
		this.players = new ArrayList<Player>();
		
		for(int i=0; i<ioPlayers.size(); i++) {
			// Create the player
			String playerName = String.format("player%d", i+1);
			Player player = new Player(playerName, ioPlayers.get(i), TIMEBANK_MAX, TIME_PER_MOVE, i+1);
			PieceColor[] pieceColors = PieceColor.values();
			player.setPieceColor(pieceColors[i]); /* Maxed out at 4 players */
			this.players.add(player);
		}
		for(Player player : this.players) {
			sendSettings(player);
		}
		
		Board b = new SquareBoard(BOARD_SIZE);

		/* Initialise player positions */
		int counter = 0;
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
		player.sendSetting("board_size", BOARD_SIZE);
	}

	@Override
	protected void runEngine() throws Exception {
	    System.out.println("starting...");
	    
		super.engine.setLogic(this);
		super.engine.start();
	}
	
	public static void main(String args[]) throws Exception {
		Tron game = new Tron();
		TronGameEngine engine = new TronGameEngine();
		engine.DEV_MODE = true;
		engine.TEST_BOT = "java -cp /home/joost/workspace/TronBot/bin/ bot.BotStarter";
		engine.NUM_TEST_BOTS = 2;
		engine.addPlayer(engine.TEST_BOT, "bot0");
		engine.run();//engine.getInitialState());
	}

	@Override
	public void sendSettings(AbstractPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
