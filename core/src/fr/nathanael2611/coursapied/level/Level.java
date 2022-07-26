package fr.nathanael2611.coursapied.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import fr.nathanael2611.coursapied.Coursapied;
import fr.nathanael2611.coursapied.Player;
import fr.nathanael2611.coursapied.render.RenderManager;
import fr.nathanael2611.coursapied.util.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level
{

    private Random rand = new Random();


    private Player player;
    private ModelBatch batch = new ModelBatch();
    private ModelInstance model;

    private List<Obstacle> obstacles = new ArrayList<>();


    private boolean gameOver;
    public boolean gameStarted = false;

    public Level(Player player)
    {
        this.player = player;
        this.obstacles = new ArrayList<>();
        Model model = Coursapied.INSTANCE.renderManager.objLoader.loadModel(Gdx.files.internal("terrain/terrain.obj"));
        this.model = new ModelInstance(model);

    }

    public int lastPlayerOffset = 0;

    public Obstacle previousSpawn = null;
    public Obstacle lastSpawn = null;

    float globalPluser = 0;

    public void spawn(Obstacle obstacle)
    {
        float pluser = 0;
        if(obstacle instanceof SlideFence && lastSpawn instanceof SlideFence)
        {
            pluser += 15;
            if(previousSpawn instanceof SlideFence)
            {
                pluser += 10;
            }
        }
        if(obstacle instanceof SlideFence && lastSpawn instanceof JumpFence && previousSpawn instanceof SlideFence)
        {
            pluser += 30;

        }

        if(obstacle instanceof JumpFence && lastSpawn instanceof SlideFence)
        {
            pluser += 20;
        }


       globalPluser += pluser;
        obstacle.x += globalPluser;
        if(!(obstacle instanceof Trash))
        {
            if(lastSpawn != null)
            {
                trySpawnTrash(lastSpawn.x + ( obstacle.x - lastSpawn.x) / 2);
                trySpawnTrash(lastSpawn.x + ( obstacle.x - lastSpawn.x) / 2);
            }
        }
        obstacles.add(lastSpawn = obstacle);
        previousSpawn = lastSpawn;

    }

    public void tick()
    {
        if (isGameOver() || !Coursapied.INSTANCE.gameStarted()) return;

        int playerOffset = getPlayerOffset();
        if(playerOffset != lastPlayerOffset)
        {
            Obstacle last = Obstacle.random(player.x + 100);
            spawn(last);


            Obstacle random = Obstacle.random(player.x + 120);


            spawn(random);

            spawn(Obstacle.random(player.x + 138 ));
        }

        lastPlayerOffset = playerOffset;

    }

    private void trySpawnTrash(float x)
    {
        if(rand.nextInt(30) < 10)
        {
            List<Integer> poses = new ArrayList<>();
            poses.add(8);
            poses.add(-8);
            poses.add(0);
            spawn(new Trash(x, (float) Helpers.randomFrom(poses)));

        }
    }

    public void gameOver()
    {
        this.gameOver = true;
        Coursapied.INSTANCE.createLevel(Coursapied.INSTANCE.getPlayer());
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public float offsetPos(int offset)
    {
        return offset * 41.7954f * 2;
    }

    public int offsetForPos(float x)
    {
        return (int) Math.ceil(x / (41.7954F * 2));
    }

    public void initModelInstance(ModelInstance instance, int offset)
    {
        instance.transform.setToTranslation(0, -1, 0);
        instance.transform.rotate(0, 1, 0, -90);
        instance.transform.translate(0, 0, -offsetPos(offset));
    }

    public int getPlayerOffset()
    {
        return offsetForPos(((player.x) - (41.7954F)));
    }

    public void render(RenderManager renderManager, float partialTicks)
    {
        this.batch.begin(renderManager.camera);
        this.initModelInstance(model, getPlayerOffset());
        this.batch.render(model, renderManager.environment);
        this.initModelInstance(model, getPlayerOffset() + 1);
        this.batch.render(model, renderManager.environment);
        this.initModelInstance(model, getPlayerOffset() + 2);
        this.batch.render(model, renderManager.environment);
        this.initModelInstance(model, getPlayerOffset() - 1);
        this.batch.render(model, renderManager.environment);
        this.batch.end();


        for (int i = 0; i < obstacles.size(); i++)
        {
            obstacles.get(i).render(renderManager, partialTicks);
        }

    }

    public List<Obstacle> getObstacles()
    {
        return obstacles;
    }
}
