import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StressTest {

    public static void main(
            String[] args
    ) throws InterruptedException {

        ConcurrentHashMap<Integer, Integer>
                map =
                new ConcurrentHashMap<>();

        int threadCount = 10;

        int operationsPerThread = 10000;

        ExecutorService executor =
                Executors.newFixedThreadPool(
                        threadCount
                );

        long startTime =
                System.currentTimeMillis();

        for (
                int i = 0;
                i < threadCount;
                i++
        ) {

            final int threadId = i;

            executor.submit(
                    () -> {

                        for (
                                int j = 0;
                                j < operationsPerThread;
                                j++
                        ) {

                            int key =
                                    threadId *
                                    operationsPerThread
                                    + j;

                            map.put(
                                    key,
                                    key
                            );

                            map.get(key);
                        }
                    }
            );
        }

        executor.shutdown();

        executor.awaitTermination(
                1,
                TimeUnit.MINUTES
        );

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                "Stress test completed"
        );

        System.out.println(
                "Expected size: " +
                threadCount *
                operationsPerThread
        );

        System.out.println(
                "Actual size: " +
                map.size()
        );

        System.out.println(
                "Capacity: " +
                map.capacity()
        );

        System.out.println(
                "Execution time: " +
                (endTime - startTime) +
                " ms"
        );

        if (
                map.size() ==
                threadCount *
                operationsPerThread
        ) {

            System.out.println(
                    "TEST PASSED"
            );

        } else {

            System.out.println(
                    "TEST FAILED"
            );
        }
    }
}