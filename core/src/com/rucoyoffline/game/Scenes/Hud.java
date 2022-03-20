package com.rucoyoffline.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rucoyoffline.game.RucoyOffline;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public Hud(SpriteBatch sb){
        viewport = new StretchViewport(RucoyOffline.V_WIDTH, RucoyOffline.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
    }


@Override
public void dispose(){
        stage.dispose();
    }
}
