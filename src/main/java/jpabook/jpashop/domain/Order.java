package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //FK 가 member_id
    private Member member; //연관관계 주인을 fk 와 가까운 Order 의 member 로 지정해야함

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  //CascadeType.ALL : 모든 영속성이 전이
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Member member, Delivery delivery, List<OrderItem> orderItems, OrderStatus status, LocalDateTime orderDate) {
        this.member = member;
        this.delivery = delivery;
        this.orderItems = orderItems;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    //==연관관계 편의 메서드==//

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        if (member != null) {
            member.getOrders().add(this);
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        return new Order(member, delivery, List.of(orderItems), OrderStatus.ORDER, LocalDateTime.now());
    }

    //==비즈니스 로직==//

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능 합니다.");
        }
        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(member, order.member) && Objects.equals(orderItems, order.orderItems) && Objects.equals(delivery, order.delivery) && Objects.equals(orderDate, order.orderDate) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, orderItems, delivery, orderDate, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", member=" + member +
                ", orderItems=" + orderItems +
                ", delivery=" + delivery +
                ", orderDate=" + orderDate +
                ", status=" + status +
                '}';
    }
}
