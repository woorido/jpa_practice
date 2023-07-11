package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.dto.order.OrderItemQueryDto;
import jpabook.jpashop.dto.order.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();
        for (OrderQueryDto orderQueryDto : result) {
            orderQueryDto.changeOrderItems(findOrderItems(orderQueryDto.getOrderId()));
        }
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.dto.order.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id = :orderId",
                        OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.dto.order.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d",
                OrderQueryDto.class
        ).getResultList();
    }
}
