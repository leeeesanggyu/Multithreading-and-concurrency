package executor.test.executor;

import java.util.concurrent.*;

import static utils.Logger.log;
import static utils.ThreadUtil.sleep;

public class OrderService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public void order(String orderNo) {
        try {
            Future<Boolean> inventoryWork = executorService.submit(new InventoryWork(orderNo));
            Future<Boolean> shippingWork = executorService.submit(new ShippingWork(orderNo));
            Future<Boolean> accountingWork = executorService.submit(new AccountingWork(orderNo));

            Boolean inventoryResult = inventoryWork.get();
            Boolean shippingResult = shippingWork.get();
            Boolean accountingResult = accountingWork.get();

            // 결과 확인
            if (inventoryResult && shippingResult && accountingResult) {
                log("모든 주문 처리가 성공적으로 완료되었습니다.");
            } else {
                log("일부 작업이 실패했습니다.");
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.close();
        }
    }

    static class InventoryWork implements Callable<Boolean> {

        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {

        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {

        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }
}
