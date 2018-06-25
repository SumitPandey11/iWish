package org.iwish.controllers;

import org.iwish.models.Gift;
import org.iwish.models.User;
import org.iwish.models.data.GiftDao;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("user")
@RequestMapping("gift")
public class GiftController {

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayCreateNewGift(Model model){
        model.addAttribute("title","Create New Gift");
        model.addAttribute(new Gift());
        return "gift/add";
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String processCreateNewUser(@SessionAttribute("user") User user, Model model, @ModelAttribute @Valid Gift gift, Errors errors){

        if(errors.hasErrors()){
            return "gift/add";
        }


        Integer i = new Integer(user.getId());
        Optional<User> u = userDao.findById(i);

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

    /*
       Display the Wishlist of user, get the UserId from Pathvariable
    */
    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "list/{userId}", method = RequestMethod.GET)
    public String listWishListByUserId( Model model, @PathVariable int userId){
        List<Gift> gifts = giftDao.findByUser_Id(userId);
        model.addAttribute("gifts", gifts);
        model.addAttribute("title", "Your wish list " );
        return "gift/list";
    }

    /*
        Display the Wishlist of user, get the UserId for current session
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listWishListByUserId(@SessionAttribute("user") User user, Model model){
        List<Gift> gifts = giftDao.findByUser_Id(user.getId());
        model.addAttribute("gifts", gifts);
        model.addAttribute("title", StringUtils.capitalize(user.getName()) + "'s wish list " );
        return "gift/list";
    }

}
