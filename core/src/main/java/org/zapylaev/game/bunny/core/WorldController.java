package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import org.zapylaev.game.bunny.core.util.CameraMan;
import org.zapylaev.game.bunny.core.util.Constants;

public class WorldController extends InputAdapter {

    public static final String LOG_TAG = WorldController.class.getSimpleName();

    public CameraMan mCameraMan;

    public Level mLevel;
    public int mLives;
    public int mScore;
    public WorldController() {
    }

    public void init() {
        Gdx.input.setInputProcessor(this);
        mCameraMan = new CameraMan();
        mLives = Constants.LIVES_START;
        initLevel();
    }

    private void initLevel () {
        mScore = 0;
        mLevel = new Level(Constants.LEVEL_01);
    }

    public void update(float delta) {
        handleDebugInput(delta);
        mCameraMan.update(delta);
    }

    private void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        // Camera Controls (move)
        float camMoveSpeed = 5 * delta;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) mCameraMan.setPosition(0, 0);

        // Camera Controls (zoom)
        float camZoomSpeed = 1 * delta;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) mCameraMan.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) mCameraMan.addZoom(-camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) mCameraMan.setZoom(1);
    }

    private void moveCamera(float x, float y) {
        x += mCameraMan.getPosition().x;
        y += mCameraMan.getPosition().y;
        mCameraMan.setPosition(x, y);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.R) {
            init();
            Gdx.app.log(LOG_TAG, "Reseted game world");
        }
        return false;
    }
}
