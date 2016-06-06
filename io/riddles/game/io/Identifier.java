package io.riddles.game.io;

public interface Identifier {
	<T> T getValue();
	
	String toString();
}
