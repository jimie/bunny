package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import org.zapylaev.game.bunny.core.util.CameraMan;

public class WorldController extends InputAdapter {

    public static final String LOG_TAG = WorldController.class.getSimpleName();

    public Sprite[] mTestSprites;
    public int mSelectedSprite = 4;

    public CameraMan mCameraMan;

    public WorldController() {
    }

    public void init() {
        Gdx.input.setInputProcessor(this);
        mCameraMan = new CameraMan();
        initTestObjects();
    }

    private void initTestObjects() {
        mTestSprites = new Sprite[5];

        Array<TextureRegion> regions = new Array<TextureRegion>();
        regions.add(Assets.getInstance().bunny.head);
        regions.add(Assets.getInstance().feather.feather);
        regions.add(Assets.getInstance().goldCoin.goldCoin);
        for (int i = 0; i < mTestSprites.length; i++) {
            Sprite sprite = new Sprite(regions.random());
            sprite.setSize(1, 1);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            sprite.setPosition(randomX, randomY);
            mTestSprites[i] = sprite;
        }
        mSelectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    public void update(float delta) {
        handleDebugInput(delta);
        updateTestObjects(delta);
        mCameraMan.update(delta);
    }

    private void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float spriteMoveSpeed = delta * 5;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveSelectedSprite(-spriteMoveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveSelectedSprite(spriteMoveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveSelectedSprite(0, spriteMoveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveSelectedSprite(0, -spriteMoveSpeed);
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
        } else if (keycode == Input.Keys.SPACE) {
            mSelectedSprite = (mSelectedSprite + 1) % mTestSprites.length;
            Gdx.app.log(LOG_TAG, "Sprite #" + mSelectedSprite + " was selected");
            if (mCameraMan.hasTarget()) {
                mCameraMan.setTarget(mTestSprites[mSelectedSprite]);
            }
        } else if (keycode == Input.Keys.ENTER) {
            mCameraMan.setTarget(mCameraMan.hasTarget() ? null : mTestSprites[mSelectedSprite]);
            Gdx.app.debug(LOG_TAG, "Camera follow enabled: " + mCameraMan.hasTarget());
        }
        return false;
    }

    private void moveSelectedSprite(float x, float y) {
        mTestSprites[mSelectedSprite].translate(x, y);
    }

    private void updateTestObjects(float delta) {
        float rotation = mTestSprites[mSelectedSprite].getRotation();
        rotation += 90 * delta;
        rotation %= 360;
        mTestSprites[mSelectedSprite].setRotation(rotation);
    }
}
