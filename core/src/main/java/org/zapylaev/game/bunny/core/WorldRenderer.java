package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private WorldController mWorldController;

    public WorldRenderer(WorldController worldController) {
        mWorldController = worldController;
    }

    public void init() {

    }

    public void render() {

    }

    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
