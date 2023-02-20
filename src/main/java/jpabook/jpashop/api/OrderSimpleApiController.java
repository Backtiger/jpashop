package jpabook.jpashop.api;

import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Repository.OrderSearch;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//xToOne관계 성능최적화
//order
//order-> member 하나의 사람이 주문여러개 manytoOne
//order->delivery  주문하나에 배송한개 one to one
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1()
    {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }
}
