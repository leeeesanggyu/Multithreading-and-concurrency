package executor.future;

import java.util.concurrent.*;

import static utils.Logger.log;

public class SumTaskMainV1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> submit1 = executorService.submit(new SumTask(1, 50));
        Future<Integer> submit2 = executorService.submit(new SumTask(51, 100));

        Integer result1 = submit1.get(); // Blocking
        Integer result2 = submit2.get(); // Blocking
        log("result = " + (result1 + result2));
        executorService.close();
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("작업 시작");
            Thread.sleep(2000);

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }

            log("작업 완료 result = " + sum);
            return sum;
        }
    }
}
