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

package com.theaigames.util;

public final class Util {

	private Util() {
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}
	
	/**
	 * Compares two boards
	 * @param args : int[][] b1, int[][] b2
	 * @return : true if boards are equal
	 */
	public static Boolean compareBoards(int[][] b1, int[][] b2) {
		for (int x = 0; x < b1.length; x++) {
			for (int y = 0; y < b1[0].length; y++) {
				int v1 = b1[x][y];
				int v2 = b2[x][y];
				if (v1 < 0) v1 = 0;
				if (v2 < 0) v2 = 0;
				if (v1 != v2) return false;
			}
		}
		return true;
	}
	
	/**
	 * Dumps a board to stdout
	 * @param args : 
	 * @return : 
	 */
	public static void dumpBoard(int[][] board, String label) {
		System.out.print("\n\ndump '" + label + " ':\n");
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				System.out.print(board[x][y]);
				if (x < board[0].length-1) {
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
	}
}