package io.riddles.boardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.riddles.boardgame.model.Board;
import io.riddles.tron.TronPiece;
import io.riddles.tron.TronPiece.PieceColor;

/**
 * @author Niko van Meurs <niko@riddles.io>
 */
public class SquareBoard extends AbstractModel implements Board {

    protected List<Field> fields;

    /**
     * Board constructor
     * @param fields
     */
    public SquareBoard(List<Field> fields) {

        this.fields = fields;
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

    public void setPieceAt(Coordinate coordinate, Piece piece) {
    	
    	int x = coordinate.getX();
        int y = coordinate.getY();
        int boardSize = this.size();

        int index = boardSize * y + x;
        int fieldSize = fields.size();
        if (index >= fieldSize) {
            throw new IndexOutOfBoundsException("Coordinate out of bounds");
        }
        fields.get(index).setPiece(Optional.of(piece));
        
    }
    
    public int size() {

        return (int) Math.sqrt(fields.size());
    }

    public static SquareBoard of(List<Field> fields) {

        return new SquareBoard(fields);
    }

	@Override
	public void setFieldAt(Coordinate coordinate, Piece piece) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		
	}
}