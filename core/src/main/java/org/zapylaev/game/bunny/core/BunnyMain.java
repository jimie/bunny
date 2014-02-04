package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class BunnyMain implements ApplicationListener {

    public static final String LOG_TAG = BunnyMain.class.getSimpleName();

    private WorldController mWorldController;
    private WorldRenderer mWorldRenderer;
    private boolean mPaused;

    public BunnyMain() {
        mWorldController = new WorldController();
        mWorldRenderer = new WorldRenderer(mWorldController);
    }

    @Override
	public void create () {
	}

	@Override
	public void resize (int width, int height) {
        mWorldRenderer.resize(width, height);
	}

	@Override
	public void render () {
        if (!mPaused) {
            mWorldController.update(Gdx.graphics.getDeltaTime());
        }
        Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        mWorldRenderer.render();
    }

	@Override
	public void pause () {
        mPaused = true;
	}

	@Override
	public void resume () {
        mPaused = false;
	}

	@Override
	public void dispose () {
        mWorldRenderer.dispose();
	}
}
