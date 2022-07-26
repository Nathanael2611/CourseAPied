package fr.nathanael2611.coursapied.util;

import com.badlogic.gdx.math.Vector3;

public enum EnumFacing
{
    DOWN (),
    UP   (),
    NORTH(),
    SOUTH(),
    WEST (),
    EAST ();

    private EnumFacing()
    {

    }


    public Vector3 getNormals()
    {
        if(this == EnumFacing.UP )
        {
            return new Vector3(0, 1, 0).nor();
        }
        else if( this == DOWN)
        {
            return new Vector3(0, -1, 0).nor();
        }
        else if(this == EnumFacing.NORTH )
        {
            return new Vector3(0,0, -1).nor();
        }
        else if(this == EnumFacing.SOUTH)
        {
            return new Vector3(0,0, 1).nor();
        }
        else if(this == EnumFacing.EAST )
        {
            return new Vector3(1,0, 0).nor();
        }
        else if(this == EnumFacing.WEST)
        {
            return new Vector3(-1,0, 0).nor();
        }

        return new Vector3(0,0, 1).nor();
    }

    public Vector3[] getCorners()
    {
        Vector3[] vecs = new Vector3[4];
        if(this == EnumFacing.UP)
        {
            vecs[0] = new Vector3(0, 1, 0);
            vecs[1] = new Vector3(0, 1, 1);
            vecs[2] = new Vector3(1, 1, 1);
            vecs[3] = new Vector3(1, 1, 0);
        }
        else if(this == EnumFacing.DOWN)
        {
            vecs[0] = new Vector3(0, 0, 0);
            vecs[1] = new Vector3(1, 0, 0);
            vecs[2] = new Vector3(1, 0, 1);
            vecs[3] = new Vector3(0, 0, 1);
        }
        else if(this == EnumFacing.SOUTH)
        {
            vecs[0] = new Vector3(0, 0, 1);
            vecs[1] = new Vector3(1, 0, 1);
            vecs[2] = new Vector3(1, 1, 1);
            vecs[3] = new Vector3(0, 1, 1);

        }
        else if(this == EnumFacing.NORTH)
        {
            /*vecs[0] = new Vector3(0, 0, 0);
            vecs[1] = new Vector3(0, 1, 0);
            vecs[2] = new Vector3(1, 1, 0);
            vecs[3] = new Vector3(1, 0, 0);*/
            vecs[0] = new Vector3(1, 0, 0);
            vecs[1] = new Vector3(0, 0, 0);
            vecs[2] = new Vector3(0, 1, 0);
            vecs[3] = new Vector3(1, 1, 0);
        }
        else if(this == EnumFacing.WEST)
        {
            vecs[0] = new Vector3(0, 0, 0);
            vecs[1] = new Vector3(0, 0, 1);
            vecs[2] = new Vector3(0, 1, 1);
            vecs[3] = new Vector3(0, 1, 0);
        }
        else if(this == EnumFacing.EAST)
        {
           /* vecs[0] = new Vector3(1, 0, 0);
            vecs[1] = new Vector3(1, 1, 0);
            vecs[2] = new Vector3(1, 1, 1);
            vecs[3] = new Vector3(1, 0, 1);*/

            vecs[0] = new Vector3(1, 0, 1);
            vecs[1] = new Vector3(1, 0, 0);
            vecs[2] = new Vector3(1, 1, 0);
            vecs[3] = new Vector3(1, 1, 1);
        }
        return vecs;
    }
}