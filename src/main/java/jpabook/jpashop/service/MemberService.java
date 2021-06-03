package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
//@RequiredArgsConstructor --> final인거 생성자 만들어 줌. 오 이거 롬복 쓰는데 쓰면 좋겠다!
public class MemberService {

    private final MemberRepository memberRepository;
    /*
    @Autowired
    private MemberRepository memberRepository;
    // 필드 주입 많이 쓰는데- 단점이 많아... 회사에서도 이거 쓰더라.
    */
    /*
    // setter 주입 : 이걸로 쓰면 test할 때 쓰기 좋아
    // 단점은? 런타임 때 여기 접근해서 바꿀 수 있음. 보통 근데.. 스프링 올리고 이거 바꿀 일 없잖아. 동작하는 와중에 바꾸면;; 에바
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */
    // -> 권장 - 생성자 주입
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //테스트 케이스 작성도 가능하고, 런타임환경에서 바꿀 수 가 없겠네!

    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        //Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
        //멀티스레드... 한방에 이거 통과할 수 도 있음;; DB에도 제약조건 줘야 안전!
    }
    //회원 전체 조회
//    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

//    @Transactional(readOnly = true) //읽기 전용에는 readOnly = true 쓰세요. 성능 최적화해준다네! //dirty checking안하기도 하고, DB에 따라리소스 많이 안쓰고
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
