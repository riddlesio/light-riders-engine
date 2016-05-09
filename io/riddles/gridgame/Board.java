package io.riddles.gridgame;

import java.util.List;


/**
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Joost
 */

public interface Board {
	int getField(int x, int y);
	void setField(int x, int y, int f);
	int getWidth();
	int getHeight();
	
}