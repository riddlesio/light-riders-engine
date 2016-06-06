package io.riddles.tron.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.riddles.game.exception.InvalidDataException;
import io.riddles.game.exception.InvalidInputException;
import io.riddles.game.io.AbstractIOProvider;
import io.riddles.game.io.AiGamesIOHandler;
import io.riddles.game.io.IOHandler;
import io.riddles.game.io.IOProvider;
import io.riddles.game.io.IORequest;
import io.riddles.game.io.Identifier;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.transformer.TronIOResponseTransformer;

public class TronIOProvider extends AbstractIOProvider implements IOProvider {
	
	HashMap<PieceColor, Identifier> botList;
	
	public TronIOProvider(IOHandler handler) {
		super(handler);
		
		botList = new HashMap<>();
		List<Identifier> playerIds = handler.getBotIdentifiers();
		
		for (int i = 0; i < playerIds.size(); i++) {
			Identifier id = playerIds.get(i);
			botList.put(PieceColor.values()[i], id);
		}
	}
	
	@Override
	public TronIOResponse execute(IORequest request) throws IOException, InvalidInputException {
		TronIORequest r = (TronIORequest) request;
		if (request.getType() == TronIORequestType.NOOP) {
			return new TronIOResponse(request, TronIOResponseType.NOOP, "noop");
		} else {
			String response = handler.sendRequest(botList.get(r.getColor()), request.toString());
			/* Check request matches with response type */
			
			
			System.out.println("In: " + response);
			
			TronIOResponseTransformer t = new TronIOResponseTransformer();
			return t.transform(request, response);
		}
	}
	

    /* mapping player to color
     */
}
