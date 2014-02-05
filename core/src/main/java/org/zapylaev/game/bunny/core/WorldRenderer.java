package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import org.zapylaev.game.bunny.core.gameobject.AGameObject;
import org.zapylaev.game.bunny.core.gameobject.Mountains;
import org.zapylaev.game.bunny.core.gameobject.Rock;
import org.zapylaev.game.bunny.core.util.Constants;

public class WorldRenderer implements Disposable {

    public static final String LOG_TAG = WorldRenderer.class.getSimpleName();

    private OrthographicCamera mCamera;
    private OrthographicCamera mCameraGUI;
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

        mCameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        mCameraGUI.position.set(0, 0, 0);
        mCameraGUI.setToOrtho(true); // flip y-axis
        mCameraGUI.update();
    }

    public void render() {
        mWorldController.mCameraMan.giveCamera(mCamera);
        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.begin();
        mWorldController.mLevel.render(mBatch);
        mBatch.end();

        mBatch.setProjectionMatrix(mCameraGUI.combined);
        mBatch.begin();
        renderGuiScore(mBatch);
        renderGuiExtraLive(mBatch);
        mBatch.end();
    }

    private void renderGuiScore(SpriteBatch batch) {
        float x = -15;
        float y = -15;
        batch.draw(Assets.getInstance().goldCoin.goldCoin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
        Assets.getInstance().fonts.defaultBig.draw(batch, "" + mWorldController.mScore, x + 75, y + 37);
    }

    private void renderGuiExtraLive(SpriteBatch batch) {
        float x = mCameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < Constants.LIVES_START; i++) {
            if (mWorldController.mLives <= i) batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            batch.draw(Assets.getInstance().bunny.head, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0); batch.setColor(1, 1, 1, 1);
        }
    }

    public void resize(int width, int height) {
        mCamera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        mCamera.update();

        mCameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        mCameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float)width;
        mCameraGUI.position.set(mCameraGUI.viewportWidth / 2, mCameraGUI.viewportHeight / 2, 0);
        mCameraGUI.update();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }
}
