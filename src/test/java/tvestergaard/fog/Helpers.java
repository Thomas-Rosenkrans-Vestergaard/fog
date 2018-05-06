package tvestergaard.fog;

import java.util.Random;

public class Helpers
{

    private static Random random = new Random();

    public static String randomString(int size)
    {
        final String  ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder              = new StringBuilder();
        while (size-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomString()
    {
        return randomString(10);
    }

    public static boolean randomBoolean()
    {
        return random.nextBoolean();
    }

    public static int randomInt(int min, int max)
    {
        return random.nextInt((max - min) + 1) + min;
    }

    public static float randomFloat()
    {
        return random.nextFloat();
    }
}
