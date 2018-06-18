package org.iwish.controllers;

import org.iwish.models.User;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserContorller {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="")
    public String index(Model model){
        model.addAttribute("title","Welcome User");
        return "user/index";
    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayCreateNewUser(Model model){
        model.addAttribute("title","Create New User");
        model.addAttribute(new User());
        return "user/add";
    }
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String processCreateNewUser(Model model, @ModelAttribute @Valid User user, Errors errors){

        if(errors.hasErrors()){
            return "user/add";
        }

       userDao.save(user);
       model.addAttribute("title", user.getName());
        return "user/index";
    }
}
