package bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static utils.Logger.log;

/**
 * put()과 take()의 synchronized는 같은 클래스 synchronized(this)를 동기화 하기 때문에 락이 함께 사용된다.
 */
public class BoundedQueueV1 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV1(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            log("[put] 큐가 가득 참, 버림: " + data);
            return;
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
