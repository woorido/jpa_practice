package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //엔티티 저장 시 슈퍼타입의 구분 컬럼에 저장할 값 지정. 기본값은 클래스 이름
public class Book extends Item{
    private String author;
    private String isbn;
}
