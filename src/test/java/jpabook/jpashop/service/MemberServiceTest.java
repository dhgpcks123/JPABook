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
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
//        try{
//        여기에 assertThrows제공 해줌
//        }catch(IllegalStateException e){
//           return;
//        }
        //이거 모르겠네... 문법이 Junit5...
    }

    //메모리DB띄워서 테스트하게 해주는... 스프링부트는 그런 기능 제공해줌.
    //허허;;
    //h2에서도 그거 제공해줘서 test폴더의 resources만들어서 yaml넣어서
    //어쨋든 in memory에서 쓸 수 있게 도와주거든? jdbc:h2:mem:test
    //근데 스프링부트는 심지어 그것도 다 없다? 그냥 없으면 메모리에 띄움; 오호
    //스프링부트는 기본적으로 create-drop으로 돌아감.
}