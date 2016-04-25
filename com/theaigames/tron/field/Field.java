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

import java.util.ArrayList;
import java.util.List;

import com.theaigames.tron.player.Player;
import com.theaigames.util.Util;

public class Field {
	
	private int[][] mBoard;
	
	private int mCols = 0, mRows = 0;
	private String mLastError = "";
	public static final int DIR_UP = 0;
	public static final int DIR_RIGHT = 90;
	public static final int DIR_DOWN = 180;
	public static final int DIR_LEFT = 270;
	
	public Field(int width, int height, List<Player> players) {
		mCols = width;
		mRows = height;
		mBoard = new int[mCols][mRows];
		clearBoard();
	}
	
	public void clearBoard() {
		for (int x = 0; x < mCols; x++) {
			for (int y = 0; y < mRows; y++) {
				mBoard[x][y] = 0;
			}
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
				r += mBoard[x][y];
				counter++;
			}
		}
//		System.out.println(r);
		return r;
	}
	
	

	public int getNrColumns() {
		return mCols;
	}
	
	public int getNrRows() {
		return mRows;
	}
}

