package io.riddles.lightriders.game.board;

import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.state.LightridersState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by joost on 7/11/16.
 */
public class LightridersBoard extends Board {

    public LightridersBoard(int width, int height) {
        super(width, height);
    }
    public LightridersBoard(int width, int height, String fieldLayout) {
        super(width, height, fieldLayout);
    }

    public void dump(ArrayList<LightridersPlayer> players, LightridersState state) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                String s = field[x][y];
                System.out.print(s);
            }
            System.out.println();
        }
    }

    public void clear() {
        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                field[x][y] = ".";
    }

    /**
     * Creates a string with comma separated ints for every cell.
     * @param :
     * @return : String with comma separated ints for every cell.
     * Format:		 LSB
     * 0 0 0 0 0 0 0 0
     * | | | | | | | |_ Yellow
     * | | | | | | |___ Green
     * | | | | | |_____ Cyan
     * | | | | |_______ Purple
     * | | | |_________ Wall
     * | | |___________ Lightcycle
     * | |_____________ Reserved
     * |_______________ Reserved
     */
    public String toRepresentationString(ArrayList<LightridersPlayer> players) {
        String s = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int b = 0;
                if (field[x][y].equals("Y")) {
                    b = b | (1 << 0);
                } else if (field[x][y].equals("G")) {
                    b = b | (1 << 1);
                } else if (field[x][y].equals("C")) {
                    b = b | (1 << 2);
                } else if (field[x][y].equals("P")) {
                    b = b | (1 << 3);
                }

                 for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getCoordinate().x == x && players.get(i).getCoordinate().y == y) {
                        b = b | (1 << 5);
                    }
                }
                if (field[x][y] != ".") {
                    b = b | (1 << 4);
                }
                if (counter > 0) s+= ",";
                s += String.valueOf(b);
                counter++;
            }
        }
        return s;
    }

    /* isEmpty doesn't check for players or enemies! */
    public Boolean isEmpty(Point c) {
        if (c.x < 0 || c.y < 0 || c.x >= this.width || c.y >= this.height) {
            return false;
        }
        return (field[c.x][c.y].equals("."));
    }


}