public class Main {

    public static void main(
            String[] args
    ) {

        ConcurrentHashMap<String, Integer>
                map =
                new ConcurrentHashMap<>();

        map.put(
                "Java",
                100
        );

        map.put(
                "JavaScript",
                200
        );

        map.put(
                "Node.js",
                300
        );

        System.out.println(
                "Java: " +
                map.get("Java")
        );

        System.out.println(
                "JavaScript: " +
                map.get("JavaScript")
        );

        map.remove(
                "Node.js"
        );

        System.out.println(
                "Node.js: " +
                map.get("Node.js")
        );

        System.out.println(
                "Size: " +
                map.size()
        );

        System.out.println(
                "Capacity: " +
                map.capacity()
        );

        System.out.println(
                "Load Factor: " +
                map.getLoadFactor()
        );
    }
}