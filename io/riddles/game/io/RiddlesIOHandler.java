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

import java.util.Scanner;
import java.io.IOException;

public final class RiddlesIOHandler implements IOHandler {

    private final Scanner scanner;
    
    public RiddlesIOHandler() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Get next message from game wrapper API
     * @return The received message
     * @throws IOException
     */
    public String getNextMessage() throws IOException {
        
        if(scanner.hasNextLine()) {
            String line = "";
            while (line.length() == 0) {
                line = scanner.nextLine().trim();
            }
            return line;
        }
        throw new IOException("No more input.");
    }
    
    /**
     * Waits until expected message is read, all
     * messages received while waiting for expected message
     * are ignored.
     * @param expected Message that is waited on
     */
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
    
    /**
     * Send a message to a given bot, expecting no response
     * @param id Bot to send message to
     * @param message Message to send
     */
    public void sendMessage(int id, String message) {
        sendMessage(String.format("bot %d send %s", id, message));
    }
    
    /**
     * Send a message that will be received by all bots,
     * no response from bots is expected
     * @param message Message to send
     */
    public void broadcastMessage(String message) {
        sendMessage(String.format("bot all send %s", message));
    }
    
    /**
     * Ask the bot something and wait for it to return with
     * an answer
     * @param id Bot to send request to
     * @param request Request to send
     * @return Answer from the bot
     */
    public String sendRequest(int id, String request) {
        sendMessage(String.format("bot %d ask %s", id, request));
        return getResponse(id);
    }
    
    /**
     * Send a warning to given bot, this will not be received
     * by the bot, but will be logged in its dump
     * @param id Bot to send warning to
     * @param warning Warning to send
     */
    public void sendWarning(int id, String warning) {
        sendMessage(String.format("bot %d warning %s", id, warning));
    }
    
    /**
     * Send a message to the game wrapper API
     * @param message Message to send
     */
    public void sendMessage(String message) {
        System.out.println(message);
        System.out.flush();
    }
    
    /**
     * Waits until a response is returned by given
     * bot, only call this after sending a request.
     * All messages received while waiting for response
     * from given bot are ignored.
     * @param id Bot to get response from
     */
    private String getResponse(int id) {
        
        String message = null;
        String identifier = String.format("bot %d ", id);
        while(message == null || !message.startsWith(identifier)) {
            try {
                message = getNextMessage();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        return message.replace(identifier, "");
    }
}