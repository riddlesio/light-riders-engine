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

package com.theaigames.tron;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.theaigames.game.player.AbstractPlayer;
import com.theaigames.game.GameHandler;
import com.theaigames.tron.Tron;
import com.theaigames.tron.field.Field;
import com.theaigames.tron.moves.Move;
import com.theaigames.tron.moves.MoveResult;
import com.theaigames.tron.player.Player;
import com.theaigames.tron.testsuite.Testsuite;

public class Processor implements GameHandler {
	
	private int mMoveNumber = 1;
	private int mRoundNumber = -1;
	private List<Player> mPlayers;
	private List<MoveResult> mMoveResults;
	private Field mField;
	private Player mGameOverByPlayerErrorPlayer = null;
	private int mPassesInARow = 0;

	public Processor(List<Player> players, Field field) {
		mPlayers = players;
		mField = field;
		mMoveResults = new ArrayList<MoveResult>();
		if (Tron.DEV_MODE) {
			System.out.println("Running in DEV_MODE");
			//Testsuite t = new Testsuite();
			//t.dbgTestKoRule(mField);
			//t.dbgTestCapture(mField);
			//t.dbgTestSuicideRule(mField);
			//t.dbgTestScore(mField);
		}
	}
	
	@Override
	public void playRound(int roundNumber) {
		System.out.println(String.format("playing round %d", roundNumber));
		mRoundNumber = roundNumber;
		for (Player player : mPlayers) {
			if (!isGameOver()) {
			    sendUpdates(player);
				String response = player.requestMove("move");
				if (!parseResponse(response, player)) {
					response = player.requestMove("move");
					if (!parseResponse(response, player)) {
					    mGameOverByPlayerErrorPlayer = player; /* Too many errors, other player wins */
					}
				}
				player.setLastMove(response);
				mMoveNumber++;
				//if (Go.DEV_MODE) {
					//mField.dumpBoard();
				//}
			}
		}
	}
	
	/**
	 * Sends round updates to given player
	 * @param player : player to send updates to
	 */
	private void sendUpdates(Player player) {
	    Player opponent = getOpponent(player);
	    
	    player.sendUpdate("round", mRoundNumber);
        player.sendUpdate("move", mMoveNumber);
        player.sendUpdate("points", player, mField.getPlayerScore(player.getId()) + "");
        player.sendUpdate("points", opponent, mField.getPlayerScore(opponent.getId()) + "");
        player.sendUpdate("field", mField.toString());
        player.sendUpdate("last_move", opponent, opponent.getLastMove());
	}
	
	/**
	 * Parses player response and inserts disc in field
	 * @param args : command line arguments passed on running of application
	 * @return : true if valid move, otherwise false
	 */
	private Boolean parseResponse(String r, Player player) {
		String[] parts = r.split(" ");
		if (parts[0].equals("place_move")) {
			try {
				int column = (int) Double.parseDouble(parts[1]);
				int row = (int) Double.parseDouble(parts[2]);
				
				if (mField.addMove(column, row, player.getId())) {
					recordMove(player);
					mPassesInARow = 0;
					return true;
				} else {
					player.getBot().outputEngineWarning(mField.getLastError());
				}
			} catch (Exception e) {
				createParseError(player, r);
			}
			recordMove(player);
		} else if (parts[0].equals("pass")) {
			mPassesInARow++;
			Move move = new Move(player);
			move.setMove("pass", mField.getLastX(), mField.getLastY());
			if (mPassesInARow == 2) {
				move.setIllegalMove("Second pass");
			} else {
				move.setIllegalMove("Pass");
			}
			MoveResult moveResult = new MoveResult(player, getOpponent(player), move, mRoundNumber, mField);
			mMoveResults.add(moveResult);
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
	}
	
	private void recordMove(Player player) {
		Move move = new Move(player);
		move.setMove("move", mField.getLastX(), mField.getLastY());
		move.setIllegalMove(mField.getLastError());
		
		MoveResult moveResult = new MoveResult(player, getOpponent(player), move, mRoundNumber, mField);

		mMoveResults.add(moveResult);
	}
	
	@Override
	public int getRoundNumber() {
		return this.mRoundNumber;
	}

	@Override
	public AbstractPlayer getWinner() {
	    if (mGameOverByPlayerErrorPlayer != null)
	        return getOpponent(mGameOverByPlayerErrorPlayer);
	    
	    int p1Index = 0;
	    int p2Index = 1;
		double scorePlayer1 = mField.getPlayerScore(mPlayers.get(p1Index).getId());
		double scorePlayer2 = mField.getPlayerScore(mPlayers.get(p2Index).getId());

		if (scorePlayer1 > scorePlayer2) return mPlayers.get(p1Index);
		if (scorePlayer2 > scorePlayer1) return mPlayers.get(p2Index);

		return null;
	}

	@Override
	public String getPlayedGame() {
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
	}
	
	private JSONObject getOutputState(MoveResult mr, boolean finalState) throws JSONException {
	    JSONObject state = new JSONObject();
	    AbstractPlayer winner = getWinner();
	    
	    String winnerstring = "";
	    if (finalState) {
            if (winner == null) {
                winnerstring = "none";
            } else {
                winnerstring = winner.getName();
            }
	    }
	    
	    JSONArray players = new JSONArray();
	    for (Player player : mPlayers) {
	        int playerId = player.getId();
	        JSONObject playerState = new JSONObject();
	        
	        playerState.put("stones", mr.getTotalStones(playerId));
	        playerState.put("stonestaken", mr.getStonesTaken(playerId));
	        playerState.put("score", mr.getTotalScore(playerId));
	        
	        players.put(playerState);
	    }
	    
        state.put("field", mr.getFieldString());
        state.put("round", mr.getRoundNumber());
        state.put("action", mr.getMove().getAction());
        state.put("winner", winnerstring);
        state.put("player", mr.getPlayer().getId());
        state.put("players", players);
        state.put("illegalMove", mr.getMove().getIllegalMove());
        
        return state;
	}
	
	/**
	 * Returns player's opponent given player
	 * @param player : player to find its opponent for
	 * @return : player's opponent
	 */
	private Player getOpponent(Player player) {
	    if (mPlayers.get(0).equals(player))
	        return mPlayers.get(1);
	    return mPlayers.get(0);
	}

	public Field getField() {
		return mField;
	}

	@Override
	public boolean isGameOver() {
		return (!mField.isMoveAvailable() || mPassesInARow >=2 
		        || mGameOverByPlayerErrorPlayer != null);
	}
}