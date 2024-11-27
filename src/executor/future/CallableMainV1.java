package executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

/**
 * Future는 작업의 미래 결과를 받을 수 있는 객체이다.
 * submit() 호출 시 future는 즉시 반환된다. 덕분에 요청 스레드는 블로킹 되지않고 필요한 작업을 할 수 있다.
 *
 * Callable의 결과가 필요하면 Future.get()을 호출하면 된다.
 * 이때 Future가 완료상태라면 Future에 결과가 포함되어 있기 때문에 스레드는 대기하지 않고 값을 즉시 반환받는다.
 * Future가 완료상태가 아니라면 어쩔 수 없이 요청스레드가 결과값을 받기 위해 블로킹 상태로 대기해야한다.
 */
public class CallableMainV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        log("submit() 호출");
        Future<Integer> future = executorService.submit(new MyCallable());
        log("Future 즉시 반환, future = " + future);

        log("Future.get() [블로킹] 메소드 호출 시작 -> main 스레드 WAITING");
        Integer result = future.get();
        log("Future.get() [블로킹] 메소드 호출 완료 -> main 스레드 RUNNABLE");

        log("result value = " + result);
        log("Future 완료, future = " + future);
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
