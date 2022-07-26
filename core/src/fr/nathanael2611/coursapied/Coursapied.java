package fr.nathanael2611.coursapied;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import fr.nathanael2611.coursapied.level.Level;
import fr.nathanael2611.coursapied.render.RenderManager;
import fr.nathanael2611.coursapied.util.Difficulty;
import fr.nathanael2611.coursapied.util.IStopper;
import fr.nathanael2611.coursapied.util.Timer;

public class Coursapied extends ApplicationAdapter
{

    public static Coursapied INSTANCE;

    public IStopper stopper;

    private Timer timer;
    private GameLoop gameLoop;
    public RenderManager renderManager;
    private Player player;
    private Level actualLevel;
    public PlayerController processor;

    public Music music;

    private Difficulty choosedDifficulty = Difficulty.EASY;

    public Coursapied(IStopper stopper)
    {
        INSTANCE = this;
        this.stopper = stopper;
    }

    public boolean gameStarted()
    {
        return actualLevel != null && actualLevel.gameStarted;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Level getActualLevel()
    {
        return actualLevel;
    }

    public Timer getTimer()
    {
        return timer;
    }

    @Override
    public void create()
    {
        super.create();
        this.timer = new Timer(20);
        this.gameLoop = new GameLoop(this.timer);
        this.renderManager = new RenderManager();

        this.player = new Player();
        this.processor = new PlayerController(this.player);
        Gdx.input.setInputProcessor(this.processor);

        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
        this.music.setLooping(true);
        this.music.setVolume(0.4f);
        this.music.play();


        createLevel(player);

    }

    @Override
    public void render()
    {
        this.update();
        super.render();
        this.renderManager.render(this.timer.renderPartialTicks);
    }

    private void update()
    {
        this.timer.updateTimer();
        this.processor.update();
        this.gameLoop.updateLoop();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    public Difficulty getDifficulty()
    {
        return this.choosedDifficulty;
    }

    public void increaseDifficulty()
    {
        this.choosedDifficulty = this.choosedDifficulty.increase();
    }

    public void decreaseDifficulty()
    {
        this.choosedDifficulty = this.choosedDifficulty.decrease();
    }

    public int getScore()
    {
        return player.getScore();
    }

    public void createLevel(Player player)
    {
        this.actualLevel = new Level(player);
    }


    public void startGame()
    {
        actualLevel.gameStarted = true;
        player.makeRun();
    }
}
