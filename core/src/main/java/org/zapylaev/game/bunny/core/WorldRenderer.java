package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import org.zapylaev.game.bunny.core.util.Constants;

public class WorldRenderer implements Disposable {

    public static final String LOG_TAG = WorldRenderer.class.getSimpleName();

    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private WorldController mWorldController;

    public WorldRenderer(WorldController worldController) {
        mWorldController = worldController;
    }

    public void init() {
        mBatch = new SpriteBatch();
        mCamera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        mCamera.position.set(0, 0, 0);
        mCamera.update();

    }

    public void render() {
        renderTestObjects();
    }

    private void renderTestObjects() {
        mWorldController.mCameraMan.giveCamera(mCamera);
        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.begin();
        for (Sprite sprite : mWorldController.mTestSprites) {
            sprite.draw(mBatch);
        }
        mBatch.end();
    }

    public void resize(int width, int height) {
        mCamera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        mCamera.update();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }
}
