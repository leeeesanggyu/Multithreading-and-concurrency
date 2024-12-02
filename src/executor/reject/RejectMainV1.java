package executor.reject;

import executor.RunnableTask;

import java.util.concurrent.*;

import static utils.Logger.log;

public class RejectMainV1 {

    public static void main(String[] args) {
        ExecutorService excutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());

        excutor.execute(new RunnableTask("task1"));

        try {
            excutor.execute(new RunnableTask("task2"));
        } catch (RejectedExecutionException e) {
            log("요청 초과");
            // 포기, 다시 시도 등 다양한 방법을 시도하면 됨
            log(e);
        }

        excutor.close();
    }
}
