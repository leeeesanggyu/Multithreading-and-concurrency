package executor.poolsize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static executor.ExecutorUtils.printState;
import static utils.ThreadUtil.sleep;

/**
 * Thread Pool에 미리 Thread를 생성해두는 법
 */
public class PreStartPoolMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        printState(executorService);

        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executorService;
        poolExecutor.prestartAllCoreThreads();
        sleep(100);
        printState(executorService);
    }
}
