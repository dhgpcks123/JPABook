package jpabook.jpashop.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name="user") 이름 바꿀 수 있어. default로 아무것도 안하면?
//springboot가 SpringPhysicalNamingStrategy 케멀 케이스 -> 언더스코어
//점 -> 언더스코어 orderDate -> order_date
//대문자 -> 소문자
//박꾸고 싶으면 spring.jpa.hiberante.naming.physical-strategy가 있는데 바꿀 수 있음
//회사에 원하는 방식으로 네이밍 할 수 있겠지. 설정 바꿔서

// 네이밍 strategy -/ 논리명 생성 -> 명시적으로 컬럼, 테이블 명 안 적으면 어떻게?,
// 물리명 적용 내가 적은 것도 다 포함. 예를 들어 @Column(name ="member_id") 에도 앞에 붙여줌
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy ="member")
    private List<Order> orders = new ArrayList<>();
    //가이드에 best는 이거! null문제에서 안전!
    //하이버네이트가 엔티티를 persist하는 순가
    //내장 컬렉션으로 변경!
    //베어-->메모장으로 좋은가본데?
    /*
        Member member = new Member();
        System.out.println(member.getOrders().getClass()); -> class java.util.ArrayList
        em.persist(team);
        System.out.println(member.getOrders().getClass()); -> org.hibernatecollection.internal.PersistentBag
        하이버네이트가 쓰는 걸로 바꿔 만약에 누가 set해서 바꿔? hibernate가 원하는대로 안 돌아감...
        private List<Order> orders = new ArrayList<>();
        이거 웬만하면 손대지 말고, 바꾸지 말고 있는 거 그대로 쓰는 게 제일 안전함.ㅎ
     */


//    public Member(){
//        orders = new ArrayList<>();
//    }
}
