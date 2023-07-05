package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    protected Order(Member member, Delivery delivery, List<OrderItem> orderItems, OrderStatus status, LocalDateTime orderDate) {
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

    public Member getMember() {
        return member;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }

        this.member = member;
        if (member != null) {
            member.getOrders().add(this);
        }
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order(member, delivery, orderItems, OrderStatus.ORDER, LocalDateTime.now());
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        delivery.setOrder(order);
        return order;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

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

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
