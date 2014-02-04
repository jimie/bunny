package org.zapylaev.game.bunny.html;

import org.zapylaev.game.bunny.core.BunnyMain;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class BunnyMainHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new BunnyMain();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
