package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
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

    //== 비즈니스 로직 ==//
    //도메인 주도 설계 -> 엔티티 안에서 해결할 수 있는 건 엔티티 안에서!
    //-> 응집력이 있음!
    //이런 거 setter쓰지 마시고!! 내부에서 validation사용해서 써주세요. 가장 객체 지향적!
    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
