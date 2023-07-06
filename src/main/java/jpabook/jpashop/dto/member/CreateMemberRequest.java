package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import lombok.Getter;

@Getter
public class CreateMemberRequest {
    private String name;
    private Address address;
}
