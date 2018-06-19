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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("gift")
public class GiftController {

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

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


        Integer i = new Integer(1);
        Optional<User> u = userDao.findById(i);
        User user;
        if(u.isPresent()){
            user = u.get();
            gift.setUser(user);
            giftDao.save(gift);
        }
        else{
            // TODO: Check if User not exists.
        }
        model.addAttribute("title", "Successfully Created :  " + gift.getName());
        return "gift/index";
    }

    @RequestMapping(value = "list/{userId}", method = RequestMethod.GET)
    public String listWishListByUserId(Model model, @PathVariable int userId){
        List<Gift> gifts = giftDao.findByUser_Id(userId);
        model.addAttribute("gifts", gifts);
        model.addAttribute("title", "Your wish list " );
        return "gift/list";
    }
}
