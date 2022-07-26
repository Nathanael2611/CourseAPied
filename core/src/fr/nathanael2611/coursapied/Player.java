package fr.nathanael2611.coursapied;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector2;
import fr.nathanael2611.coursapied.level.Obstacle;
import fr.nathanael2611.coursapied.render.RenderManager;
import fr.nathanael2611.coursapied.util.AnimListener;
import fr.nathanael2611.coursapied.util.BoundingBox;

public class Player
{

    private final ModelBatch batch;
    private final ModelInstance instance;
    private final AnimationController controller;

    private State state = State.RUNNING;

    public float prevX, prevZ, x, z, width = 2;

    public float speed = 1.1f;

    public float motionX = 0, motionZ = 0;

    public float desiredZ = 0;

    Sound woosh, jump, die;



    public Player()
    {
        this.batch = new ModelBatch();


        Model model = Coursapied.INSTANCE.renderManager.modelLoader.loadModel(Gdx.files.internal("player/player.g3dj"));
        instance = new ModelInstance(model);

        controller = new AnimationController(instance);

        controller.setAnimation("Armature|STAND", Integer.MAX_VALUE);

        this.woosh = Gdx.audio.newSound(Gdx.files.internal("smoosh.wav"));
        this.jump = Gdx.audio.newSound(Gdx.files.internal("jump.ogg"));
        this.die = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
    }

    public void makeRun()
    {
        speed = 1.1f;
        controller.setAnimation("Armature|RUN2", Integer.MAX_VALUE);
    }

    public BoundingBox getBox()
    {
        return new BoundingBox(x - width / 2, z - width / 2, x + width / 2, z + width / 2);
    }


    public void update()
    {
        this.prevX = x;
        this.prevZ = z;



        if(Coursapied.INSTANCE.getActualLevel().gameStarted)
        {

            for (int i = 0; i < Coursapied.INSTANCE.getActualLevel().getObstacles().size(); i++)
            {
                Obstacle obstacle = Coursapied.INSTANCE.getActualLevel().getObstacles().get(i);
                if(obstacle.getBox().intersects(this.getBox()))
                {
                    if(obstacle.neededState() != state)
                    {
                        die();
                    }
                }

                if(obstacle.x < x - 40)
                {
                    Coursapied.INSTANCE.getActualLevel().getObstacles().remove(i);
                }
            }
            float speed  =this.speed;

            if(motionX < speed)
            {
                motionX = Math.min(speed, motionX + speed * 0.2f);
            }
            else if(motionX > speed)
            {
                motionX = Math.max(speed, motionX - speed * 0.5f);
            }

            if(z < desiredZ)
            {
                z = Math.min(desiredZ, z + speed * 1f);
            }
            else if(z > desiredZ)
            {
                z = Math.max(desiredZ, z - speed * 1f);
            }

            if(motionX > 0)
            {
                x += Math.max(motionX, 0);
            }

        }



    }

    public void die()
    {
        die.play(1);

        speed = 0;
        controller.setAnimation("Armature|DIE", new AnimListener().onEnd(() ->
        {
            Coursapied.INSTANCE.getActualLevel().gameOver();
        }));
        sliding = false;
        jumping = false;
    }

    boolean jumping = false;
    public void jump()
    {
        if(state != State.RUNNING || controller.current.animation.id.equals("Armature|DIE")) return;
        if(jumping) return;
        jumping = true;
        jump.play(1);

        speed = 0.8f;
        controller.setAnimation("Armature|JUMP", new AnimListener().onEnd(() ->
        {
            makeRun();
            state = State.RUNNING;
            jumping = false;
            sliding = false;
        }));

    }

    public State getState()
    {
        return state;
    }

    boolean sliding = false;
    public void slide()
    {
        if(state != State.RUNNING || controller.current.animation.id.equals("Armature|DIE")) return;
        if(sliding) return;
        woosh.play(1);
        speed = 1.5f;
        sliding = true;
        controller.setAnimation("Armature|SLIDE", new AnimListener().onEnd(() ->
        {
            makeRun();
            state = State.RUNNING;
            sliding = false;
            jumping = false;
        }));
    }

    public Vector2 getPositionVector()
    {
        return new Vector2(this.x, this.z);
    }

    public float getRenderX(float partialTicks)
    {
        return prevX + (x - prevX) * partialTicks;
    }

    public float getRenderZ(float partialTicks)
    {
        return prevZ + (z - prevZ) * partialTicks;
    }

    public void render(RenderManager renderManager, float partialTicks)
    {

        String animId = controller.current.animation.id;


        if(animId.equals("Armature|JUMP"))
        {
            if(state != State.JUMPING && controller.current.time > 0.2 && controller.current.time < 0.8)
            {
                state = State.JUMPING;
                speed = 1.1f;
            }


            if(state == State.JUMPING && controller.current.time > 0.7)
            {
                state = State.RUNNING;
            }


        }

        if(animId.equals("Armature|SLIDE"))
        {
            if(state != State.SLIDING && controller.current.time > 0.2 && controller.current.time < 0.8)
            {
                state = State.SLIDING;
            }
            if(state == State.SLIDING && controller.current.time > 0.9)
            {
                state = State.RUNNING;

            }

        }

        controller.update(Gdx.graphics.getDeltaTime());
        instance.transform.setToTranslation(getRenderX(partialTicks), 0, getRenderZ(partialTicks));
        instance.transform.rotate(0, 1, 0, 180);
        batch.begin(renderManager.camera);

        batch.render(instance, renderManager.environment);

        if(renderManager.debugMode){
            ModelInstance instance = renderManager.models.hitBox;
            instance.transform.setToTranslation(this.getRenderX(partialTicks), 0, this.getRenderZ(partialTicks));
            instance.transform.scale((float) width, (float) 1, (float) width);
            batch.render(instance, renderManager.environment);
        }

        batch.end();


    }

    public void reset()
    {
        x = 0;
        z = 0;
        state = State.RUNNING;
        desiredZ = 0;
        sliding = false;
        jumping = false;

    }

    public int getScore()
    {
        return 0;
    }
}