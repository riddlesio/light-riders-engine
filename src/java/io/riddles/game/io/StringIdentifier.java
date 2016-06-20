package io.riddles.game.io;

public class StringIdentifier implements Identifier {
	
	private String value;
	
	public StringIdentifier(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return "".concat(value);
	}
}
