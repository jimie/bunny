package org.zapylaev.game.bunny.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import org.zapylaev.game.bunny.core.gameobject.AGameObject;
import org.zapylaev.game.bunny.core.gameobject.Clouds;
import org.zapylaev.game.bunny.core.gameobject.Mountains;
import org.zapylaev.game.bunny.core.gameobject.Rock;
import org.zapylaev.game.bunny.core.gameobject.WaterOverlay;

public class Level {
    public enum BlockType {
        EMPTY(0, 0, 0), // black
        ROCK(0, 255, 0), // green
        PLAYER_SPAWNPOINT(255, 255, 255), // white
        ITEM_FEATHER(255, 0, 255), // purple
        ITEM_GOLD_COIN(255, 255, 0); // yellow

        private int color;

        private BlockType(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean isSameColor(int color) {
            return this.color == color;
        }

        public int getColor() {
            return color;
        }
    }

    // objects
    public Array<Rock> rocks;
    // decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level(String filename) {
        init(filename);
    }

    private void init(String filename) {
        rocks = new Array<Rock>();
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // scan pixels from top-left to bottom-right
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AGameObject obj = null;
                float offsetHeight = 0;
                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                // get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match
                // empty space
                if (BlockType.EMPTY.isSameColor(currentPixel)) {
                    // do nothing
                }
                // rock
                else if (BlockType.ROCK.isSameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.mPosition.set(pixelX, baseHeight * obj.mDimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock) obj);
                    } else {
                        rocks.get(rocks.size - 1).increaseLength(1);
                    }
                } else if (BlockType.PLAYER_SPAWNPOINT.isSameColor(currentPixel)) {

                } else if (BlockType.ITEM_FEATHER.isSameColor(currentPixel)) {

                } else if (BlockType.ITEM_GOLD_COIN.isSameColor(currentPixel)) {

                } else { // unknown object/pixel color
                    int r = 0xff & (currentPixel >>> 24); //red color channel
                    int g = 0xff & (currentPixel >>> 16); //green color channel
                    int b = 0xff & (currentPixel >>> 8); //blue color channel
                    int a = 0xff & currentPixel;
                    //alpha channel
                    Gdx.app.error(Level.class.getSimpleName(), "Unknown object at x<" + pixelX
                            + "> y<" + pixelY
                            + ">: r<" + r
                            + "> g<" + g
                            + "> b<" + b
                            + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }
    }

    public void render(SpriteBatch batch) {
        // Draw Mountains
        if (mountains != null) mountains.render(batch);
        // Draw Rocks
        for (Rock rock : rocks) rock.render(batch);
        // Draw Water Overlay
        if (waterOverlay != null) waterOverlay.render(batch);
        // Draw Clouds
        if (clouds != null) clouds.render(batch);
    }
}
