package io.riddles.tron.visitor;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Field;
import io.riddles.boardgame.model.Piece;
import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.tron.TronPiece;
import io.riddles.tron.TronPiece.PieceColor;
import io.riddles.tron.TronPiece.PieceType;
import io.riddles.tron.TronState;

public class TronStateToJSONVisitor implements Visitor<JSONArray> {
	
	public JSONArray traverse(TronState state) {
		
		return state.accept(this);
	}

	@Override
	public JSONArray visit(Traversible traversible) {
		
		JSONArray result = new JSONArray();
		TronState state = (TronState) traversible;
		
		result.put(boardtoPresentationString(state.getBoard()));
		
		if (state.hasPreviousState()) {
			
			JSONArray intermediate = state.getPreviousState().get().accept(this);
			
			for(int i = 0; i < intermediate.length(); i++) {

				try {
					String stateRepresentation = intermediate.getString(i);
					result.put(stateRepresentation);
				} catch (Exception e) {
					result.put(getEmptyBoardRepresentation(state.getBoard()));
				}
			}
		}
		
		return result;
		
	}
	
	/**
	 * Creates a string with comma separated ints for every cell.
	 * @param args : 
	 * @return : String with comma separated ints for every cell.
	 * Format:		 LSB
	 * 0 0 0 0 0 0 0 0
	 * | | | | | | | |_ Yellow
	 * | | | | | | |___ Green
	 * | | | | | |_____ Cyan
	 * | | | | |_______ Purple
	 * | | | |_________ Wall
	 * | | |___________ Lightcycle
	 * | |_____________ Reserved
	 * |_______________ Reserved
	 */
	private String boardtoPresentationString(Board board) {
		String s = "";
		int counter = 0;
		List<Field> fields = board.getFields();
		
    	for (int i = 0; i < fields.size(); i++) {
			Optional<Piece> p = fields.get(i).getPiece();
			int b = 0;
			
			if(p.isPresent()) {
				Piece piece = p.get();
				if (piece.getColor() == PieceColor.YELLOW) {
					b = b | (1 << 0);
				} else if (piece.getColor() == PieceColor.GREEN) {
					b = b | (1 << 1);
				} else if (piece.getColor() == PieceColor.CYAN) {
					b = b | (1 << 2);
				} else if (piece.getColor() == PieceColor.PURPLE) {
					b = b | (1 << 3);
				}
				
				if (piece.getType() == PieceType.WALL) {
					b = b | (1 << 4);
				} else if (piece.getType() == PieceType.LIGHTCYCLE) {
					b = b | (1 << 5);
				}
			}
			if (counter > 0) s+= ",";
			s += String.valueOf(b);
			counter++;
    	}
		return s;
	}
	
	private String getEmptyBoardRepresentation(Board board) {
		String s = "";
		int counter = 0;
		List<Field> fields = board.getFields();
		
		for (int i = 0; i < fields.size(); i++) {
			if (counter > 0) s+= ",";
			s += "0";
			counter++;
    	}
    	return s;
	}

}