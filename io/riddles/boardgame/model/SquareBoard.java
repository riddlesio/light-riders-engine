package io.riddles.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import io.riddles.boardgame.model.Board;

/**
 * @author Niko van Meurs <niko@riddles.io>
 */
public final class SquareBoard extends AbstractModel implements Board {

    private List<Field> fields;

    /**
     * Board constructor
     * @param fields
     */
    public SquareBoard(List<Field> fields) {

        this.fields = fields;
    }
    
    /**
     * Board constructor for board filled with fields
     * @param fields
     */
    public SquareBoard(int width, int height) {
    	List<Field> fields = new ArrayList<Field>();
    	for (int x = 0; x < width; x++) {
    		for (int y = 0; y < height; y++) {
    			fields.add(new Field(null));
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
}