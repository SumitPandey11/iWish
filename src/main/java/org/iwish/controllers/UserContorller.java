package org.iwish.controllers;

import org.iwish.models.User;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
//Whaterver atttribute you will add in the Model with the name 'user' will be added in this session below
@SessionAttributes("user")
@RequestMapping("user")
public class UserContorller {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public String index(Model model){
        model.addAttribute("title","Welcome User");
        return "user/index";
    }

    @RequestMapping(value="login",method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String displayLogin(Model model){
        model.addAttribute("title","Login");
        model.addAttribute(new User());
        return "user/login";
    }

    @RequestMapping(value="login",method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String validateLogin(Model model, @ModelAttribute @Valid User user, Errors errors){

        if(errors.hasErrors()){
            return "user/login";
        }
        //get the user from DB by name.
        User aUser = userDao.findByName(user.getName());
        if(aUser != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

            if (bCryptPasswordEncoder.matches( user.getPassword(), aUser.getPassword() )) {
                model.addAttribute("title", "Welcome");
                //Setting this 'aUser' in the model, so that it will set in the Session. This user came from DB so it has all the required data fro USER
                model.addAttribute("user",aUser);
                return "user/index";
            } else {
                model.addAttribute("title", "Incorrect username / Password - Please try again");

                return "user/login";
            }
        }
        else{
            model.addAttribute("title", "Incorrect username / Password - Please try again");
            return "user/login";
        }

    }

    @RequestMapping(value="add",method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String displayCreateNewUser(Model model){
        model.addAttribute("title","Register New User");
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
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

       userDao.save(user);
       model.addAttribute("user", user);
        return "user/index";
    }
}
