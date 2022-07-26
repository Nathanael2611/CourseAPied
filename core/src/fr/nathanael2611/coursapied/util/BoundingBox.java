package fr.nathanael2611.coursapied.util;

public class BoundingBox
{

    public float minX, minZ, maxX, maxZ;



    public BoundingBox(float minX, float minZ, float maxX, float maxZ)
    {
        this.minX = Math.min(minX, maxX);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxZ = Math.max(minZ, maxZ);
    }

    public float getWidthX()
    {
        return this.maxX - this.minX;
    }

    public float getWidthZ()
    {
        return this.maxZ - this.minZ;
    }

    public boolean containsPoint(float x, float z)
    {
        return x >= this.minX && x <= this.maxX
                && z >= this.minZ && z <= this.maxZ;
    }


    public boolean intersects(BoundingBox other)
    {
        return this.intersects(other.minX,  other.minZ, other.maxX, other.maxZ);
    }

    public boolean intersects(double x1, double z1, double x2, double z2)
    {
        return this.minX < x2 && this.maxX > x1  && this.minZ < z2 && this.maxZ > z1;
    }
}
