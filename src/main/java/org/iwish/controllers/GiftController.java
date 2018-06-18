package org.iwish.controllers;

import org.iwish.models.Gift;
import org.iwish.models.User;
import org.iwish.models.data.GiftDao;
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
@RequestMapping("gift")
public class GiftController {
    @Autowired
    private GiftDao giftDao;

    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayCreateNewGift(Model model){
        model.addAttribute("title","Create New Gift");
        model.addAttribute(new Gift());
        return "gift/add";
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    public String processCreateNewUser(Model model, @ModelAttribute @Valid Gift gift, Errors errors){

        if(errors.hasErrors()){
            return "gift/add";
        }

        giftDao.save(gift);
        model.addAttribute("title", "Successfully Created :  " + gift.getName());
        return "gift/index";
    }
}
