package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member("userA", new Address("서울", "1", "2"));
            em.persist(member);

            Book book1 = new Book("jpa1", 10000, 100, "author1", "isbn1");
            Book book2 = new Book("jpa2", 20000, 100, "author2", "isbn2");

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 10000, 2);

            Order order = Order.createOrder(member, new Delivery(member.getAddress()), List.of(orderItem1, orderItem2));

            em.persist(order);
        }

        public void dbInit2() {
            Member member = new Member("userB", new Address("대전", "3", "4"));
            em.persist(member);

            Book book1 = new Book("spring book1", 20000, 200, "author3", "isbn3");
            Book book2 = new Book("spring book2", 40000, 300, "author4", "isbn4");

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 40000, 4);

            Order order = Order.createOrder(member, new Delivery(member.getAddress()), List.of(orderItem1, orderItem2));

            em.persist(order);
        }
    }
}


