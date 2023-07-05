package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.service.UpdateItemDto;

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

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item() {

    }

    //==비즈니스 로직==//

    /**
     * stock 증가
     *
     * @param quantity
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    /**
     * update item
     *
     * @param updateItemDto
     */
    public void updateItem(UpdateItemDto updateItemDto) {
        this.price = updateItemDto.getPrice();
        this.stockQuantity = updateItemDto.getStockQuantity();
        this.name = updateItemDto.getName();
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

}
