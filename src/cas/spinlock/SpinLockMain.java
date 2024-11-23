package cas.spinlock;

import static utils.Logger.log;

public class SpinLockMain {
    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();

        new Thread(() -> lockLogic(spinLock), "Thread-1").start();
        new Thread(() -> lockLogic(spinLock), "Thread-2").start();
    }

    private static void lockLogic(SpinLock spinLock) {
        spinLock.lock();

        try {
            // critical section
            log("비지니스 로직 실행");
        } finally {
            spinLock.unlock();
        }
    }
}
