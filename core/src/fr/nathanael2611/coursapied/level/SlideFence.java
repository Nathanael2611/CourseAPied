package fr.nathanael2611.coursapied.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import fr.nathanael2611.coursapied.State;
import fr.nathanael2611.coursapied.render.RenderManager;

public class SlideFence extends Obstacle
{

    private static ModelInstance instance;
    private static ModelBatch batch;

    public SlideFence(float x, float z)
    {
        super(x, z, 30);
    }

    @Override
    public State neededState()
    {
        return State.SLIDING;
    }

    @Override
    public void render(RenderManager renderManager, float partialTicks)
    {
        instance.transform.setToTranslation(x, 1, z);
        instance.transform.rotate(0, 1, 0, 90);
        instance.transform.scale(1.4f, 1, 1);
        batch.begin(renderManager.camera);
        batch.render(instance, renderManager.environment);

        renderHitbox(renderManager, partialTicks, batch);
        batch.end();
    }

    public static void initModel(RenderManager renderManager)
    {
        instance = new ModelInstance(renderManager.objLoader.loadModel(Gdx.files.internal("obstacle/slidefence.obj")));
        batch = new ModelBatch();



    }
}
