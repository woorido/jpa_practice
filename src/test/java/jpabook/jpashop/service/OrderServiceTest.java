package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    private Member member;
    private Item item;

    @BeforeEach
    void setup() {
        member = new Member("회원1", new Address("서울", "강가", "123-123"));
        em.persist(member);

        item = new Book("시골 JPA", 10000, 10, "author1", "isbn1");
        em.persist(item);
    }

    @Test
    void 상품_주문() {
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 주문_취소() {
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 상품주문_재고수량초과() {
        int orderCount = 11;

        assertThatThrownBy(() -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }).isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문조회() {
        orderService.order(member.getId(), item.getId(), 3);

        OrderSearch orderSearch = new OrderSearch("회원1", OrderStatus.ORDER);
        List<Order> orders = orderService.findOrders(orderSearch);

        assertThat(orders.size()).isEqualTo(1);
    }
}