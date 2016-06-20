package io.riddles.tron.visitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.tron.TronState;

public class TronStateToJSONVisitor implements Visitor<TronState> {  
	JSONArray a = new JSONArray();

	@Override
	public TronState visit(Traversible traversible) {
		TronState s = (TronState) traversible;
		if (traversible instanceof TronState) {
	        while (s.hasPreviousState()) { 
	            System.out.println(s.getBoard());
	        	s = s.getPreviousState().get();
	        } 			
		}
		return s;
		
	}
	
	public void addJSONObject(JSONObject j) {
		a.put(j);
	}
	
	
	

}