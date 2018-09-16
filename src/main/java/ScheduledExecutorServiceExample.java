import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

    public static void testScheduledExecutorService() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.SECONDS.sleep((new Random()).nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished -- " + Thread.currentThread().getName());
        }, 3, 5, TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

}
