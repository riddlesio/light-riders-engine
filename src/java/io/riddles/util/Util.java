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

package io.riddles.util;

import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Coordinate;
import io.riddles.boardgame.model.Direction;
import io.riddles.boardgame.model.Piece;

public final class Util {

	private Util() {
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}
	
	public static String directionToString(Direction direction) {
		switch (direction) {
			case UP:
				return "up";
			case RIGHT:
				return "right";
			case DOWN:
				return "down";
			case LEFT:
				return "left";
			default:
				return "unknown";
		}
	}	
	
	public static Direction stringToDirection(String direction) {
		switch (direction) {
			case "up":
				return Direction.UP;
			case "right":
				return Direction.RIGHT;
			case "down":
				return Direction.DOWN;
			case "left":
				return Direction.LEFT;
		}
		return null;
	}
	
	public static void dumpBoard(Board b) {
		for (int y = 0; y < b.size(); y++) {
			for (int x = 0; x < b.size(); x++) {
				Optional<Piece> p = b.getFieldAt(new Coordinate(x,y)).getPiece();
				if(p.isPresent()) {
					System.out.print(p.get().toString());
				} else {
					System.out.print("--");
				}
			}
			System.out.println("");
		}
	}
}