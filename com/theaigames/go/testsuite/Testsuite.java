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

package com.theaigames.go.testsuite;

import com.theaigames.go.field.Field;

public class Testsuite {
	
	public void dbgTestKoRule(Field field) {
		field.addMove(0, 0, 1);
		field.addMove(1, 0, 1);
		field.addMove(2, 0, 1);
		field.addMove(3, 0, 1);
		field.addMove(4, 0, 1);
		field.addMove(5, 0, 1);
		field.addMove(6, 0, 1);
		field.addMove(6, 1, 1);
		field.addMove(6, 2, 1);
		
		field.addMove(0, 1, 2);
		field.addMove(1, 1, 2);
		field.addMove(2, 1, 2);
		field.addMove(3, 1, 2);
		field.addMove(4, 1, 2);
		field.addMove(5, 1, 2);
		field.addMove(5, 2, 2);
		
		field.addMove(0, 5, 2);
		field.addMove(1, 5, 2);
		field.addMove(2, 5, 2);
		field.addMove(3, 5, 2);
		field.addMove(4, 5, 2);
		field.addMove(5, 5, 2);
		field.addMove(6, 5, 2);
		field.addMove(6, 4, 2);
		field.addMove(6, 3, 2);
		
		field.addMove(0, 4, 1);
		field.addMove(1, 4, 1);
		field.addMove(2, 4, 1);
		field.addMove(3, 4, 1);
		field.addMove(4, 4, 1);
		field.addMove(5, 4, 1);
		field.addMove(5, 3, 1);
		
		field.addMove(1, 2, 2);
		field.addMove(1, 3, 1);
		field.addMove(3, 2, 2);
		field.addMove(3, 3, 1);
		
		field.addMove(0, 2, 1);
		field.addMove(2, 2, 1);
		field.addMove(4, 3, 2);
		field.addMove(0, 3, 2);
		field.addMove(4, 2, 1);
		field.addMove(2, 3, 2);
		field.addMove(0, 2, 1);
		field.addMove(4, 3, 2);
		field.addMove(2, 2, 1);

		System.out.println(field.getLastError());

		/* TODO: Assert field state */
	}
	
	public void dbgTestCapture(Field field) {
		field.addMove(18, 1, 2);
		field.addMove(18, 2, 2);
		
		field.addMove(18, 0, 1);
		field.addMove(17, 1, 1);
		field.addMove(17, 2, 1);
		field.addMove(18, 3, 1);
		/* TODO: Assert field state */
	}
	
	public void dbgTestSuicideRule(Field field) {
		/* Test suicide with one stone */
		field.addMove(8, 0, 1);
		field.addMove(7, 1, 1);
		field.addMove(8, 2, 1);
		field.addMove(9, 1, 1);
		field.addMove(8, 1, 2);
		System.out.println(field.getLastError());
		/* TODO: Assert field state */
		
		/* Test suicide with a group of stones */
		field.addMove(8, 8, 1);
		field.addMove(9, 8, 1);
		field.addMove(7, 9, 1);
		field.addMove(10, 9, 1);	
		field.addMove(8, 10, 1);
		field.addMove(10, 10, 1);
		field.addMove(9, 11, 1);

		field.addMove(8, 9, 2);
		field.addMove(9, 9, 2);
		field.addMove(9, 10, 2); /* Triggers a violation of suicide rule */
		System.out.println(field.getLastError());
		
		field.addMove(9, 10, 1); /* Should work fine */
		System.out.println(field.getLastError());
		/* TODO: Assert field state */
		
		field.addMove(1, 12, 1);
		field.addMove(2, 12, 1);
		field.addMove(3, 12, 1);
		
		field.addMove(0, 13, 1);
		field.addMove(1, 13, 2);
		field.addMove(2, 13, 2);
		field.addMove(3, 13, 2);
		field.addMove(4, 13, 1);
		
		field.addMove(0, 14, 1);
		field.addMove(1, 14, 2);
		field.addMove(3, 14, 2);
		field.addMove(4, 14, 1);
		
		field.addMove(0, 15, 1);
		field.addMove(1, 15, 2);
		field.addMove(2, 15, 2);
		field.addMove(3, 15, 2);
		field.addMove(4, 15, 1);
		
		field.addMove(1, 16, 1);
		field.addMove(2, 16, 1);
		field.addMove(3, 16, 1);
		
		field.addMove(2, 14, 1);
	}
	
	public void dbgTestScore(Field field) {
		field.addMove(6, 6, 2);
		field.addMove(7, 6, 2);
		field.addMove(8, 6, 2);
		
		field.addMove(8, 7, 2);
		field.addMove(8, 8, 2);
		field.addMove(8, 9, 2);
		
		field.addMove(6, 7, 1);
		field.addMove(6, 8, 1);
		field.addMove(6, 9, 1);

		field.addMove(6, 10, 1);
		field.addMove(7, 10, 1);
		field.addMove(8, 10, 1);
		
		field.addMove(7, 9, 1);

		/* TODO: Assert field state */
	}
}
