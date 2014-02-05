package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.zapylaev.game.bunny.core.Assets;

public class Clouds extends AGameObject {

    private float mLength;
    private Array<TextureRegion> mRegClouds;
    private Array<Cloud> mClouds;

    public Clouds(float length) {
        mLength = length;
        init();
    }

    private void init() {
        mDimension.set(3.0f, 1.5f);
        mRegClouds = new Array<TextureRegion>();
        mRegClouds.add(Assets.getInstance().levelDecoration.cloud01);
        mRegClouds.add(Assets.getInstance().levelDecoration.cloud02);
        mRegClouds.add(Assets.getInstance().levelDecoration.cloud03);
        int distFac = 5;
        int numClouds = (int) (mLength / distFac);
        mClouds = new Array<Cloud>(2 * numClouds);
        for (int i = 0; i < numClouds; i++) {
            Cloud cloud = spawnCloud();
            cloud.mPosition.x = i * distFac;
            mClouds.add(cloud);
        }
    }

    private Cloud spawnCloud() {
        Cloud cloud = new Cloud();
        cloud.mDimension.set(mDimension);
        // select random cloud image
        cloud.setRegion(mRegClouds.random());
        // mPosition
        Vector2 pos = new Vector2();
        pos.x = mLength + 10; // mPosition after end of level
        pos.y += 1.75; // base mPosition
        // random additional mPosition
        pos.y += MathUtils.random(0.0f, 0.2f)
                * (MathUtils.randomBoolean() ? 1 : -1);
        cloud.mPosition.set(pos);
        return cloud;
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Cloud cloud : mClouds) {
            cloud.render(batch);
        }
    }

    private static class Cloud extends AGameObject {

        private TextureRegion mRegCloud;

        public void setRegion(TextureRegion region) {
            mRegCloud = region;
        }

        @Override
        public void render(SpriteBatch batch) {
            TextureRegion reg = mRegCloud;
            batch.draw(reg.getTexture(),
                    mPosition.x + mOrigin.x, mPosition.y + mOrigin.y,
                    mOrigin.x, mOrigin.y,
                    mDimension.x, mDimension.y,
                    mScale.x, mScale.y,
                    mRotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
        }
    }
}
