package executor.reject;

import executor.RunnableTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectMainV3 {

    public static void main(String[] args) {
        ExecutorService excutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

        excutor.execute(new RunnableTask("task1"));
        excutor.execute(new RunnableTask("task2"));
        excutor.execute(new RunnableTask("task3"));
        excutor.execute(new RunnableTask("task4"));

        excutor.close();
    }
}
