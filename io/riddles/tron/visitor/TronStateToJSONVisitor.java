package io.riddles.tron.visitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;
import io.riddles.tron.TronState;

public class TronStateToJSONVisitor implements Visitor<String> {  
	JSONArray a = new JSONArray();

	@Override
	public String visit(Traversible traversible) {
		String r = traversible.accept(this);
		
		return r;
	}
	
//   public void visit(TronState state) {
//	   
//   }
	public void addJSONObject(JSONObject j) {
		a.put(j);
	}

}