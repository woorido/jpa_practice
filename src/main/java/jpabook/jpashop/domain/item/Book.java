package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //엔티티 저장 시 슈퍼타입의 구분 컬럼에 저장할 값 지정. 기본값은 클래스 이름
public class Book extends Item {
    private String author;
    private String isbn;

    public Book(String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String name, int price, int stockQuantity) {
        super(name, price, stockQuantity);
    }

    public Book() {
        super();
    }
}
