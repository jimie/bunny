package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import org.zapylaev.game.bunny.core.util.Constants;

public class Assets implements Disposable, AssetErrorListener {

    public static final String LOG_TAG = Assets.class.getSimpleName();

    private AssetManager mAssetManager;

    private static Assets instance;

    private Assets () {}

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public AssetBunny bunny;
    public AssetRock rock;
    public AssetGoldCoin goldCoin;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;

    public void init(AssetManager assetManager) {
        mAssetManager = assetManager;
        mAssetManager.setErrorListener(this);
        mAssetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        mAssetManager.finishLoading();

        Gdx.app.debug(LOG_TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(LOG_TAG, "asset: " + a);
        }

        TextureAtlas atlas = mAssetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        goldCoin = new AssetGoldCoin(atlas);
        feather = new AssetFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(LOG_TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    @Override
    public void dispose() {
        mAssetManager.dispose();
    }

    public static class AssetBunny {
        public final TextureAtlas.AtlasRegion head;
        public AssetBunny(TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");
        }
    }

    public static class AssetRock {
        public final TextureAtlas.AtlasRegion edge;
        public final TextureAtlas.AtlasRegion middle;
        public AssetRock(TextureAtlas atlas) {
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock_middle");
        }
    }

    public static class AssetGoldCoin {
        public final TextureAtlas.AtlasRegion goldCoin;
        public AssetGoldCoin(TextureAtlas atlas) {
            goldCoin = atlas.findRegion("item_gold_coin");
        }
    }

    public static class AssetFeather {
        public final TextureAtlas.AtlasRegion feather;
        public AssetFeather(TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }
    }

    public static class AssetLevelDecoration {
        public final TextureAtlas.AtlasRegion cloud01;
        public final TextureAtlas.AtlasRegion cloud02;
        public final TextureAtlas.AtlasRegion cloud03;
        public final TextureAtlas.AtlasRegion mountainLeft;
        public final TextureAtlas.AtlasRegion mountainRight;
        public final TextureAtlas.AtlasRegion waterOverlay;
        public AssetLevelDecoration(TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
        }
    }
}

