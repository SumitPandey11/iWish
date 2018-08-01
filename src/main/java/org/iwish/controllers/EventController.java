package org.iwish.controllers;

import org.iwish.mail.EmailServiceImpl;
import org.iwish.models.Event;
import org.iwish.models.Gift;
import org.iwish.models.Guest;
import org.iwish.models.User;
import org.iwish.models.data.EventDao;
import org.iwish.models.data.GiftDao;
import org.iwish.models.data.GuestDao;
import org.iwish.models.data.UserDao;
import org.iwish.models.form.InvitationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("user")
@RequestMapping("event")
public class EventController {


    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GuestDao guestDao;

    @Autowired
    public EmailServiceImpl emailService;

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


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String EveneListByUserId(@SessionAttribute("user") User user, Model model){

        List<Event> events = eventDao.findByUser_Id(user.getId());

        model.addAttribute("events",events);
        model.addAttribute("title","Event list");

        return "event/list";
    }

    @RequestMapping(value = "invite/{eventId}", method = RequestMethod.GET)
    public String invite(@SessionAttribute("user") User user, Model model, @PathVariable int eventId){

        Optional<Event> _event = eventDao.findById(eventId);
        Event event = null;
        if(_event.isPresent()) {
            event = _event.get();
        }

        //retrive all invited guestes for this eventId
        List<Guest> guests = guestDao.findGuestByEvent_Id(eventId);

        model.addAttribute("event",event);
        model.addAttribute("eventId",eventId);
        model.addAttribute("guests",guests);
        model.addAttribute("title","Invite");

        return "event/invite";
    }


    @RequestMapping(value="sendmail",method = RequestMethod.POST)
    public String sendEmail(@SessionAttribute("user") User user, @ModelAttribute @Valid Event event, @RequestParam("email") String email, @RequestParam("message") String message,@RequestParam("eventId") int eventId, Model model){

        //TODO: umcomment this for email to work
        emailService.sendSimpleMessage(email,"Invitation", message);

        //Add the guest to Guest table with event id.
        Guest guest = new Guest();
        guest.setGuestEmail(email);

        Optional<Event> _event = eventDao.findById(eventId);
        if(_event.isPresent()){
            event = _event.get();
        }

        guest.setEvent(event);
        guestDao.save(guest);


        //retrive all invited guestes for this eventId
        List<Guest> guests = guestDao.findGuestByEvent_Id(eventId);


        model.addAttribute("event",event);
        model.addAttribute("eventId",eventId);
        model.addAttribute("guests",guests);
        model.addAttribute("title", "Invitation Sent  to  " + email);
        return "event/invite";
    }

}
