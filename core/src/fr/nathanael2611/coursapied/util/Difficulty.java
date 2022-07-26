package fr.nathanael2611.coursapied.util;

public enum Difficulty
{

    EASY(8), MEDIUM(10), HARD(16);

    private int seconds;

    Difficulty(int seconds)
    {
        this.seconds = seconds;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public Difficulty increase()
    {
        return this == EASY ? MEDIUM : this == MEDIUM ? HARD : EASY;
    }

    public Difficulty decrease()
    {
        return this == HARD ? MEDIUM : this == MEDIUM ? EASY : HARD;
    }
}
