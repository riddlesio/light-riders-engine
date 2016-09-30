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

    public String toRepresentationString(ArrayList<LightridersPlayer> players) {
        String s = "";
        int counter = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                String c = "0";
                if (!isEmpty(new Point(x,y))) {
                    c = "-1";
                }
                for (LightridersPlayer player : players) {
                    if (player.getCoordinate().equals(new Point(x, y))) c = Integer.toString(player.getId());
                }
                if (counter > 0) s+= ",";
                s += c;
                counter++;
            }
        }
        return s;
    }

    public Boolean isEmpty(Point c) {
        return !(c.x < 0 || c.y < 0 || c.x >= this.width || c.y >= this.height) &&
                (field[c.x][c.y].equals("."));
    }
}
