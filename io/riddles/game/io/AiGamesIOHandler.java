// Copyright 2016 riddles.io (developers@riddles.io)

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

package io.riddles.game.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import io.riddles.tron.player.Player;

/**
 * IOHandler class
 * 
 * Extends IOWrapper class by adding stuff specifically
 * for engine processes
 * 
 * @author Sid Mijnders <sid@riddles.io>, Jim van Eeden <jim@starapple.nl>
 */
public class AiGamesIOHandler implements IOHandler {
    /* IOHandler manages all processes */
	/* map players to processes */
	
    private final long TIMEOUT = 2000; // 2 seconds
    private ArrayList<AiGamesBot> bots;
    public Queue<String> inputQueue;
    public String response;

    public AiGamesIOHandler(ArrayList<Player> players, ArrayList<Process> processes) {
    	int counter = 0;
    	bots = new ArrayList<AiGamesBot>();
		for (Player player : players) {
    		AiGamesBot bot = new AiGamesBot(processes.get(counter));
    		bot.setPlayerId(player.getId());
    		bots.add(bot);
    		counter++;
    	}
    }
    
    public AiGamesIOHandler() {
    	bots = new ArrayList<AiGamesBot>();
    }
    
    public void addPlayerProcess(Player player, Process process) {
		AiGamesBot bot = new AiGamesBot(process);
		bot.setPlayerId(player.getId());
		bot.run();
		bots.add(bot);
    }
    
    /**
     * Handles engine response time out
     * @param timeout Time before timeout
     * @return Empty string
     */
    protected String handleResponseTimeout(long timeout) {
        System.err.println(String.format("Engine took too long! (%dms)", this.TIMEOUT));
        return "";
    }

	@Override
	public String getNextMessage() throws IOException {
        long timeStart = System.currentTimeMillis();
        String message = this.inputQueue.poll();
        
        while (message == null) {
            long timeNow = System.currentTimeMillis();
            long timeElapsed = timeNow - timeStart;
            
            if (timeElapsed >= this.TIMEOUT) {
                return handleResponseTimeout(this.TIMEOUT);
            }
            
            try { 
                Thread.sleep(2);
            } catch (InterruptedException e) {}
            
            message = this.inputQueue.poll();
        }

        this.response = null;
        return message;
	}

	@Override
	public void waitForMessage(String expected) {
        String message = null;
        while(!expected.equals(message)) {
            try {
                message = getNextMessage();
                try { Thread.sleep(2); } catch (InterruptedException e) {}
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
	}

	@Override
	public void sendMessage(int id, String message) {
		AiGamesBot b = getBotWithPlayerId(id);
		b.write(message);
	}

	@Override
	public void broadcastMessage(String message) {
		for (AiGamesBot bot : bots) {
			bot.write(message);
		}
	}

	@Override
	public String sendRequest(int id, String request) throws IOException {
		AiGamesBot b = getBotWithPlayerId(id);
		return b.ask(request, TIMEOUT);
	}

	@Override
	public void sendWarning(int id, String warning) {
		// TODO Auto-generated method stub
		
	}
	
	private AiGamesBot getBotWithPlayerId(int id) {
		for (AiGamesBot bot : bots) {
    		if (bot.getPlayerId() == id)
    			return bot;
    	}
    		return null;
	}
}