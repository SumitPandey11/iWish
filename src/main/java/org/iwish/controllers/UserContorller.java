package org.iwish.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserContorller {

    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("title","Welcome User");
        return "user/index";
    }
}
