package com.toy.subkeeper.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class DefaultPageController {
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {
        // 템플릿 엔진이 처리하지 않도록 빈 응답을 반환합니다.
    }

    // @RequestMapping의 주소값은 스트링 한개도 가능, 스트링 배열도 가능
    @RequestMapping({"", "/"})
    public String empty() {
        return "redirect:/user/login";
    }

    @RequestMapping("/{page}")
    public String page(@PathVariable String page){
        return page;
    }
}
