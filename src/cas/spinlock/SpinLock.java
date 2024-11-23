package cas.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

/**
 * CAS를 활용한 원자적인 연산은 스레드가 쪼갤 수 없어 여러 스레드가 동시에 실행해도 안전하다.
 * 원자적으로 만든 락 덕분에 무거운 동기화 작업 없이 가벼운 락을 만들 수 있다.
 *
 * 동기화 락을 사용하는 경우 스레드가 락을 획득하지 못하면 BLOCKED, WAITING 등 상태가 변한다.
 * 또한 대기상태의 스레드를 깨워야 하는 무겁고 복잡한 과정이 추가로 들어간다.
 *
 * CAS를 활용한 락 방식은 사실 락이 없다. 단순히 while문을 반복할 뿐이다. 따라서 대기하는 스레드도 RUNNABLE 상태를 유지하면서 가볍고 빠르게 동작한다.
 * 하지만 while문을 반복하면 락을 기다리는 스레드가 CPU를 계속 사용하면서 대기하게 된다.
 *
 * SpinLock은 어떨때 효율적일까?
 * 안전한 임계 영역이 필요하지만 연산이 길지 않고 아주 짧게 끝날때 사용해야한다.
 * 단순히 숫자 값의 증가, 자료 구조의 데이터 추가와 같이 CPU 싸이클이 금방끝나는 연산에 사용하면 효과적이다.
 */
public class SpinLock {

    private volatile AtomicBoolean lock = new AtomicBoolean(FALSE);

    public void lock() {
        log("Lock 획득 시도");
        while (!lock.compareAndSet(FALSE, TRUE)) {
            log("락 획득 실패 - 스핀 대기");
        }
        log("락 획득 완료");
    }

    public void unlock() {
        lock.set(FALSE);
        log("락 반납 완료");
    }
}
