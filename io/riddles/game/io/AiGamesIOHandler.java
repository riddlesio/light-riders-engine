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

    public AiGamesIOHandler(Player[] players, Process[] processes) {
    	int counter = 0;
		for (Player player : players) {
    		AiGamesBot bot = new AiGamesBot(processes[counter]);
    		bots.add(bot);
    		bot.setPlayerId(player.getId());
    		counter++;
    	}
    }

    /**
     * Send line to engine
     * @param line Line to send
     * @return True if write was successful, false otherwise
     */
    public boolean send(String message) throws IOException {
        return write(message);
    }
    
    /**
     * Send line to engine and waits for response
     * @param line Line to output
     * @param timeout Time before timeout
     * @return Engine's response
     * @throws IOException
     */
    public String ask(String message) throws IOException {
        return super.ask(message, this.TIMEOUT);
    }
    
    /**
     * Waits until engine returns a response and returns it
     * @return Engine's response, returns and empty string when there is no response
     */
    public String getResponse() {
        return super.getResponse(this.TIMEOUT);
    }
    
    
    /**
     * Shuts down the engine
     */
    public void finish() {
        super.finish();
        System.out.println("Engine shut down.");
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
    
    /**
     * Sends the bot IDs to the engine
     * @param bots All the bots for this game
     * @return False if write failed, true otherwise
     */
    public boolean sendPlayers(ArrayList<Player> players) {
        StringBuilder message = new StringBuilder();
        message.append("bot_ids ");
        String connector = "";
        for (int i=0; i < players.size(); i++) {
            message.append(String.format("%s%d", connector, i));
            connector = ",";
        }
        return write(message.toString());
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
		
        sendMessage(String.format("bot %d send %s", id, message));
	}

	@Override
	public void broadcastMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String sendRequest(int id, String request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendWarning(int id, String warning) {
		// TODO Auto-generated method stub
		
	}
}