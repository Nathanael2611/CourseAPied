package fr.nathanael2611.coursapied;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerController implements InputProcessor
{

    public static int selected = 0;

    public static void increment()
    {

        selected --;
        if(selected < 0)
        {
            selected = 2;
        }
    }

    public static void decrement()
    {
        selected ++;
        if(selected > 2)
        {
            selected = 0;
        }
    }

    private final Player player;
    private final float zoom = 1.25f;

    public float getZoom()
    {
        return zoom;
    }

    public PlayerController(Player player)
    {

        this.player = player;
    }

    public void update()
    {

        if(Coursapied.INSTANCE.gameStarted())
        {
            if(Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                player.jump();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                player.slide();
            }

            if(player.getState() == State.RUNNING)
            {
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.desiredZ >= 0)
                {
                    player.desiredZ -= 8;
                }
                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.desiredZ <= 0)
                {
                    player.desiredZ += 8;
                }
            }
        }
        else
        {

            if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            {
                increment();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            {
                decrement();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            {
                if(selected == 0)
                {
                    Coursapied.INSTANCE.getPlayer().reset();
                    Coursapied.INSTANCE.startGame();
                }
                else if(selected == 1)
                {
                    if (Coursapied.INSTANCE.music.isPlaying())
                    {
                        Coursapied.INSTANCE.music.stop();
                    }
                    else
                    {
                        Coursapied.INSTANCE.music.setLooping(true);
                        Coursapied.INSTANCE.music.play();
                    }
                }
                else if(selected == 2)
                {
                    Coursapied.INSTANCE.stopper.stop();
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {


        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        System.out.println(character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }
}
