package fr.nathanael2611.coursapied.level;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import fr.nathanael2611.coursapied.State;
import fr.nathanael2611.coursapied.render.RenderManager;
import fr.nathanael2611.coursapied.util.BoundingBox;

import java.util.Random;

public abstract class Obstacle
{

    public float x, z, width;

    public Obstacle(float x, float z, float width)
    {
        this.x = x;
        this.z = z;
        this.width = width;
    }

    public BoundingBox getBox()
    {
        return new BoundingBox(x - 0.2f, z - width / 2, x +0.2F, z + width /2);
    }

    public abstract State neededState();

    public abstract void render(RenderManager renderManager, float partialTicks);

    public void renderHitbox(RenderManager renderManager, float partialTicks, ModelBatch batch)
    {
        if(renderManager.debugMode){
            ModelInstance instance = renderManager.models.hitBox;
            instance.transform.setToTranslation(x, 0, z);
            instance.transform.scale((float) getBox().getWidthX(), (float) 1, (float) getBox().getWidthZ());
            batch.render(instance, renderManager.environment);
        }
    }

    public static Obstacle random(float x)
    {
        if (new Random().nextBoolean())
        {
            return new SlideFence(x, 0);
        }
        else
        {
            return new JumpFence(x, 0);
        }
    }

}
