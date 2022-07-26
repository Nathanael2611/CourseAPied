package fr.nathanael2611;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fr.nathanael2611.coursapied.Coursapied;
import fr.nathanael2611.coursapied.util.IStopper;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Coursapied(new IStopper()
		{
			@Override
			public void stop()
			{
				Gdx.app.exit();
			}
		}), config);
	}
}
