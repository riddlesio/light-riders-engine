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

package com.theaigames.go.moves;

import com.theaigames.go.field.Field;
import com.theaigames.go.player.Player;

public class MoveResult {
	private int mRoundNumber;
	private Player mPlayer;
	private Move mMove;
	private String mString;
	private int totalStones[] = new int[3];
	private int stonesTaken[] = new int[3];
	private double totalScore[] = new double[3];

	public MoveResult(Player player, Player opponent, Move move, int roundNumber, Field field) {
	    int pId = player.getId();
	    int oId = opponent.getId();
	    
		mPlayer = player;
		mMove = move;
		mRoundNumber = roundNumber;
		totalStones[pId] = field.getPlayerStones(pId);
		totalStones[oId] = field.getPlayerStones(oId);
		stonesTaken[pId] = field.getTotalStonesTaken(pId);
		stonesTaken[oId] = field.getTotalStonesTaken(oId);
		totalScore[pId] = field.getPlayerScore(pId);
		totalScore[oId] = field.getPlayerScore(oId);
		mString = field.toString();
	}
	
	public Player getPlayer() {
		return mPlayer;
	}
	
	public Move getMove() {
	    return mMove;
	}
	
	public int getRoundNumber() {
		return mRoundNumber;
	}
	
	public String getFieldString() {
	    return mString;
	}
	
	public int getTotalStones(int playerId) {
	    return totalStones[playerId];
	}
	
	public int getStonesTaken(int playerId) {
	    return stonesTaken[playerId];
	}
	
	public double getTotalScore(int playerId) {
	    return totalScore[playerId];
	}
}
