package com.rucoyoffline.game.Sprites;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.cell;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rucoyoffline.game.Screens.PlayScreen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTile;



public class MainChar extends Sprite {
    public World world;
    public Body b2body;
    public TiledMap map1;
    private Rectangle rectangle;
    private MapObject square1;
    private TmxMapLoader maploader;
    private TextureRegion defaultKnightBody;
    private TextureRegion defaultKnightHead;
    private Vector2 playerPosition;

    private float stateTimer;
    public enum State {RUNNING, BASIC_ATTACK, SPECIAL_ATTACK, STANDING}
    public State currentState;
    public State previousState;

    private boolean runningUp;
    private boolean runningDown;
    private boolean runningLeft;
    private boolean runningRight;



    public MainChar(World world, PlayScreen screen){


        super(screen.getAtlas().findRegion("knight"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningDown = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        defineMainChar();

        defaultKnightBody = new TextureRegion(getTexture(), 153, 212, 17, 19);
        setBounds(0, 0, 20, 22);
        setRegion(defaultKnightBody);


    }

    public Rectangle SpawnPoint(){
        maploader = new TmxMapLoader();
        map1 = maploader.load("Maps/map1.tmx");
        rectangle = new Rectangle();
        square1 = map1.getLayers().get("Open Tiles").getObjects().get("Spawn tile");
        rectangle = ((RectangleMapObject) square1).getRectangle();
        return rectangle;
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,
                b2body.getPosition().y - getHeight()/2);
    }

    public void defineMainChar(){

        BodyDef bdef = new BodyDef();


        bdef.position.set(((SpawnPoint().getX()) + SpawnPoint().getWidth()/2),((SpawnPoint().getY()) + SpawnPoint().getHeight()/2));


        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SpawnPoint().getHeight()/2,SpawnPoint().getWidth()/2);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        playerPosition = bdef.position;




    }

    public Vector2 getPlayerPosition(){
        return playerPosition;
    }
}
