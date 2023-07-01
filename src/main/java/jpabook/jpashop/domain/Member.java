package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//한 회원이 여러 상품을 주문할 수 있음 //연관관계 주인을 fk 와 가까운 Order 의 member 로 지정해야함.
    //mappedBy : order table 에 있는 member 필드에 의해 매핑되었다는 뜻
    private List<Order> orders = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", orders=" + orders +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(name, member.name) && Objects.equals(address, member.address) && Objects.equals(orders, member.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, orders);
    }
}
