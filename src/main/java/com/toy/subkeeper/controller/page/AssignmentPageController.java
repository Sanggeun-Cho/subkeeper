package com.toy.subkeeper.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/assignment")
@Controller
public class AssignmentPageController {
    @RequestMapping("/{page}")
    public String page(@PathVariable String page){
        return "assignment/" + page;
    }
    @RequestMapping("/{page}/{id}")
    public String page(@PathVariable String page, @PathVariable String id){
        return "assignment/" + page;
    }
}
