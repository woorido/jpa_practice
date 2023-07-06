package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.Getter;

@Getter
public class UpdateMemberResponse {
    private Long id;
    private String name;
    private Address address;

    public UpdateMemberResponse(Member response) {
        this.id = response.getId();
        this.name = response.getName();
        this.address = response.getAddress();
    }
}
