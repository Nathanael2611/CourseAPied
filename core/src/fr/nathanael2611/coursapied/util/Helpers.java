package fr.nathanael2611.coursapied.util;

import java.util.List;
import java.util.Random;

public class Helpers
{


    public static <T> T randomFrom(List<T> array)
    {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

}
