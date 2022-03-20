package com.rucoyoffline.game.Screens;

import static java.lang.Math.abs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rucoyoffline.game.PathfindingTools.Tile;
import com.rucoyoffline.game.PathfindingTools.TileGraph;
import com.rucoyoffline.game.RucoyOffline;
import com.rucoyoffline.game.Scenes.Hud;
import com.rucoyoffline.game.Sprites.MainChar;
import com.rucoyoffline.game.Sprites.PlayerHead;
import com.rucoyoffline.game.Tools.B2WorldCreator;
import com.rucoyoffline.game.PathfindingTools.pathFinder;
import com.rucoyoffline.game.PathfindingTools.*;

public class PlayScreen implements Screen {

    private TextureAtlas atlas;

    private RucoyOffline game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map1;
    private Box2DDebugRenderer b2dr;
    private World world;
    private MainChar player;
    private SpriteBatch batch;
    private Texture tex;
    private Rectangle rect;
    private TileGraph tileGraph;
    private GraphPath<Tile> tilePath;
    private Tile tile;
    private Tile startTile;
    private Tile goalTile;
    private Vector3 mouse_position = new Vector3(0, 0, 0);
    private int i = 1;
    public boolean touched;
    private PlayerHead head;


    public PlayScreen(RucoyOffline game) {


        atlas = new TextureAtlas("Sprites/pack.atlas");

        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new StretchViewport(RucoyOffline.V_WIDTH, RucoyOffline.V_HEIGHT, camera);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map1 = maploader.load("Maps/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map1);
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(map1);

        player = new MainChar(world, this);

        head = new PlayerHead(this);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public int getI() {
    return i;
}




    @Override
    public void show() {

    }

    public void handleInput(float dt) {


    }

    public void update(float dt) {
        handleInput(dt);

        player.update(dt);

        head.update(dt, player.getPlayerPosition().x, player.getPlayerPosition().y);

        pathCreator();
        world.step(1 / 60f, 6, 2);

        camera.position.x = player.b2body.getPosition().x;
        camera.position.y = player.b2body.getPosition().y;

        camera.update();
        renderer.setView(camera);


    }


    @Override
    public void render(float delta) {



        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(),0);

        update(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        head.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        camera.unproject(mouse_position);

        pathCreator();

        if (Gdx.input.isTouched()){
            if(!Gdx.input.isTouched()) {
                System.out.print(1);
            }
        }



        int x = getI();

        if (Gdx.input.justTouched()) {

            tilePath = tileGraph.findPath(startTile, goalTile);

            touched = true;
        }


        if (touched) {

            if (Gdx.input.justTouched()){
                i = 1;
            }
            if (x < tilePath.getCount()) {

                if (player.b2body.getPosition().x > tilePath.get(x).x) {

                    player.b2body.setLinearVelocity(-60, 0);
                }
                   else if ((player.b2body.getPosition().x == tilePath.get(x).x) &&
                            (player.b2body.getPosition().y == tilePath.get(x).y)) {
                        player.b2body.setLinearVelocity(0, 0);

                        i++;
                    }
                else if (player.b2body.getPosition().x < tilePath.get(x).x) {
                     player.b2body.setLinearVelocity(60, 0);
                 }
                   else if ((player.b2body.getPosition().x == tilePath.get(x).x) &&
                            (player.b2body.getPosition().y == tilePath.get(x).y)) {
                        player.b2body.setLinearVelocity(0, 0);

                        i++;
                    }
                else if (player.b2body.getPosition().y > tilePath.get(x).y) {
                     player.b2body.setLinearVelocity(0, -60);
                 }
                   else if ((player.b2body.getPosition().x == tilePath.get(x).x) &&
                            (player.b2body.getPosition().y == tilePath.get(x).y)) {
                        player.b2body.setLinearVelocity(0, 0);

                        i++;
                    }

                else if (player.b2body.getPosition().y < tilePath.get(x).y) {
                     player.b2body.setLinearVelocity(0, 60);
                 }
                   else if ((player.b2body.getPosition().x == tilePath.get(x).x) &&
                            (player.b2body.getPosition().y == tilePath.get(x).y)) {
                        player.b2body.setLinearVelocity(0, 0);

                        i++;
                    }




            }



        }










    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map1.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public void pathCreator(){

        tileGraph = new TileGraph();

        for (MapObject object :
                map1.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            tile = new Tile(rect.getX() + rect.getWidth() / 2,
                    rect.getY() + rect.getHeight() / 2);
            tileGraph.addTile(tile);
        }
        for (int i=0; i<tileGraph.getNodeCount(); i++) {
            if ((abs(tileGraph.tiles.get(i).x - player.b2body.getPosition().x) <
                    13) && (abs(tileGraph.tiles.get(i).y - player.b2body.getPosition().y) <
                    13))
                startTile = tileGraph.tiles.get(i);
        }
        for (int i=0; i<tileGraph.getNodeCount(); i++) {
            if (((abs(mouse_position.x - tileGraph.tiles.get(i).x) < 13) &&
                    (Gdx.input.justTouched())) && ((abs(mouse_position.y - tileGraph.tiles.get(i).y) <
                    13) && (Gdx.input.justTouched())))
                goalTile = tileGraph.tiles.get(i);
        }

        for (int i=0; i<tileGraph.getNodeCount(); i++) {
            for (int j = 0; j < tileGraph.getNodeCount(); j++)
                if (((tileGraph.tiles.get(j).x == tileGraph.tiles.get(i).x) &&
                        (abs(tileGraph.tiles.get(j).y - tileGraph.tiles.get(i).y) ==
                                26)) ||
                        ((tileGraph.tiles.get(j).y == tileGraph.tiles.get(i).y) &&
                                (abs(tileGraph.tiles.get(j).x - tileGraph.tiles.get(i).x) ==
                                        26)))
                    tileGraph.connectTiles(tileGraph.tiles.get(j), tileGraph.tiles.get(i));
        }





    }




}