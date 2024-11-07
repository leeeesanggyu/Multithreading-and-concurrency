package bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static utils.Logger.log;

public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생상자 대기 : " + data);
            try {
                this.wait(); // RUNNABLE -> WAITING, 락 반납하고 대기 상태에 빠짐
                log("[put] 생상자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            queue.offer(data);
        } finally {
            this.notify();  // WAIT -> BLOCKED
        }
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                this.wait(); // RUNNABLE -> WAITING, 락 반납하고 대기 상태에 빠짐
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return queue.poll();
        } finally {
            this.notify();  // WAIT -> BLOCKED
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
