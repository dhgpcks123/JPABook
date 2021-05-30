package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
// ManyToOne은 무조건 (fetch=FetchType.LAZY)줘야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //JPAL select o from order o; -> SQL select * from order (EAGER 무시)
    //order100개 조회 보니까 member EAGER로 되어있네. 100번 단방향 쿼리 날라감
    //n+1문제 order날라가는 쿼리 나오고 결과 + n개 더 가져오니까. n+1문제
    //JPQL쓸 때 N+1문제가 많이 발생한다.
    //다 LAZY로 바꿔서 쓰셈 한방 쿼리 쓰고 싶어!? fetch join또는 엔티티 그래프 기능(최근에 나옴) 사용
    //@XToOne(ManyToOne, 기본이 EAGER임... OneToMany는 기본이 LAZY임...)

//    @OneToMany(mappedBy = "order", fetch=FetchType.LAZY) default여서 안 건드려도 됨.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    //private Date date; -- 예전에 날짜 관련 어노테이션 썼어야 함.
    //자바 8에서는 LocalDateTime에서는 hibernate가 자동으로 지원해줌
    private LocalDateTime oderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
