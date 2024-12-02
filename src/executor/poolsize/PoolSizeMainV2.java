package executor.poolsize;

import executor.RunnableTask;

import java.util.concurrent.*;

import static executor.ExecutorUtils.printState;
import static utils.Logger.log;

public class PoolSizeMainV2 {

//    static final int TASK_SIZE = 1100;  // 일반
    static final int TASK_SIZE = 1200;  // 긴급
//    static final int TASK_SIZE = 1201;  // 거절

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(100, 200, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        printState(executorService);

        long startMs = System.currentTimeMillis();

        for (int i = 0; i < TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                executorService.execute(new RunnableTask(taskName));
                printState(executorService, taskName);
            } catch (RejectedExecutionException e) {
                log(taskName + " -> " + e);
            }
        }

        executorService.close();
        long endMs = System.currentTimeMillis();
        log("time: " + (endMs - startMs));
    }
}
