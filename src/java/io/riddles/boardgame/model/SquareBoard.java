package io.riddles.boardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Board;

/**
 * @author Niko van Meurs <niko@riddles.io>
 */
public final class SquareBoard extends AbstractModel implements Board {

    protected List<Field> fields;

    /**
     * Board constructor
     * @param fields
     */
    public SquareBoard(List<Field> fields) {
    	this.fields = new ArrayList<Field>();
    	for (int i = 0; i < fields.size(); i++) {
    		Field f = new Field(fields.get(i).getPiece());
    		
    		this.fields.add(f);
    	}
       // this.fields = fields;
    }
    
    /**
     * Board constructor for board filled with empty fields
     * @param fields
     */
    public SquareBoard(int size) {
    	List<Field> fields = new ArrayList<Field>();
    	for (int x = 0; x < size; x++) {
    		for (int y = 0; y < size; y++) {
    			fields.add(new Field(Optional.empty()));
    		}
    	}
        this.fields = fields;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public Field getFieldAt(Coordinate coordinate) throws IndexOutOfBoundsException {

        int x = coordinate.getX();
        int y = coordinate.getY();
        int boardSize = this.size();

        int index = boardSize * y + x;
        int fieldSize = fields.size();

        if (index >= fieldSize) {
            throw new IndexOutOfBoundsException("Coordinate out of bounds");
        }

        return fields.get(index);
    }
    
    public int size() {

        return (int) Math.sqrt(fields.size());
    }

    public static SquareBoard of(List<Field> fields) {

        return new SquareBoard(fields);
    }
    
    public String toString() {
    	String s = "";
    	for (int i = 0; i < fields.size(); i++) {
			Optional<Piece> p = fields.get(i).getPiece();
			if(p.isPresent()) {
				s += (p.get().toString());
			} else {
				s += "0";
			}
    	}
    	return s;
    }
}