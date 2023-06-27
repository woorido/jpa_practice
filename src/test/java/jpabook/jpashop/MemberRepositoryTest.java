package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testMember() {
        Member member = new Member("memberA");
        Long savedId = repository.save(member);
        Member findMember = repository.find(savedId);

        assertThat(findMember).isEqualTo(member);
    }
}