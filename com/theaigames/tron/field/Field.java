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

package com.theaigames.tron.field;


import java.util.List;
import java.util.ArrayList;


import com.theaigames.tron.player.Player;

public class Field {
	
	private int[][] mBoard;
	
	private int mCols = 0, mRows = 0;
	private String mLastError = "";
	public static final int DIR_UP = 0;
	public static final int DIR_RIGHT = 90;
	public static final int DIR_DOWN = 180;
	public static final int DIR_LEFT = 270;
	
	private int[] mPlayerDirections;

			
	public Field(int width, int height, List<Player> players) {
		mCols = width;
		mRows = height;
		mBoard = new int[mCols][mRows];
		mPlayerDirections = new int[players.size() + 1];
		clearBoard();
		addMaze();
		for (Player player : players) {
			mPlayerDirections[player.getId()] = 0;
			mBoard[player.getX()][player.getY()] = player.getId();
		}
	}
	
	public void clearBoard() {
		for (int x = 0; x < mCols; x++) {
			for (int y = 0; y < mRows; y++) {
				mBoard[x][y] = 0;
			}
		}
	}
	
	public void addMaze() {
		for (int i = 0; i < mCols; i++) {
			mBoard[i][0] = 5;
			mBoard[i][mRows-1] = 5;
			mBoard[0][i] = 5;
			mBoard[mCols-1][i] = 5;
		}
	}
	
	/**
	 * Dumps the board to stdout
	 * @param args : 
	 * @return : 
	 */
	public void dumpBoard() {
		System.out.print("\n\n");
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				System.out.print(mBoard[x][y]);
				if (x < mCols-1) {
					String s = ", ";
					System.out.print(s);
				}
			}
			System.out.print("\n");
		}
	}

	/**
	 * Returns reason why addMove returns false
	 * @param args : 
	 * @return : reason why addMove returns false
	 */
	public String getLastError() {
		return mLastError;
	}
	
	public void setLastError(String error) {
	    mLastError = error;
	}
	
	@Override
	/**
	 * Creates comma separated String with player ids for the microboards.
	 * @param args : 
	 * @return : String with player names for every cell, or 'empty' when cell is empty.
	 */
	public String toString() {
		String r = "";
		int counter = 0;
		for (int y = 0; y < mRows; y++) {
			for (int x = 0; x < mCols; x++) {
				if (counter > 0) {
					r += ",";
				}
				int d = mBoard[x][y];
				r += d;
				counter++;
			}
		}
		return r;
	}
	
	

	public int getNrColumns() {
		return mCols;
	}
	
	public int getNrRows() {
		return mRows;
	}
	
	/**
	 * Set direction of Player player. 
	 * @param args : Player involved, int direction
	 * @return : 
	 */
	public void setPlayerDirection(Player player, int direction) {
		mPlayerDirections[player.getId()] = direction;
	}
	
	/**
	 * Updates the board one tick, moving the player involved one step forward. 
	 * @param args : Player involved, int direction
	 * @return : true if player moved okay, false if player crashed
	 */
	public boolean update(Player player) {
		boolean result = false;
		int x = player.getX(), y = player.getY();
		switch (player.getDirection()) {
			case DIR_UP:
				if (y > 0) {
					if (mBoard[x][y-1] == 0) {
						mBoard[x][y-1] = player.getId();
						player.setY(y-1);
						result = true;
					}
				}
				break;
			case DIR_RIGHT:
				if (x < mCols - 1) {
					if (mBoard[x+1][y] == 0) {
						mBoard[x+1][y] = player.getId();
						player.setX(x+1);
						result = true;
					}
				}
				break;
			case DIR_DOWN:
				if (y < mRows - 1) {
					if (mBoard[x][y+1] == 0) {
						mBoard[x][y+1] = player.getId();
						player.setY(y+1);
						result = true;
					}
				}
				break;
			case DIR_LEFT:
				if (x > 0) {
					if (mBoard[x-1][y] == 0) {
						mBoard[x-1][y] = player.getId();
						player.setX(x-1);
						result = true;
					}
				}
				break;
		}
		return result;
	}
}

