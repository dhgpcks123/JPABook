package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(false)//Transactional넣으면 commit안함 그래서 Rollback(false)주면 들어가는 거 볼 수 잇음
    //이거 없으면 insert쿼리 자체가 안날라감!
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        ///when
        Long saveId = memberService.join(member);
//        em.flush(); em Autowired받아서 em.flush()해도 INSERT쿼리 나감
        //then
        Assertions.assertThat(member.getId()).isEqualTo(saveId);
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given

        //when

        //then

    }
}