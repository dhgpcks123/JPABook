package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
// SINGLE_TABLE(슈퍼 테이블), TABLE_PER_CLASS(Book, Movie, Album만 나오는 전략), JOINED(정규화 된 스타일)
@DiscriminatorColumn(name ="dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy="items")
    private List<Category> categories = new ArrayList<>();
}
