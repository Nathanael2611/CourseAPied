package fr.nathanael2611.coursapied;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fr.nathanael2611.coursapied.util.IStopper;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher
{

    public static int VIEWPORT_WIDTH;
    public static int VIEWPORT_HEIGHT;

    public static void main(String[] args)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        VIEWPORT_WIDTH = (int) (850);
        VIEWPORT_HEIGHT = (int) (850);
        config.setTitle("COURSE à PIED");
        config.setWindowedMode(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        config.setResizable(false);
        config.setWindowIcon("l.png");
        new Lwjgl3Application(new Coursapied(new IStopper()
        {
            @Override
            public void stop()
            {
                System.exit(0);
            }
        }), config);
    }
}
