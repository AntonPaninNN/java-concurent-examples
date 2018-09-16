import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    public static void testReadWriteLock() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Map<String, String> map = new HashMap<>();

        Runnable writeTask = () -> {
            try {
                lock.writeLock().lock();
                map.put("key", "value");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        };

        Runnable readTask = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                lock.readLock().lock();
                System.out.println("The Value is: " + map.get("key"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        };

        service.submit(writeTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

}
