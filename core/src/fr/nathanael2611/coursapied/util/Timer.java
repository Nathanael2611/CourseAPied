package fr.nathanael2611.coursapied.util;

public class Timer
{
    public int elapsedTicks;

    public float renderPartialTicks;
    public float elapsedPartialTicks;
    private long lastSync;
    private float tickLength;

    public Timer(float tps)
    {
        this.tickLength = 1000 / tps;
        this.lastSync = System.currentTimeMillis();
    }

    public void updateTimer()
    {
        long i = System.currentTimeMillis();
        this.elapsedPartialTicks = (float)(i - this.lastSync) / this.tickLength;
        this.lastSync = i;
        this.renderPartialTicks += this.elapsedPartialTicks;
        this.elapsedTicks = (int)this.renderPartialTicks;
        this.renderPartialTicks -= (float)this.elapsedTicks;
    }
}