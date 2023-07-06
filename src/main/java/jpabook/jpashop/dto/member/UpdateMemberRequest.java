package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import lombok.Getter;

@Getter
public class UpdateMemberRequest {
    private String name;
    private Address address;
}
