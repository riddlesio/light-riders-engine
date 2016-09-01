package io.riddles.lightriders.game.board;

import java.awt.*;

/**
 * io.riddles.bookinggame.game.board.Board - Created on 29-6-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class Board {
    protected String[][] field;
    protected int width = 20;
    protected int height = 11;


    protected Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new String[width][height];
    }

    protected Board(int width, int height, String fieldLayout) {
        this.width = width;
        this.height = height;
        this.field = new String[width][height];

        String[] split = fieldLayout.split(",");
        int x = 0;
        int y = 0;

        for (String value : split) {
            this.field[x][y] = value;
            if (++x == width) {
                x = 0;
                y++;
            }
        }
    }

    protected Board(int width, int height, String[][] field) {
        this.width = width;
        this.height = height;
        this.field = field;
    }

    public String toString() {
        String s = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (counter > 0) s += ",";
                s += this.field[x][y];
                counter ++;
            }
        }
        return s;
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }


    public String getFieldAt(Point c) {
        return field[c.x][c.y];
    }

    public void setFieldAt(Point c, String s) {
        field[c.x][c.y] = s;
    }

    public void dump() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                System.out.print(field[x][y]);
            }
            System.out.println();
        }
    }


    public int getNrAvailableFields() {
        int availableFields = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if(field[x][y].equals(".")) {
                    availableFields++;
                }
            }
        }
        return availableFields;
    }
}