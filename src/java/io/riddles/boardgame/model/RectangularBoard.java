package io.riddles.boardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Board;

/**
 * @author Niko van Meurs <niko@riddles.io>
 */
public final class RectangularBoard extends AbstractModel implements Board {

    protected List<Field> fields;
    protected int width, height;

    /**
     * Board constructor
     * @param fields
     */
    public RectangularBoard(List<Field> fields, int width, int height) {
    	
    	this.width = width; this.height = height;
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
    public RectangularBoard(int width, int height) {
    	
    	this.width = width; this.height = height;
    	List<Field> fields = new ArrayList<Field>();
    	for (int x = 0; x < width; x++) {
    		for (int y = 0; y < height; y++) {
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

        int index = this.width * y + x;
        int fieldSize = fields.size();

        if (index >= fieldSize) {
            throw new IndexOutOfBoundsException("Coordinate out of bounds");
        }

        return fields.get(index);
    }

    public static RectangularBoard of(List<Field> fields, int width, int height) {

        return new RectangularBoard(fields, width, height);
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