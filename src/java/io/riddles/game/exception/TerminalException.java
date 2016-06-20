package io.riddles.game.exception;

import io.riddles.game.model.Traversible;

public class TerminalException extends Exception {
	
	Traversible traversible;
	String serializedData;
	
	public TerminalException(String message, Traversible traversible) {
		super(message);
		this.traversible = traversible;
	}
	
	public Traversible getTraversible() {
		return traversible;
	}
	
	public void setSerializedData(String value) {
		serializedData = value;
	}
	
	public String getSerializedData() {
		return serializedData;
	}
}
