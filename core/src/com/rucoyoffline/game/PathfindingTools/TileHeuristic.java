package com.rucoyoffline.game.PathfindingTools;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class TileHeuristic implements Heuristic<Tile>{

    @Override
    public float estimate(Tile currentTile, Tile goalTile){
        return Vector2.dst(currentTile.x, currentTile.y, goalTile.x, goalTile.y);
    }
}
