package org.iwish.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public  String index(){
        return "index";
    }

    @RequestMapping(value = "user/search", method = RequestMethod.GET)
    public  String searchUser(){
        return "user/search";
    }
}
