package io.riddles.lightriders.game.board;

import io.riddles.javainterface.game.data.Point;
import io.riddles.lightriders.game.player.LightridersPlayer;
import io.riddles.lightriders.game.state.LightridersState;

import java.util.ArrayList;

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

    public Boolean isEmpty(Point c) {
        if (c.x < 0 || c.y < 0 || c.x >= this.width || c.y >= this.height) {
            return false;
        }
        return (field[c.x][c.y].equals("."));
    }

    public LightridersBoard clone() {
        LightridersBoard cBoard = new LightridersBoard(this.width, this.height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x, y);
                cBoard.setFieldAt(p, getFieldAt(p));
            }
        }
        return cBoard;
    }
}
