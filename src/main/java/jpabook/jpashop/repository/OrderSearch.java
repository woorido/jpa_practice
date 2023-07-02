package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;

public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

    public OrderSearch(String memberName, OrderStatus orderStatus) {
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }

    public String getMemberName() {
        return memberName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
