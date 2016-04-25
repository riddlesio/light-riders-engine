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

import com.theaigames.tron.field.Field;

public final class Util {

	private Util() {
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}
	
	public static String directionToString(int direction) {
		switch (direction) {
			case Field.DIR_UP:
				return "up";
			case Field.DIR_RIGHT:
				return "right";
			case Field.DIR_DOWN:
				return "down";
			case Field.DIR_LEFT:
				return "left";
		}
		return "unknown";
	}	
	
	public static int directionToInt(String direction) {
		switch (direction) {
			case "up":
				return Field.DIR_UP;
			case "right":
				return Field.DIR_RIGHT;
			case "down":
				return Field.DIR_DOWN;
			case "left":
				return Field.DIR_LEFT;
		}
		return -1;
	}
}