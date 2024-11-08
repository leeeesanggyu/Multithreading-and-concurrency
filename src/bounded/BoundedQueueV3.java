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
            this.notify();  // WAIT(스레드 대기집합) -> BLOCKED
            /*
            - notify()로 깨어난 스레드는 바로 동작하는게 아니라 임계영역에서 BLOCKED 상태가 되어서 락을 기다린다.
            - notify()로 깨우게 되면 계속해서 생산자의 스레드만 깨우는 스레드 기아상태가 될 수도 있기 때문에 notifyAll()을 사용하여 스레드 대기집합의 모든 스레드를 깨워 기아상태를 막을 수 있다.
            - (생산자 스레드는 모두 깼지만 조건에 맞지않아 다시 WAITING 상태가 되고 소비자 스레드는 조건에 부합해 RUNNABLE이 되기 때문
            */
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
