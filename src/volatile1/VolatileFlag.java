package volatile1;

import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class VolatileFlag {
    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("1. runFlag = " + task.runFlag);

        t.start();

        sleep(1000);
        log("3. runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("5. runFlag = " + task.runFlag);

        log("6. main 종료");
    }

    static class MyTask implements Runnable {

        boolean runFlag = true;
//        volatile boolean runFlag = true;

        @Override
        public void run() {
            log("2. task 시작");
            while (runFlag) {
                // runFlag가 false로 변하면 탈출
            }
            log("4. task 종료");
        }
    }
}