package com.rucoyoffline.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rucoyoffline.game.Sprites.MainChar;


public class arrowkeycode {
    MainChar player;
    public void keycode(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.b2body.setLinearVelocity(0, 450);
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.b2body.setLinearVelocity(0, -450);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.b2body.setLinearVelocity(-450, 0);
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.b2body.setLinearVelocity(450, 0);
        else
            player.b2body.setLinearVelocity(0, 0);
    }
}
