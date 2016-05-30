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

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.SquareBoard;
import io.riddles.game.GameHandler;
import io.riddles.game.exception.NoMoreMovesException;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.IOResponse;
import io.riddles.game.player.AbstractPlayer;
import io.riddles.tron.Tron;
import io.riddles.tron.io.TronIOProvider;
import io.riddles.tron.io.TronIORequest;
import io.riddles.tron.io.TronIORequestType;
import io.riddles.tron.io.TronIOResponse;
import io.riddles.tron.io.TronIOResponseType;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Move;
import io.riddles.tron.player.Player;
import io.riddles.util.Util;

import java.util.ArrayList;

import java.util.List;


/* Class to glue processor to new TronProcessor */ 
public class TronGameHandler implements GameHandler {
	
	private int mMoveNumber = 1;
	private int mRoundNumber = -1;
	private List<Player> mPlayers;
	private Board mBoard;
	private boolean mGameOver = false;
	private TronState previousState;
	
	public TronGameHandler(List<Player> players, Board board) {
		mPlayers = players;
		mBoard = board;
		previousState = new TronState(mBoard);
		
		if (Tron.DEV_MODE) {
			System.out.println("Running in DEV_MODE");
			//Testsuite t = new Testsuite();
			//t.dbgTestKoRule(mField);
		}
	}
	
	@Override
	public void playRound(int roundNumber) {
		System.out.println(String.format("playing round %d", roundNumber));
		mRoundNumber = roundNumber;
		
		TronProcessor processor = new TronProcessor();
		for (Player player : mPlayers) {
			if (!isGameOver()) {
				TronState state = new TronState(previousState);
				state.setActivePieceColor(player.getPieceColor());
				sendUpdates(player);
				
				TronIORequest request = new TronIORequest(player.getPieceColor(), TronIORequestType.MOVE);
				//TronIOProvider provider = new TronIOProvider(player.getHandler());
				//TronIOResponse response = provider.execute(request);
				/*
				
				
				String s = player.requestMove("move");
				
				try {
					state = processor.processInput(state, response);
					mBoard = state.getBoard();
					previousState = state;
				} catch (Exception e) {
					if (e instanceof NoMoreMovesException) {
						mGameOver = true;
					}
					System.out.println("Exception: " + e.toString());
				}
				*/
			}
			
			/*
				if (player.isAlive()) {
					sendUpdates(player);
					
					String input = player.requestMove("move");
					try {
						processor.processInput(state, input);
					} catch (Exception e) {
						System.out.println("Exception: " + e.toString());
					}
					
				} else {
					//recordMove(player);
				}
				mMoveNumber++;
			}
			*/
		}
		/* TODO: this is temporary */
		if (mRoundNumber == 43) {
			Util.dumpBoard(mBoard);
			System.exit(0);
		}
		
	}
	
	/**
	 * Sends round updates to given player
	 * @param player : player to send updates to
	 */
	private void sendUpdates(Player player) {
	    //Player opponent = getOpponent(player);
	    
	    player.sendUpdate("round", mRoundNumber);
        player.sendUpdate("move", mMoveNumber);
        //player.sendUpdate("field", mField.toString());
        player.sendUpdate("your_location", player.getX() + "," + player.getY());
        //player.sendUpdate("opponent_location", opponent.getX() + "," + opponent.getY());
        player.sendUpdate("your_direction", "left"); /* TODO */
        //player.sendUpdate("opponent_direction", Util.directionToString(opponent.getDirection()));
	}
	
	/**
	 * Parses player response and executes action
	 * @param args : command line arguments passed on running of application, Player involved
	 * @return : true if valid action, otherwise false
	 */
	/*
	private Boolean parseResponse(String r, Player player) {
		String[] parts = r.split(" ");
		if (parts[0].equals("turn_direction")) {
			try {
				String direction = parts[1];
				if (player.turnDirection(Util.directionToInt(direction))) {
					recordMove(player);
					mField.setPlayerDirection(player, Util.directionToInt(direction));
					if (!mField.update(player)) {
						player.die();
					}
					return true;
				} else {
					player.getBot().outputEngineWarning("Illegal turn");
					mField.setLastError("Illegal turn");
				}
			} catch (Exception e) {
				createParseError(player, r);
			}
			recordMove(player);
		} else if (parts[0].equals("pass")) {
			Move move = new Move(player);
			move.setMove("pass", player.getX(), player.getY());
			MoveResult moveResult = new MoveResult(player, getOpponent(player), move, mRoundNumber, mField);
			mMoveResults.add(moveResult);
			if (!mField.update(player)) {
				player.die();
			}
			return true;
		} else {
			createParseError(player, r);
			recordMove(player);
		}
		return false;
	}
	
	private void createParseError(Player player, String input) {
		mField.setLastError("Error: failed to parse input");
		player.getBot().outputEngineWarning(String.format("Failed to parse input '%s'", input));
		System.out.println("Error: failed to parse input");
	}
	
	private void recordMove(Player player) {
		Move move = new Move(player);
		move.setMove("move", player.getX(), player.getY());
		move.setIllegalMove(mField.getLastError());
		
		MoveResult moveResult = new MoveResult(player, getOpponent(player), move, mRoundNumber, mField);

		mMoveResults.add(moveResult);
	}
	*/
	
	@Override
	public int getRoundNumber() {
		return this.mRoundNumber;
	}

	@Override
	public AbstractPlayer getWinner() {
	    /* If only one player is still going, that player is the winner */
		if (getNrPlayersAlive() == 1) {
			for (Player player : mPlayers) {
				if (player.isAlive()) return player;
			}
		}
		return null;
	}
	
	public int getNrPlayersAlive() {
		int nrPlayersAlive = 0;
		for (Player player : mPlayers) {
			if (player.isAlive()) nrPlayersAlive++;
		}
		return nrPlayersAlive;
	}

	@Override
	public String getPlayedGame() {
		/*
		JSONObject output = new JSONObject();

		try {
			JSONArray playerNames = new JSONArray();
			for(Player player : this.mPlayers) {
				playerNames.put(player.getName());
			}

			output.put("settings", new JSONObject()
			.put("field", new JSONObject()
					.put("width", String.valueOf(getField().getNrColumns()))
					.put("height", String.valueOf(getField().getNrRows())))
			.put("players", new JSONObject()
					.put("count", this.mPlayers.size())
					.put("names", playerNames))
			);

			JSONArray states = new JSONArray();
			int counter = 0;
			for (MoveResult mr : mMoveResults) {
				states.put(getOutputState(mr, false));
				if (counter == mMoveResults.size()-1) { // final overlay state with winner
					states.put(getOutputState(mr, true));
				}
				counter++;
			}
			output.put("states", states);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return output.toString();
		*/
		return "";
	}
	
	

	public Board getBoard() {
		return mBoard;
	}

	@Override
	public boolean isGameOver() {
		return mGameOver;
	}
}