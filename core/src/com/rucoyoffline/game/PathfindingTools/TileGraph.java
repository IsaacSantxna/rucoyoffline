package com.rucoyoffline.game.PathfindingTools;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class TileGraph implements IndexedGraph<Tile> {

    TileHeuristic tileHeuristic = new TileHeuristic();
    public Array<Tile> tiles = new Array<>();
    Array<Connector> connectors = new Array<>();
    ObjectMap<Tile, Array<Connection<Tile>>> connectorsMap = new ObjectMap<>();
    private int lastNodeIndex = 0;

    public void addTile(Tile tile){
        tile.index = lastNodeIndex;
        lastNodeIndex++;
        tiles.add(tile);
    }

    public void connectTiles(Tile fromTile, Tile toTile){
        Connector connector = new Connector(fromTile, toTile);
        if(!connectorsMap.containsKey(fromTile)){
            connectorsMap.put(fromTile, new Array<Connection<Tile>>());
        }
        connectorsMap.get(fromTile).add(connector);
        connectors.add(connector);
    }

    public GraphPath<Tile> findPath(Tile startTile, Tile goalTile){
        GraphPath<Tile> tilePath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startTile, goalTile, tileHeuristic, tilePath);
        return tilePath;
    }

    @Override
    public int getIndex(Tile node){
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile fromNode){
        if(connectorsMap.containsKey(fromNode))
            return connectorsMap.get(fromNode);
        return new Array<>(0);
    }
}
