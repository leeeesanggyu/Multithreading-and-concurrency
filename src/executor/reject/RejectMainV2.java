package executor.reject;

import executor.RunnableTask;

import java.util.concurrent.*;

import static utils.Logger.log;

public class RejectMainV2 {

    public static void main(String[] args) {
        ExecutorService excutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());

        excutor.execute(new RunnableTask("task1"));
        excutor.execute(new RunnableTask("task2"));
        excutor.execute(new RunnableTask("task3"));
        excutor.execute(new RunnableTask("task4"));

        excutor.close();
    }
}
