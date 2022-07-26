package fr.nathanael2611.coursapied;

import fr.nathanael2611.coursapied.util.Timer;

public class GameLoop
{

    private final Timer timer;

    public GameLoop(Timer timer)
    {
        this.timer = timer;
    }

    public void updateLoop()
    {
        for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); ++j)
        {
            this.update();
        }
    }

    private void update()
    {
        Coursapied.INSTANCE.getPlayer().update();
        if (Coursapied.INSTANCE.getActualLevel() != null)
        {
            Coursapied.INSTANCE.getActualLevel().tick();
        }
    }

}