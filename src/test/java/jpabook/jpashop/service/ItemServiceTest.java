package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService service;
    @Autowired
    ItemRepository repository;

    private Long bookNo;
    private Long albumNo;
    private Long movieNo;

    @BeforeEach
    void setUp(){
        bookNo = service.saveItem(new Book("before", 1000, 10, "author", "isbn"));
        albumNo = service.saveItem(new Album("artist", "etc"));
        movieNo = service.saveItem(new Movie("director", "actor"));
    }

    @Test
    void 아이템_등록() {
        assertThat(repository.findAll().size()).isEqualTo(3);
    }

    @Test
    void stock_증가_감소() {
        Book book = new Book("author", "isbn");
        book.addStock(4);

        assertThat(book.getStockQuantity()).isEqualTo(4);

        book.removeStock(2);
        assertThat(book.getStockQuantity()).isEqualTo(2);
    }

    @Test
    void 변경(){
        UpdateItemDto updateItemDto = new UpdateItemDto("after", 2000, 20);
        service.updateItem(bookNo, updateItemDto);

        Item updateItem = repository.findOne(bookNo);

        assertThat(updateItem.getName()).isEqualTo("after");
        assertThat(updateItem.getPrice()).isEqualTo(2000);
        assertThat(updateItem.getStockQuantity()).isEqualTo(20);
    }
}