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

package io.riddles.tron.moves;

import io.riddles.tron.field.Field;
import io.riddles.tron.player.Player;


public class MoveResult {
	private int mRoundNumber;
	private Player mPlayer;
	private Move mMove;
	private String mString;
	
	private boolean[] mPlayerAlive;
	
	public MoveResult(Player player, Player opponent, Move move, int roundNumber, Field field) {
		mPlayer = player;
		mMove = move;
		mRoundNumber = roundNumber;
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
	
	public void setPlayerStates(boolean[] playerAlive) {
		for(int i = 0; i< playerAlive.length; i++) {
			mPlayerAlive[i] = playerAlive[i];
		}
	}
}
