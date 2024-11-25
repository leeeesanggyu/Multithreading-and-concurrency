package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.ExecutorUtils.printState;
import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class ExecutorBasicMain {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );
        log("== 초기 상태 ==");
        printState(executorService);

        // ExecutorService의 execute는 사실 Runnable을 실행하는게 아니라 ExecutorService에 있는 BlockingQueue에 작업을 비동기로 담는것이였음 (메인스레드는 생산자가 되는것)
        // ExecutorService 내부의 스레드풀에 있는 스레드들은 소비자가 되어 BlockingQueue에서 작업을 할당함
        executorService.execute(new RunnableTask("taskA"));
        executorService.execute(new RunnableTask("taskB"));
        executorService.execute(new RunnableTask("taskC"));
        executorService.execute(new RunnableTask("taskD"));
        log("== 작업 수행 중 ==");
        printState(executorService);

        sleep(3000);
        log("== 작업 수행 완료 ==");
        printState(executorService);

        executorService.close();
        log("== shutdown 완료 ==");
        printState(executorService);
    }
}
