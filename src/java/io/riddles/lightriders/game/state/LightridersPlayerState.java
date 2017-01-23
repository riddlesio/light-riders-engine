package io.riddles.lightriders.game.state;

import io.riddles.javainterface.game.data.Point;
import io.riddles.javainterface.game.state.AbstractPlayerState;
import io.riddles.lightriders.game.data.*;
import io.riddles.lightriders.game.data.Color;
import io.riddles.lightriders.game.move.LightridersMove;


/**
 * Created by Niko on 23/11/2016.
 */
public class LightridersPlayerState extends AbstractPlayerState {

    int playerId = 0;

    private Point c;
    private boolean alive;
    private io.riddles.lightriders.game.data.Color color;
    private MoveType direction;

    public LightridersPlayerState() {}

    public LightridersPlayerState(LightridersMove move) {
        super(move);
    }


    public LightridersPlayerState clone() {
        LightridersPlayerState psClone = new LightridersPlayerState();
        psClone.setPlayerId(this.playerId);
        return psClone;
    }



    public int getPlayerId() { return this.playerId; }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public LightridersMove getMove() {
        if (this.getMoves().size() > 0) {
            return (LightridersMove)this.getMoves().get(0);
        }
        return null;
    }

    public Point getCoordinate() { return this.c; }

    public void setCoordinate(Point c) {
        this.c = c;
    }

    public Color getColor() { return this.color; }

    public void setColor(Color color) {
        this.color = color;
    }

    public MoveType getDirection() { return this.direction; }

    public void setDirection(MoveType direction) {
        this.direction = direction;
    }

    public String toString() {
        return "PlayerState p" + this.getPlayerId() + " " + this.color + " coord " + this.getCoordinate() + " alive " + this.alive;
    }

    public boolean isAlive() { return this.alive; }
    public void kill() { this.alive = false; }
}
