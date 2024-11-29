package executor.test.executor;

public class OrderServiceMain {

    public static void main(String[] args) {
        String orderNo = "Order#1234"; // 예시 주문 번호
        OrderService orderService = new OrderService();
        orderService.order(orderNo);
    }
}
