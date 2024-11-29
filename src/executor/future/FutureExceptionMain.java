package executor.future;

import java.util.concurrent.*;

import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = executorService.submit(new ExceptionCallable());
        sleep(1000);

        try {
            log("future.get() 호출 시도, future.state(): " + future.state());
            Integer result = future.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) { // Callable이 실행될때 예외가 터지는것은 ExecutionException으로 예외를 전달받을 수 있음
            log("e = " + e);
            log("cause = " + e.getCause()); // 원본 예외
        }

        executorService.close();
    }

    static class ExceptionCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("exception!");
        }
    }
}
