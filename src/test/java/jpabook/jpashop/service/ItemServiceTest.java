package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
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

    @Test
    void 아이템_등록() {
        Book book = new Book("author", "isbn");
        Album album = new Album("artist", "etc");
        Movie movie = new Movie("director", "actor");

        service.saveItem(book);
        service.saveItem(album);
        service.saveItem(movie);

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
}