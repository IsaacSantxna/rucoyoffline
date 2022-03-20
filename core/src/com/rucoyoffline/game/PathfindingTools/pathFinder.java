package com.rucoyoffline.game.PathfindingTools;

import static java.lang.Math.abs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rucoyoffline.game.Sprites.MainChar;
import com.rucoyoffline.game.Screens.PlayScreen;
import com.badlogic.gdx.Input;

public class pathFinder extends ApplicationAdapter{
    TileGraph tileGraph;
    GraphPath<Tile> tilePath;
    TiledMap map1;
    Tile tile;
    MainChar player;
    Tile startTile;
    Tile goalTile;


    public void createPath(){

        tileGraph = new TileGraph();
        //Iterates through each tile and adds to tileGraph
        for (MapObject object :
                map1.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            tile = new Tile(rect.getX()+rect.getWidth()/2,
                    rect.getY()+rect.getHeight()/2);
            tileGraph.addTile(tile);

            //gets starting tile based on player position
            if ((abs(tile.x - player.b2body.getPosition().x) <
                    rect.getWidth()/2) && (abs(tile.y - player.b2body.getPosition().y) <
                    rect.getHeight()/2))
                startTile = tile;

            //gets goal tile based on which tile is clicked
            if (((abs(Gdx.input.getX()-tile.x)<rect.getWidth()/2) &&
                    (Gdx.input.isTouched())) && ((abs(Gdx.input.getY()-tile.y) <
                    rect.getHeight()) && (Gdx.input.isTouched())))
                goalTile = tile;

        //Connects all tiles that are adjacent to eachother
        for (int i=0; i<tileGraph.getNodeCount(); i++){
            for(int j=0; j<tileGraph.getNodeCount(); j++)
                if (((tileGraph.tiles.get(j).x == tileGraph.tiles.get(i).x) &&
                (abs(tileGraph.tiles.get(j).y - tileGraph.tiles.get(i).y) ==
                        rect.getHeight())) ||
                ((tileGraph.tiles.get(j).y == tileGraph.tiles.get(i).y) &&
                        (abs(tileGraph.tiles.get(j).x - tileGraph.tiles.get(i).x) ==
                                rect.getWidth())))
                    tileGraph.connectTiles(tileGraph.tiles.get(j), tileGraph.tiles.get(i));
        }

        //finds a path
        tilePath = tileGraph.findPath(startTile, goalTile);

        //makes the player go to each tile within the path
        for (int i = 0; i < tilePath.getCount(); i++){
            if ((player.b2body.getPosition().x > tilePath.get(i).x))
                player.b2body.setLinearVelocity(-100, 0);
            else if ((player.b2body.getPosition().x < tilePath.get(i).x))
                player.b2body.setLinearVelocity(100, 0);
            else if ((player.b2body.getPosition().y > tilePath.get(i).y))
                player.b2body.setLinearVelocity(0, -100);
            else if ((player.b2body.getPosition().y < tilePath.get(i).y))
                player.b2body.setLinearVelocity(0, 100);
        }

        }




    }
}
