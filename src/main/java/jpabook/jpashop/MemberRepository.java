package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
        //왜 member를 반환하지 않고, 아이디를 반환하지?
        //이건 영한쌤 스타일임
        //커맨드와 쿼리를 분리해라. 저장을 하고 나면 가급적이면 return안해줘.
        //그냥 id정도만 리턴해줘
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
