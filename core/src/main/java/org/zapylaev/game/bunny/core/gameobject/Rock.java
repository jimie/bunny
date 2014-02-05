package org.zapylaev.game.bunny.core.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.zapylaev.game.bunny.core.Assets;

public class Rock extends AGameObject {

    private TextureRegion mRegEdge;
    private TextureRegion mRegMiddle;
    private int mLength;

    public Rock() {
        init();
    }

    private void init() {
        mDimension.set(1, 1.5f);
        mRegEdge = Assets.getInstance().rock.edge;
        mRegMiddle = Assets.getInstance().rock.middle;
        setLength(1);
    }

    public void setLength(int length) {
        mLength = length;
        bounds.set(0, 0, mDimension.x * length, mDimension.y);
    }

    public void increaseLength(int amount) {
        setLength(mLength + amount);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg;
        float relX = 0;
        float relY = 0;
        // Draw left edge
        reg = mRegEdge;
        relX -= mDimension.x / 4;
        batch.draw(reg.getTexture(),
                mPosition.x + relX, mPosition.y + relY,
                mOrigin.x, mOrigin.y,
                mDimension.x / 4, mDimension.y,
                mScale.x, mScale.y,
                mRotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
        // Draw middle
        relX = 0;
        reg = mRegMiddle;
        for (int i = 0; i < mLength; i++) {
            batch.draw(reg.getTexture(),
                    mPosition.x + relX, mPosition.y + relY,
                    mOrigin.x, mOrigin.y,
                    mDimension.x, mDimension.y,
                    mScale.x, mScale.y,
                    mRotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
            relX += mDimension.x;
        }
        // Draw right edge
        reg = mRegEdge;
        batch.draw(reg.getTexture(),
                mPosition.x + relX, mPosition.y + relY,
                mOrigin.x + mDimension.x / 8, mOrigin.y,
                mDimension.x / 4, mDimension.y,
                mScale.x, mScale.y,
                mRotation,
                reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(),
                true, false);
    }
}
