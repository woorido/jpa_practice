package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//상속관계 전략을 부모클래스에 지정해야함
@DiscriminatorColumn(name = "dtype") //하위 클래스를 구분하는 용도의 컬럼
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
