package fr.nathanael2611.coursapied.util;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimListener implements AnimationController.AnimationListener
{

    private Runnable onEnd, onLoop;

    public AnimListener()
    {
        this.onEnd = null;
        this.onLoop = null;
    }

    public AnimListener onLoop(Runnable onLoop)
    {
        this.onLoop = onLoop;
        return this;
    }
    public AnimListener onEnd(Runnable onEnd)
    {
        this.onEnd = onEnd;
        return this;
    }

    @Override
    public void onEnd(AnimationController.AnimationDesc animation)
    {
        if(this.onEnd != null)
        {
            onEnd.run();
        }

    }

    @Override
    public void onLoop(AnimationController.AnimationDesc animation)
    {
        if(this.onLoop != null)
            onLoop.run();
    }
}
