package fr.nathanael2611.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import fr.nathanael2611.coursapied.Coursapied;
import fr.nathanael2611.coursapied.util.IStopper;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                GwtApplicationConfiguration gwtApplicationConfiguration = new GwtApplicationConfiguration(720, 800, true);
                return gwtApplicationConfiguration;
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Coursapied(new IStopper()
                {
                        @Override
                        public void stop()
                        {

                        }
                });
        }
}