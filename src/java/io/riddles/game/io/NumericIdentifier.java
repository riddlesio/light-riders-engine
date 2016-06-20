package io.riddles.game.io;

public class NumericIdentifier implements Identifier {
	
	private Integer value;
	
	public NumericIdentifier(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}
	
	public String toString() {
		return value.toString();
	}
}
