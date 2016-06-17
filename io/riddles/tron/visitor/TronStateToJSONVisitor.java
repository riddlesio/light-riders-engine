package io.riddles.tron.visitor;

import org.json.JSONException;
import org.json.JSONObject;

import io.riddles.game.model.Traversible;
import io.riddles.game.model.Visitor;

public class TronStateToJSONVisitor implements Visitor<JSONObject> {  

	@Override
	public JSONObject visit(Traversible traversible) {
		JSONObject j = traversible.accept(this);
		
		JSONObject states = new JSONObject();
		try {
			states.put("state", j);
		} catch (JSONException e) {
			
		}
		JSONObject r = new JSONObject();
		r.put("states", states);

		return r;
	}
}