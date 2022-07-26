package fr.nathanael2611.coursapied.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import fr.nathanael2611.coursapied.util.EnumFacing;

public class GenericModels
{

    private ModelBuilder builder;

    public final ModelInstance hitBox;
    public final ModelInstance coolHitBox;
    public final ModelInstance selectedBox;

    public GenericModels(RenderManager renderManager)
    {
        this.builder = renderManager.getModelBuilder();

        this.hitBox = createHitBoxModel();
        this.coolHitBox = createCoolHitBoxModel();
        this.selectedBox = createSelectedBox();
    }

    private ModelInstance createHitBoxModel()
    {
        Material material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        ColorAttribute cat = ColorAttribute.createDiffuse(new Color(1, 0, 0.5f, 0.3F));
        Material material2 = new Material(cat, new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        this.builder.begin();
        MeshPartBuilder meshPartBuilder = this.builder.part("lines", GL20.GL_LINES, VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position, material);

        meshPartBuilder.line(0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F);
        meshPartBuilder.line(0.5F, -0.5F, 0.5F, -0.5F, -0.5F, 0.5F);
        meshPartBuilder.line(0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F);
        meshPartBuilder.line(0.5F, -0.5F, -0.5F, -0.5F, -0.5F, -0.5F);

        meshPartBuilder.line(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, -0.5F);
        meshPartBuilder.line(0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F);
        meshPartBuilder.line(-0.5F, 0.5F, 0.5F, -0.5F, 0.5F, -0.5F);
        meshPartBuilder.line(-0.5F, -0.5F, 0.5F, -0.5F, -0.5F, -0.5F);

        meshPartBuilder.line(0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F);
        meshPartBuilder.line(-0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F);
        meshPartBuilder.line(-0.5F, 0.5F, -0.5F, -0.5F, -0.5F, -0.5F);
        meshPartBuilder.line(0.5F, 0.5F, -0.5F, 0.5F, -0.5F, -0.5F);

        meshPartBuilder = this.builder.part("rects", GL20.GL_TRIANGLES, VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position, material2);


        for (EnumFacing value : EnumFacing.values())
        {
            Vector3[] corners = value.getCorners();
            for (int i = 0; i < corners.length; i++)
            {
                Vector3 corner = corners[i];
                corners[i] = corner.sub(0.5F);
            }
            meshPartBuilder.rect(corners[0], corners[1], corners[2], corners[3], value.getNormals());
        }
        Model model = this.builder.end();
        return new ModelInstance(model);
    }

    private ModelInstance createCoolHitBoxModel()
    {
        Material material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        ColorAttribute cat = ColorAttribute.createDiffuse(new Color(1, 0, 0.5f, 0.3F));
        Material material2 = new Material(cat, new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        this.builder.begin();
        MeshPartBuilder meshPartBuilder = this.builder.part("lines", GL20.GL_LINES, VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position, material);

        meshPartBuilder.line(1F, 1F, 1F, 0F, 1F, 1F);
        meshPartBuilder.line(1F, 0F, 1F, 0F, 0F, 1F);
        meshPartBuilder.line(1F, 1, -0, -0, 1, -0);
        meshPartBuilder.line(1F, -0, -0, -0, -0, -0);

        meshPartBuilder.line(1, 1, 1, 1, 1, -0);
        meshPartBuilder.line(1, -0, 1, 1, -0, -0);
        meshPartBuilder.line(-0, 1, 1, -0, 1, -0);
        meshPartBuilder.line(-0, -0, 1, -0, -0, -0);

        meshPartBuilder.line(1, 1F, 1, 1, -0, 1);
        meshPartBuilder.line(0, 1F, 1, -0, -0, 1);
        meshPartBuilder.line(0, 1F, -0, -0, -0, -0);
        meshPartBuilder.line(1F, 1F, -0, 1, -0, -0);

        meshPartBuilder = this.builder.part("rects", GL20.GL_TRIANGLES, VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position, material2);


        for (EnumFacing value : EnumFacing.values())
        {
            Vector3[] corners = value.getCorners();
            for (int i = 0; i < corners.length; i++)
            {
                Vector3 corner = corners[i];
                // corners[i] = corner.sub(0.5F);
            }
            meshPartBuilder.rect(corners[0], corners[1], corners[2], corners[3], value.getNormals());
        }
        Model model = this.builder.end();
        return new ModelInstance(model);
    }


    private ModelInstance createSelectedBox()
    {
        Material material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        this.builder.begin();
        MeshPartBuilder meshPartBuilder = this.builder.part("lines", GL20.GL_LINES, VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position, material);
        meshPartBuilder.line(1F, 1F, 1F, 0F, 1F, 1F);
        meshPartBuilder.line(1F, 0F, 1F, 0F, 0F, 1F);
        meshPartBuilder.line(1F, 1, -0, -0, 1, -0);
        meshPartBuilder.line(1F, -0, -0, -0, -0, -0);

        meshPartBuilder.line(1, 1, 1, 1, 1, -0);
        meshPartBuilder.line(1, -0, 1, 1, -0, -0);
        meshPartBuilder.line(-0, 1, 1, -0, 1, -0);
        meshPartBuilder.line(-0, -0, 1, -0, -0, -0);

        meshPartBuilder.line(1, 1F, 1, 1, -0, 1);
        meshPartBuilder.line(0, 1F, 1, -0, -0, 1);
        meshPartBuilder.line(0, 1F, -0, -0, -0, -0);
        meshPartBuilder.line(1F, 1F, -0, 1, -0, -0);
        Model model = this.builder.end();
        return new ModelInstance(model);
    }

}
