/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.lightriders.game.board;

import io.riddles.javainterface.game.data.Board;

import java.awt.*;

/**
 * io.riddles.lightriders.game.board.LightridersBoard
 *
 * [description]
 *
 * @author Joost - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class LightridersBoard extends Board<String> {

    private final String EMPTY = ".";
    private final String BLOCKED = "x";

    public LightridersBoard(int width, int height) {
        super(width, height);
        this.fields = new String[this.width][this.height];

        clear();
    }

    public LightridersBoard(LightridersBoard board) {
        super(board.getWidth(), board.getHeight());
        this.fields = new String[this.width][this.height];

        this.setFieldsFromString(board.toString());
    }

    @Override
    public void clear() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.fields[x][y] = EMPTY;
            }
        }
    }

    @Override
    public String fieldFromString(String string) {
        return string;
    }

    @Override
    public void dump() {
        int maxLength = 0;

        int y;
        for(y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                String cellString = this.fields[x][y] + "";
                int length = cellString.length();
                if(length > maxLength) {
                    maxLength = length;
                }
            }
        }

        for(y = 0; y < this.height; ++y) {
            String line = "";

            for(int x = 0; x < this.width; ++x) {
                String cell = this.fields[x][y] + "";
                line = line + cell;

                for(int i = 0; i <= maxLength - cell.length(); ++i) {
                    line = line + " ";
                }
            }

            System.err.println(line);
        }

        System.err.println();
    }

    public Boolean isEmpty(Point point) {
        return !isOutsideBoard(point) && getFieldAt(point).equals(EMPTY);
    }

    public void setBlocked(Point point) {
        setFieldAt(point, BLOCKED);
    }

    private boolean isOutsideBoard(Point point) {
        return point.x < 0 || point.y < 0 || point.x >= this.width || point.y >= this.height;
    }
}
