package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    @DisplayName("상품주문")
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();

        Book book = createBook("JPA", 10000, 10);
        //when
        Long order = orderService.order(member.getId(), book.getId(), 2);

        //then
        Order getOrder = orderRepository.findOne(order);
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(1,getOrder.getOrderItems().size(),"주문할 상품 종류 수가 정확해야 한다");
        Assertions.assertEquals(10000*2,getOrder.getTotalPrice(),"주문 가격은 가격 곱하기 수량이다");
        Assertions.assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }



    @Test
    @DisplayName("주문취소")
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long order = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(order);

        //then
        Order one = orderRepository.findOne(order);
        Assertions.assertEquals(OrderStatus.CANCEL, one.getStatus(), "주문 취소시 상태는 CANCEL이다.");
        Assertions.assertEquals(10,item.getStockQuantity(),"주문이 취소한 상품은 그 만큼 재고가 증가해야한다.");

    }
    @Test
    @DisplayName("상품주문_재고수량초과")
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 11;
        //when
        orderService.order((member.getId()), item.getId(), orderCount);
        //then
        Assertions.fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}