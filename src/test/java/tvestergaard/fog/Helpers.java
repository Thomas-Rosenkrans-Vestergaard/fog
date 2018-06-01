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

    public static <T> T randomEnum(Class<T> type)
    {
        T[] values = type.getEnumConstants();

        return values[random.nextInt(values.length)];
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

    public static int randomInt(int min)
    {
        if (min == 0)
            return random.nextInt();

        return random.nextInt(min) + min;
    }

    public static int randomInt()
    {
        return random.nextInt();
    }

    public static float randomFloat()
    {
        return random.nextFloat();
    }
}
