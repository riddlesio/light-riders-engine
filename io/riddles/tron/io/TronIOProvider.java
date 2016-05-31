package io.riddles.tron.io;

import java.io.IOException;

import io.riddles.game.io.AbstractIOProvider;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.IORequest;

public class TronIOProvider extends AbstractIOProvider implements IOProvider {
	
	public TronIOProvider(IOHandler handler) {
		/* mapping player -> handler */
		super(handler);
	}
	
	public TronIOResponse execute(IORequest request) throws IOException {
		
		String s = handler.ask(request.toString());
		System.out.println("In: " + s);
		//handler.writeToBot
		return new TronIOResponse(request, TronIOResponseType.MOVE, "up");
	}

    /* mapping player to color
     * request to string
     * figure out response type 
     */
}
