package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }
    // 검색기능은 동적쿼리

//    public List<Order> findAllByString(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o join o.member m where o.status = :status and m.name like :name",
//                Order.class).setParameter("status",orderSearch.getOrderStatus())
//                .setParameter("name",orderSearch.getMemberName())
//                .setFirstResult(100).setMaxResults(1000).getResultList();
        // 값이 무조건 있다는 조건하에 그냥 넣으면 되지.
        // 동적 쿼리?
        // 1. JPQL 생짜로 ;; 와우;;
        /*
        String jpql = "select o from Order o join o.member m";
        // 주문 상태 검색
        boolean isFirstCondition = true;
        if (orderSearch.getOrderStatus() != null) {
            if (!isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypeQuery<Order> query = em.createQuery(jpql, Order.class)
            .setMaxResults(1000);
        if(orderSearch.getOrderStatus() != null){
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        List<Order> resultList = query.getResultList();

        return resultList;
         */

//    }

    /**
     * JPA Criteria 이것도 권장방법은 아니래 ㅋㅎ
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> critera = new ArrayList<>();
        //주문 상태 검색
        if(orderSearch.getOrderStatus() != null){
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            critera.add(status);
        }
        //회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())){
            Predicate name =
                    cb.like(m.<String>get("name"), "%"+orderSearch.getMemberName()+"%");
            critera.add(name);
        }

        cq.where(cb.and(critera.toArray(new Predicate[critera.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
        // criteria 이거 성배팀장이 쓰던거네 ㅋㅋ
        // 치명적인 단점이 있다. -> 뭐래는지 한 눈에 안 읽힘. 유지보수 하려면;;;
        // 안. 씀.
        // 이래서 쓰는 게 Querydsl!
    }
}
