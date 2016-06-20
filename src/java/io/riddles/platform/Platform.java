package io.riddles.platform;

import java.io.IOException;
import java.util.HashMap;

import io.riddles.game.engine.GameEngine;
import io.riddles.game.io.IOHandler;
import io.riddles.tron.game.TronGameEngine;

/**
 * 
 *
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Joost <joost@riddles.io>
 */
public interface Platform {

	public IOHandler getHandler();
	
	public HashMap<String, Object> getConfiguration();
	
	public void setEngine(GameEngine engine);
	
	public void preRun() throws IOException ;
	
	public void run() throws Exception;
	
	public void postRun();
	
	
	
	
}