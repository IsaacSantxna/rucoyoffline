package com.rucoyoffline.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.rucoyoffline.game.Screens.PlayScreen;

public class PlayerHead extends Sprite {
    private TextureRegion defaultKnightHead;
    private MainChar player;
    private World world;

    public PlayerHead(PlayScreen screen){
        super(screen.getAtlas().findRegion("knight"));

        defaultKnightHead = new TextureRegion(getTexture(), 206, 216, 20, 21);
        setBounds(0, 0, 20, 21);
        setRegion(defaultKnightHead);



    }

    public void update(float dt, float playerX, float playerY){
        setPosition(playerX, playerY);
        setPosition(playerX, playerY);
        //setPosition(getPlayerPosition().x, getPlayerPosition().y);
    }

}
