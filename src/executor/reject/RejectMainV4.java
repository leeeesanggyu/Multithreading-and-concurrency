package executor.reject;

import executor.RunnableTask;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static utils.Logger.log;

public class RejectMainV4 {

    public static void main(String[] args) {
        ExecutorService excutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new MyRejectedExecutionHandler());

        excutor.execute(new RunnableTask("task1"));
        excutor.execute(new RunnableTask("task2"));
        excutor.execute(new RunnableTask("task3"));
        excutor.execute(new RunnableTask("task4"));

        excutor.close();
    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        static AtomicInteger count = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            count.incrementAndGet();
            log("rejected count = " + count);
        }
    }
}
