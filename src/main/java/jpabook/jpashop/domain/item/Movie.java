package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;

    public Movie(String director, String actor) {
        this.director = director;
        this.actor = actor;
    }

    public Movie() {

    }
}
