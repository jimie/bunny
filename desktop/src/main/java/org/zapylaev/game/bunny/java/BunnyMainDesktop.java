package org.zapylaev.game.bunny.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.zapylaev.game.bunny.core.BunnyMain;

public class BunnyMainDesktop {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        new LwjglApplication(new BunnyMain(), config);
    }
}
