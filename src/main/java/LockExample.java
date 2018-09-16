import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    public static void testLock() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        ReentrantLock lock = new ReentrantLock();

        Callable<?> lockTask = () -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } finally {
                lock.unlock();
            }
            return "finished";
        };

        Runnable monitoringTask = () -> {
            System.out.println("Is locked: " + lock.isLocked());
            System.out.println("Is held by current thread: " + lock.isHeldByCurrentThread());
            System.out.println("Lock acquired: " + lock.tryLock());
        };

        service.submit(lockTask);
        service.submit(monitoringTask);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

}
