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

import com.theaigames.game.moves.AbstractMove;
import com.theaigames.game.player.AbstractPlayer;

public class Move extends AbstractMove {

	private int mColumn = 0, mRow = 0;
	private String mAction = "move";
	
	public Move(AbstractPlayer player) {
		super(player);
	}
	
	/**
	 * @param column : Sets the column of a move
	 */
	public void setMove(String move, int column, int row) {
		this.mColumn = column;
		this.mRow = row;
		this.mAction = move;
	}
	
	/**
	 * @return : Column of move
	 */
	public int getColumn() {
		return mColumn;
	}

	/**
	 * @return : Row of move
	 */
	public int getRow() {
		return mRow;
	}

	/**
	 * @return : Action
	 */
	public String getAction() {
		return mAction;
	}
}