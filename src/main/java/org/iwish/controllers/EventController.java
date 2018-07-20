package org.iwish.controllers;

import org.iwish.models.Event;
import org.iwish.models.Gift;
import org.iwish.models.User;
import org.iwish.models.data.EventDao;
import org.iwish.models.data.GiftDao;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes("user")
@RequestMapping("event")
public class EventController {


    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayInviteForm(Model model){
        model.addAttribute("title","Create event");
        model.addAttribute(new Event());
        return "event/add";
    }

    @RequestMapping(value="add",method = RequestMethod.POST)
    public String processCreateGift(@SessionAttribute("user") User user, Model model, @ModelAttribute @Valid Event event, Errors errors){

        if(errors.hasErrors()){
            return "event/add";
        }

        Integer i = new Integer(user.getId());
        Optional<User> u = userDao.findById(i);

        if(u.isPresent()){
            user = u.get();
            event.setUser(user);
            eventDao.save(event);
        }
        else{
            // TODO: Check if User not exists.
        }
        model.addAttribute("title", "Successfully Created :  " + event.getTitle());
        return "event/add";
    }

}
