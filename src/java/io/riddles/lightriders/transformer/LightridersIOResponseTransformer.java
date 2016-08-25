package io.riddles.lightriders.transformer;


import io.riddles.game.exception.InvalidInputException;
import io.riddles.game.io.IORequest;
import io.riddles.lightriders.io.LightridersIOResponse;
import io.riddles.lightriders.io.LightridersIOResponseType;


public class LightridersIOResponseTransformer { /* Doesn't implement Tranformer, yet. */
	
	public LightridersIOResponse transform(IORequest request, String input) throws InvalidInputException  {
		
		/* Possible future implementation: */
		/* Step 1: Tokenize (String) -> Token */
		/* Step 2: Lexer (Token) -> Lexeme */
		/* Step 3: Transformer (Lexeme[]) -> IOResponse */
		
        String[] tokens = input.trim().split(" ");
		if (tokens[0].equals("move")) {
			String direction = tokens[1];
			if (direction.equals("up") || direction.equals("right") || direction.equals("down") || direction.equals("left")) {
				return new LightridersIOResponse(request, LightridersIOResponseType.MOVE, direction);
			}
		} else if (tokens[0].equals("pass")) {
			return new LightridersIOResponse(request, LightridersIOResponseType.PASS, null);
		}
		
		throw new InvalidInputException("Syntax Error.");
	}
}