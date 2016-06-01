package io.riddles.tron.io;

import java.io.IOException;

import io.riddles.game.io.AbstractIOProvider;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.IORequest;
import io.riddles.tron.visitor.TronDirectionDeserializer;

public class TronIOProvider extends AbstractIOProvider implements IOProvider {
	
	public TronIOProvider(IOHandler handler) {
		super(handler);
	}
	
	public TronIOResponse execute(IORequest request) throws IOException {
		
		String r = handler.sendRequest(0, request.toString());
		System.out.println("In: " + r);
		String[] parts = r.split(" ");
		
		if (parts[0].equals("turn_direction")) {
			String direction = parts[1];
			if (direction.equals("up") || direction.equals("right") || direction.equals("down") || direction.equals("left")) {
				return new TronIOResponse(request, TronIOResponseType.MOVE, direction);
			}
		} else if (parts[0].equals("pass")) {
			return new TronIOResponse(request, TronIOResponseType.PASS, null);
		}
		throw new IOException("Invalid input");
	}

    /* mapping player to color
     */
}
