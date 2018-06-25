package org.iwish.controllers;

import org.iwish.models.User;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value="login")
    public String login(Model model){
        return "user/login";
    }

    @RequestMapping(value="login",method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String validateLogin(Model model){
        model.addAttribute("title","LOGIN SUCCESS Create New User");

        return "user/index";
    }

    @GetMapping(value="logout-success")
    public String logout(Model model){
        return "user/logout";
    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String displayCreateNewUser(Model model){
        model.addAttribute("title","Create New User");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String processCreateNewUser(Model model, @ModelAttribute @Valid User user, Errors errors){

        if(errors.hasErrors()){
            return "user/add";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
        String encryptedPassowrd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassowrd);

       userDao.save(user);
       model.addAttribute("title", user.getName());
        return "user/index";
    }
}
