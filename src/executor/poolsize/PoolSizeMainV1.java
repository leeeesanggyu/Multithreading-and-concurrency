package executor.poolsize;

import executor.RunnableTask;

import java.util.concurrent.*;

import static executor.ExecutorUtils.printState;
import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class PoolSizeMainV1 {

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ExecutorService executorService = new ThreadPoolExecutor(2, 4,
                3_000, TimeUnit.MILLISECONDS, workQueue);
        printState(executorService);

        executorService.execute(new RunnableTask("task1"));
        printState(executorService, "task1");

        executorService.execute(new RunnableTask("task2"));
        printState(executorService, "task2");

        executorService.execute(new RunnableTask("task3"));
        printState(executorService, "task3");

        executorService.execute(new RunnableTask("task4"));
        printState(executorService, "task4");

        executorService.execute(new RunnableTask("task5")); // 작업 큐까지 가득 찼을때(초과 스레드) maximumPoolSize만큼 스레드가 늘어나기 시작한다.
        printState(executorService, "task5");

        executorService.execute(new RunnableTask("task6"));
        printState(executorService, "task6");

        try {
            executorService.execute(new RunnableTask("task7"));
            printState(executorService, "task7");
        } catch (RejectedExecutionException e) {
            log("task7 실행 거절 예외 발생: " + e);
        }

        sleep(3000);
        log("== 작성 수행 완료 ==");
        printState(executorService);

        sleep(3000);
        log("== maximumPoolSize keepAliveTime 시간 초과  ==");
        printState(executorService);
    }
}
