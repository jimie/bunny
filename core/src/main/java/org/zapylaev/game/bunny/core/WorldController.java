package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import org.zapylaev.game.bunny.core.gameobject.BunnyHead;
import org.zapylaev.game.bunny.core.gameobject.GoldCoin;
import org.zapylaev.game.bunny.core.gameobject.Rock;
import org.zapylaev.game.bunny.core.util.CameraMan;
import org.zapylaev.game.bunny.core.util.Constants;

public class WorldController extends InputAdapter {

    public static final String LOG_TAG = WorldController.class.getSimpleName();

    public CameraMan mCameraMan;

    public Level mLevel;
    public int mLives;
    public int mScore;

    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

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
        mCameraMan.setTarget(mLevel.bunnyHead);
    }

    public void update(float delta) {
//        handleDebugInput(delta);
        handleInputGame(delta);
        mLevel.update(delta);
        testCollisions();
        mCameraMan.update(delta);
    }

    private void handleInputGame (float deltaTime) {
        if (mCameraMan.hasTarget(mLevel.bunnyHead)) {
            // Player Movement
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                mLevel.bunnyHead.velocity.x =
                        -mLevel.bunnyHead.terminalVelocity.x;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                mLevel.bunnyHead.velocity.x =
                        mLevel.bunnyHead.terminalVelocity.x;
            } else {
                // Execute auto-forward movement on non-desktop platform
                if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
                    mLevel.bunnyHead.velocity.x = mLevel.bunnyHead.terminalVelocity.x;
                }
            }
            // Bunny Jump
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE))
                mLevel.bunnyHead.setJumping(true);
            else mLevel.bunnyHead.setJumping(false);
        } else {
            mLevel.bunnyHead.setJumping(false);
        }
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

    private void onCollisionBunnyHeadWithRock(Rock rock) {
        BunnyHead bunnyHead = mLevel.bunnyHead;
        float heightDifference = Math.abs(bunnyHead.mPosition.y
                - ( rock.mPosition.y
                + rock.bounds.height));
        if (heightDifference > 0.25f) {
            boolean hitLeftEdge = bunnyHead.mPosition.x
                    > ( rock.mPosition.x
                    + rock.bounds.width / 2.0f);
            if (hitLeftEdge) {
                bunnyHead.mPosition.x = rock.mPosition.x
                        + rock.bounds.width;
            } else {
                bunnyHead.mPosition.x = rock.mPosition.x
                        - bunnyHead.bounds.width;
            }
            return;
        }
        switch (bunnyHead.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                bunnyHead.mPosition.y = rock.mPosition.y
                        + bunnyHead.bounds.height
                        + bunnyHead.mOrigin.y;
                bunnyHead.jumpState = BunnyHead.JumpState.GROUNDED;
                break;
            case JUMP_RISING:
                bunnyHead.mPosition.y = rock.mPosition.y
                        + bunnyHead.bounds.height
                        + bunnyHead.mOrigin.y;
                break;
        }
    }

    private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
        goldcoin.collected = true;
        mScore += goldcoin.getScore();
    }

    private void testCollisions () {
        r1.set(mLevel.bunnyHead.mPosition.x,
                mLevel.bunnyHead.mPosition.y,
                mLevel.bunnyHead.bounds.width,
                mLevel.bunnyHead.bounds.height);
        // Test collision: Bunny Head <-> Rocks
        for (Rock rock : mLevel.rocks) {
            r2.set(rock.mPosition.x, rock.mPosition.y,
                    rock.bounds.width, rock.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyHeadWithRock(rock);
            // IMPORTANT: must do all collisions for valid
            // edge testing on rocks.
        }
        // Test collision: Bunny Head <-> Gold Coins
        for (GoldCoin goldcoin : mLevel.goldcoins) {
            if (goldcoin.collected) continue;
            r2.set(goldcoin.mPosition.x, goldcoin.mPosition.y,
                    goldcoin.bounds.width, goldcoin.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyWithGoldCoin(goldcoin);
            break;
        }
    }
}
