import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample {

    public static void testSemaphore() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable task = () -> {
            boolean allow = false;
            try {
                allow = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (allow) {
                    System.out.println("Acquired");
                    TimeUnit.SECONDS.sleep(10);
                } else
                    System.out.println("Not Acquired");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (allow)
                    semaphore.release();
            }
        };

        IntStream.range(0, 10)
                .forEach(i -> service.submit(task));

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

}
