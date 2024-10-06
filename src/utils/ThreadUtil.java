package utils;

import static utils.Logger.log;

public abstract class ThreadUtil {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log("인터럽트 발생: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}