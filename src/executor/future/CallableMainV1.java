package executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class CallableMainV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> result = executorService.submit(new MyCallable());
        log("result1 = " + result.state());
        log("result2 = " + result.get());
        log("result3 = " + result.state());

        executorService.close();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 시작");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("Callable 종료");
            return value;
        }
    }
}
