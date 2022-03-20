package com.rucoyoffline.game.PathfindingTools;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class Connector implements Connection<Tile>{
    Tile fromTile;
    Tile toTile;
    float cost;

    public Connector(Tile fromTile, Tile toTile){
        this.fromTile = fromTile;
        this.toTile = toTile;
        cost = Vector2.dst(fromTile.x, fromTile.y, toTile.x, toTile.y);
    }

    @Override
    public float getCost(){
        return cost;
    }

    @Override
    public Tile getFromNode(){
        return fromTile;
    }

    @Override
    public Tile getToNode(){
        return toTile;
    }

}
