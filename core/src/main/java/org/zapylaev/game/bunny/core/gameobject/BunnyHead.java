package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.zapylaev.game.bunny.core.Assets;

public class BunnyHead extends AGameObject {
    public static final String TAG = BunnyHead.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

    public enum ViewDirection { LEFT, RIGHT }
    public enum JumpState {
        GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
    }

    private TextureRegion regHead;
    public ViewDirection mViewDirection;
    public float timeJumping;
    public JumpState jumpState;

    public BunnyHead() {
        init();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (velocity.x != 0) {
            mViewDirection = velocity.x < 0 ? ViewDirection.LEFT : ViewDirection.RIGHT;
        }
    }

    @Override
    protected void updateMotionY (float deltaTime) {
        switch (jumpState) {
            case GROUNDED:
                jumpState = JumpState.FALLING;
                break;
            case JUMP_RISING:
                // Keep track of jump time
                timeJumping += deltaTime;
                // Jump time left?
                if (timeJumping <= JUMP_TIME_MAX) {
                    // Still jumping
                    velocity.y = terminalVelocity.y;
                }
                break;
            case FALLING:
                break;
            case JUMP_FALLING:
                // Add delta times to track jump time
                timeJumping += deltaTime;
                // Jump to minimal height if jump key was pressed too short
                if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
                    // Still jumping
                    velocity.y = terminalVelocity.y;
                }
        }
        if (jumpState != JumpState.GROUNDED) super.updateMotionY(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(regHead.getTexture(),
                mPosition.x, mPosition.y,
                mOrigin.x, mOrigin.y,
                mDimension.x, mDimension.y,
                mScale.x, mScale.y,
                mRotation,
                regHead.getRegionX(), regHead.getRegionY(),
                regHead.getRegionWidth(), regHead.getRegionHeight(),
                mViewDirection == ViewDirection.LEFT, false);
    }

    public void init() {
        mDimension.set(1, 1);
        regHead = Assets.getInstance().bunny.head;
        mOrigin.set(mDimension.x / 2, mDimension.y / 2);
        bounds.set(0, 0, mDimension.x, mDimension.y);
        // Set physics values
        terminalVelocity.set(3.0f, 4.0f);
        friction.set(12.0f, 0.0f);
        acceleration.set(0.0f, -25.0f);
        // View direction
        mViewDirection = ViewDirection.RIGHT;
        // Jump state
        jumpState = JumpState.FALLING;
        timeJumping = 0;
    }

    public void setJumping(boolean jumpKeyPressed) {
        switch (jumpState) {
            case GROUNDED: // Character is standing on a platform
                if (jumpKeyPressed) {
                    // Start counting jump time from the beginning
                    timeJumping = 0;
                    jumpState = JumpState.JUMP_RISING;
                }
                break;
            case JUMP_RISING: // Rising in the air
                if (!jumpKeyPressed)
                    jumpState = JumpState.JUMP_FALLING;
                break;
            case FALLING:// Falling down
            case JUMP_FALLING: // Falling down after jump
                if (jumpKeyPressed) {
                    timeJumping = JUMP_TIME_OFFSET_FLYING;
                    jumpState = JumpState.JUMP_RISING;
                }
                break;
        }
    }
}
