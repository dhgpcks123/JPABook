package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
        // Model에다가 데이터를 심어서 뷰에 날려보냄.
        // return -> resources에 templates의 hello.html을 찾아감
    }
}
