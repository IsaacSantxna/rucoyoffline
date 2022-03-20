package com.rucoyoffline.game.PathfindingTools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Tile {
    public float x;
    public float y;
    int index;

    public Tile(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
