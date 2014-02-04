package org.zapylaev.game.bunny.core.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraMan {
    public static final String LOG_TAG = CameraMan.class.getSimpleName();

    public static final float MAX_ZOOM_IN = 0.25f;
    public static final float MAX_ZOOM_OUT = 10f;

    private Vector2 mPosition;
    private float mZoom;
    private Sprite mTarget;

    public CameraMan() {
        mPosition = new Vector2();
        mZoom = 1f;
    }

    public void update(float delta) {
        if (!hasTarget()) return;

        mPosition.x = mTarget.getX() + mTarget.getOriginX();
        mPosition.y = mTarget.getY() + mTarget.getOriginY();
    }

    public void setPosition(float x, float y) {
        mPosition.set(x, y);
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    public void addZoom(float amount) {
        setZoom(mZoom + amount);
    }
    public void setZoom(float zoom) {
        mZoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }
    public float getZoom() {
        return mZoom;
    }

    public void setTarget(Sprite target) {
        mTarget = target;
    }

    public Sprite getTarget () {
        return mTarget;
    }

    public boolean hasTarget() {
        return mTarget != null;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && mTarget.equals(target);
    }

    public void giveCamera(OrthographicCamera camera) {
        camera.position.x = mPosition.x;
        camera.position.y = mPosition.y;
        camera.zoom = mZoom;
        camera.update();
    }
}
