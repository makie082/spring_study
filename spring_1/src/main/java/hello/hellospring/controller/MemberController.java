package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


// Controller를 통해서 외부 요청을 받는다
@Controller // spring이 시작될 때 컨트롤러 객체가 생성됨 -> 컨테이너에서 스피링 빈이 관리되는 것
public class MemberController {

    //private final MemberService memberService = new MemberService();
    // 이렇게 하나하나 new 로 생성해주는 것보다 스프링 컨테이너에 등록하고 꺼내서 사용하는게 나음

    private final MemberService memberService;

    @Autowired // 컨트롤러와 스프링 컨테이너에 있는 memberService와 연결시켜줌 / 생성자에 Autowired 하면 됨
     public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // url에 /members/new를 치면 여기로 이동해라 (키를 조회할 때 사용)
    public String createForm() {
        return "members/createMemberForm"; // members/createMemberForm으로 이동하기만 함 -> template에서 경로 찾음
    }

    @PostMapping("/members/new") // 데이터를 form에 넣어서 전달할 때 사용
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); // 회원가입 시켜줌

        return "redirect:/"; //회원 가입이 끝나니까 홈화면으로 보내줌
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers(); // 멤버를 다 보여주는 함수 findMembers
        model.addAttribute("members", members); // 멤버 리스트 자체를 메델에 담아서 뷰에 넘길 예정
        return "members/memberList";
    }
}

