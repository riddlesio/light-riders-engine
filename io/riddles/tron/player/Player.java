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

package io.riddles.tron.player;

import io.riddles.boardgame.model.Direction;
import io.riddles.engine.io.IOPlayer;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.player.AbstractPlayer;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.field.Field;


public class Player extends AbstractPlayer {
	
	int mId;
	String mLastMove;
	Direction mDirection;
	int mX, mY;
	boolean mAlive;
	PieceColor mPieceColor;
	
	public Player(String name, long maxTimeBank, long timePerMove, int id) {
		super(name, handler, maxTimeBank, timePerMove);
		mId = id;
		mLastMove = "Null";
		mAlive = true;
	}

	public int getId() {
		return mId;
	}
	
	public void setLastMove(String lastMove) {
		mLastMove = lastMove;
	}
	
	public String getLastMove() {
		return mLastMove;
	}

	public void setDirection(Direction d) {
		mDirection = d;
	}
	
	public Direction getDirection() {
		return mDirection;
	}
	
	public void setX(int x) {
		mX = x;
	}
	
	public void setY(int y) {
		mY = y;
	}
	
	public int getX() {
		return mX;
	}
	
	public int getY() {
		return mY;
	}
	
	public void die() {
		mAlive = false;
	}
	
	public boolean isAlive() {
		return mAlive;
	}
	
	public PieceColor getPieceColor() {
		return mPieceColor;
	}
	
	public void setPieceColor(PieceColor pieceColor) {
		mPieceColor = pieceColor;
	}
}
