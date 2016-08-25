package io.riddles.game.io;

import java.io.IOException;
import java.util.List;

public interface IOHandler {
	
	/**
     * Get next message from game wrapper API
     * @return The received message
     * @throws IOException
     */
	public String getNextMessage() throws IOException;
	/**
     * Waits until expected message is read, all
     * messages received while waiting for expected message
     * are ignored.
     * @param expected Message that is waited on
     */
    public void waitForMessage(String expected);
    /**
     * Send a message to a given bot, expecting no response
     * @param id Bot to send message to
     * @param message Message to send
     */
    public void sendMessage(Identifier id, String message);
    /**
     * Send a message that will be received by all bots,
     * no response from bots is expected
     * @param message Message to send
     */
    public void broadcastMessage(String message);
    /**
     * Ask the bot something and wait for it to return with
     * an answer
     * @param id Bot to send request to
     * @param request Request to send
     * @return Answer from the bot
     */
    public String sendRequest(Identifier id, String request) throws IOException;
    /**
     * Send a warning to given bot, this will not be received
     * by the bot, but will be logged in its dump
     * @param id Bot to send warning to
     * @param warning Warning to send
     */
    public void sendWarning(Identifier id, String warning);
    
    
    public List<Identifier> getBotIdentifiers();
    

}
