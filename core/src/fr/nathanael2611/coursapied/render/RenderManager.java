package fr.nathanael2611.coursapied.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonReader;
import fr.nathanael2611.coursapied.Coursapied;
import fr.nathanael2611.coursapied.Player;
import fr.nathanael2611.coursapied.PlayerController;
import fr.nathanael2611.coursapied.level.JumpFence;
import fr.nathanael2611.coursapied.level.SlideFence;
import fr.nathanael2611.coursapied.level.Trash;

public class RenderManager
{

    private final RenderContext renderContext;
    public final Environment environment;
    public final PerspectiveCamera camera;
    public final G3dModelLoader modelLoader;
    public final ObjLoader objLoader;

    public boolean debugMode = false;

    private ModelBuilder modelBuilder;


    SpriteBatch spriteBatch = new SpriteBatch();
    ModelBatch batch = new ModelBatch();
    BitmapFont font;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public GenericModels models;

    Texture logo;


    public RenderManager()
    {
        this.camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(40, 40, 40);
        this.camera.lookAt(0, 0, 0);
        this.camera.near = 0.1f;
        this.camera.far = 130;

        this.camera.update();

        this.modelBuilder = new ModelBuilder();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0.93f, 0.93f, 0.91f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.ROUNDROBIN, 1));


        modelLoader = new G3dModelLoader(new JsonReader());
        objLoader = new ObjLoader();

        models = new GenericModels(this);

        this.font = new BitmapFont(Gdx.files.internal("font/fonttest.fnt"));

        this.logo = new Texture("l.png");
        JumpFence.initModel(this);
        SlideFence.initModel(this);
        Trash.initModel(this);


    }


    public ModelBuilder getModelBuilder()
    {
        return modelBuilder;
    }

    public void updateCam(float partialTicks)
    {
        Player player = Coursapied.INSTANCE.getPlayer();
        camera.position.set(player.getRenderX(partialTicks) - 10, 10, player.getRenderZ(partialTicks) - 5);
        camera.lookAt(player.getRenderX(partialTicks), 4, player.getRenderZ(partialTicks));
        camera.update();
    }

    public PerspectiveCamera getCamera()
    {
        return camera;
    }

    public String frameIfSelected(int id, String text)
    {
        return PlayerController.selected == id ? ("[  "+ text + "  ]") : text;
    }

    public void render(float partialTicks)
    {
        updateCam(partialTicks);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.93f, 0.93f, 0.91f, 1);


        renderContext.begin();

        batch.begin(camera);
        Coursapied.INSTANCE.getPlayer().render(this, partialTicks);
        Coursapied.INSTANCE.getActualLevel().render(this, partialTicks);
        batch.end();

        renderContext.end();

        if (!Coursapied.INSTANCE.gameStarted())
        {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(new Color(0, 0, 0, 0.7F));
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            spriteBatch.begin();
            spriteBatch.draw(this.logo, 10, Gdx.graphics.getHeight() - 300, 300, 300);

            font.getData().setScale(0.6F);
            font.draw(spriteBatch, "Course à pied", 10, Gdx.graphics.getHeight() - 340);
            font.getData().setScale(0.4F);
            font.setColor(PlayerController.selected == 0 ? Color.WHITE : Color.GRAY);
            font.draw(spriteBatch, frameIfSelected(0, "PLAY"), 40, Gdx.graphics.getHeight() - 410);
            font.setColor(PlayerController.selected == 1 ? Color.WHITE : Color.GRAY);
            font.draw(spriteBatch, frameIfSelected(1, "MUSIC: " + (Coursapied.INSTANCE.music.isPlaying() ? "ON" : "OFF")), 40, Gdx.graphics.getHeight() - 470);
            font.setColor(PlayerController.selected == 2 ? Color.WHITE : Color.GRAY);
            font.draw(spriteBatch, frameIfSelected(2, "LEAVE"), 40, Gdx.graphics.getHeight() - 530);
            font.setColor(Color.WHITE);

            spriteBatch.end();
        }
        else
        {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(new Color(0, 0, 0, 0.7F));
            shapeRenderer.rect(0, Gdx.graphics.getHeight() - 30, 150, 40);

            shapeRenderer.end();

            spriteBatch.begin();

            font.draw(spriteBatch, "Score: " + Coursapied.INSTANCE.getScore(), 2,  Gdx.graphics.getHeight() - 2);

            spriteBatch.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }





     /*   bgBatch.begin();
        bgBatch.draw(bg, 0, 0);
        bgBatch.end();

        batch.begin();
        batch.setProjectionMatrix(this.camera.combined);
        Level actualLevel = Coursapied.INSTANCE.getActualLevel();
        if (actualLevel != null)
        {
            if (actualLevel.isGameOver())
            {
                batch.draw(gameOver, -(89 * 4), -(89 * 4), 178 * 4, 178 * 4);
                font.draw(batch, "Cliquez sur Entrée pour continuer", -84 * 4, -90 * 4);
            } else
            {
                batch.draw(gameGui, -(89 * 4), -89 * 4, 178 * 4, 200 * 4);
                font.getData().setScale(3.2f);
                font.draw(batch, actualLevel.secondsRemaining + "s", 48 * 4, 98 * 4);
                font.draw(batch, Coursapied.INSTANCE.getScore() + "", -65 * 4, 98 * 4);
                batch.setColor(Helpers.toColor(actualLevel.getNeededColor()));
                batch.draw(Tile.TEXTURE, -74, 87 * 4, 55, 55);
                batch.setColor(Color.WHITE);
                batch.draw(check, -74, 87 * 4, 55, 55);
                if(actualLevel.getAvoidColor() != null)
                {
                    batch.setColor(Helpers.toColor(actualLevel.getAvoidColor()));
                }
                else
                {
                    batch.setColor(Color.BLACK);
                }
                batch.draw(Tile.TEXTURE, 4, 87 * 4, 55, 55);
                batch.setColor(Color.WHITE);
                batch.draw(cross, 4, 87 * 4, 55, 55);
                font.draw(batch, "Maintenez entrée pour accélérer le temps", -100 * 4, -90 * 4);
            }
        } else
        {
            batch.draw(menu, -(89 * 4), -(89 * 4), 178 * 4, 178 * 4);
            Difficulty difficulty = Coursapied.INSTANCE.getDifficulty();
            float x = -89 * 4, y;
            switch (difficulty)
            {
                case EASY:
                    y = -11 * 4;
                    break;
                case MEDIUM:
                    y = -50 * 4;
                    break;
                case HARD:
                    y = -89 * 4;
                    break;
                default:
                    y = 0;
            }
            batch.draw(selector, x, y, 178 * 4, 34 * 4);
            font.getData().setScale(3);
            font.draw(batch, "Cliquez sur Entrée pour lancer le jeu", -84 * 4, -90 * 4);
        }

        batch.end();

        if (Coursapied.INSTANCE.getActualLevel() != null && !Coursapied.INSTANCE.getActualLevel().isGameOver())
        {
            Coursapied.INSTANCE.getActualLevel().render(this, partialTicks);
            Coursapied.INSTANCE.getPlayer().render(this, partialTicks);
        }*/
    }
}