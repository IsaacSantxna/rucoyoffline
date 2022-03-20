package com.rucoyoffline.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class OpenTiles {
    private World world;
    private TiledMap map1;
    private TiledMapTile tile;
    private Rectangle bounds;
    private Body body;

    public OpenTiles(World world, TiledMap map1, Rectangle bounds) {
        this.world = world;
        this.map1 = map1;
        this.bounds = bounds;

    }
}
