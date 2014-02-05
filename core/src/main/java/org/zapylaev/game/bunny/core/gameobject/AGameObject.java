package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AGameObject {
    public Vector2 mPosition;
    public Vector2 mDimension;
    public Vector2 mOrigin;
    protected Vector2 mScale;
    protected float mRotation;

    public AGameObject() {
        mPosition = new Vector2();
        mDimension = new Vector2(1, 1);
        mOrigin = new Vector2();
        mScale = new Vector2(1, 1);
        mRotation = 0;
    }

    public void update(float delta) {

    }

    public abstract void render(SpriteBatch batch);
}
