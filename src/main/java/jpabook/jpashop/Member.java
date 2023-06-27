package jpabook.jpashop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    public Member(String username) {
        this.username = username;
    }

    public Member() {}

    public Long getId() {
        return id;
    }
}
