public class HashFunction {

    public static int defaultHash(
            String key
    ) {

        int hash = 0;

        for (
                int i = 0;
                i < key.length();
                i++
        ) {

            hash =
                    31 * hash +
                    key.charAt(i);
        }

        return hash;
    }

    public static int djb2Hash(
            String key
    ) {

        int hash = 5381;

        for (
                int i = 0;
                i < key.length();
                i++
        ) {

            hash =
                    ((hash << 5) +
                    hash) +
                    key.charAt(i);
        }

        return hash;
    }

    public static int fnv1aHash(
            String key
    ) {

        int hash =
                0x811c9dc5;

        for (
                int i = 0;
                i < key.length();
                i++
        ) {

            hash ^=
                    key.charAt(i);

            hash *=
                    0x01000193;
        }

        return hash;
    }
}