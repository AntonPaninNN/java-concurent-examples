import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorServiceExample {

    public static void testExecutorService() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futures.add(service.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep((new Random()).nextInt(5) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    return "Finished -- " + Thread.currentThread().getName();
                }

            }));
        }

        try {
            for (Future<String> future : futures) {
                while (!future.isDone()) {
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }
}
