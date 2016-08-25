package io.riddles.lightriders.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.riddles.game.exception.InvalidInputException;
import io.riddles.game.io.AbstractIOProvider;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.Identifier;
import io.riddles.lightriders.LightridersPiece.PieceColor;
import io.riddles.lightriders.transformer.LightridersIOResponseTransformer;

public class LightridersIOProvider extends AbstractIOProvider implements IOProvider {
	
	HashMap<PieceColor, Identifier> botList;
	
	public LightridersIOProvider(IOHandler handler) {
		super(handler);
		
		botList = new HashMap<>();
		List<Identifier> playerIds = handler.getBotIdentifiers();
		
		for (int i = 0; i < playerIds.size(); i++) {
			Identifier id = playerIds.get(i);
			botList.put(PieceColor.values()[i], id);
		}
	}
	
	@Override
	public LightridersIOResponse execute(IORequest request) throws IOException, InvalidInputException {
		LightridersIORequest r = (LightridersIORequest) request;
		if (request.getType() == LightridersIORequestType.NOOP) {
			return new LightridersIOResponse(request, LightridersIOResponseType.NOOP, "noop");
		} else {
			System.out.println("sendRequest color: " + r.getColor());
			String response = handler.sendRequest(botList.get(r.getColor()), request.toString());
			/* Check request matches with response type */
			
			
			System.out.println("In: " + response);
			
			LightridersIOResponseTransformer t = new LightridersIOResponseTransformer();
			return t.transform(request, response);
		}
	}
}
