package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = new Member("kim", new Address("서울", "강가", "123-123"));
        Long savedId = memberService.join(member);
        Member findOne = memberRepository.findOne(savedId);

        assertThat(findOne).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() {
        Member member1 = new Member("kim", new Address("서울", "강가", "123-123"));
        Member member2 = new Member("kim", new Address("서울", "강가", "123-123"));

        memberService.join(member1);
        assertThatThrownBy(() -> {
            memberService.join(member2);
        }).isInstanceOf(IllegalStateException.class);
    }
}