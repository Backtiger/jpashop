package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity   //book은 Item상송
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;

    private String isbn;

}
