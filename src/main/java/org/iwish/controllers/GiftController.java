package org.iwish.controllers;

import org.iwish.models.*;
import org.iwish.models.data.*;
import org.iwish.models.form.GiftAndContibutionAmount;
import org.iwish.models.form.UsersContributionsByGiftId;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

@Controller
@SessionAttributes("user")
@RequestMapping("gift")
public class GiftController {

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContributionDao contributionDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private BookReadingListDao bookReadingListDao;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayCreateNewGift(Model model){
        model.addAttribute("title","Create your wish");
        model.addAttribute(new Gift());
        return "gift/add";
    }

    @RequestMapping(value="add/book/{bookId}",method = RequestMethod.GET)
    public String displayAddBook(Model model,@PathVariable int bookId){
        Optional<BookReadingList> _bookReadingList = bookReadingListDao.findById(bookId);
        BookReadingList bookReadingList = null;
        String bookNameISBNAuthor ="";
        if(_bookReadingList.isPresent()){
            bookReadingList = _bookReadingList.get();
            bookNameISBNAuthor = "Book Title : " + bookReadingList.getBookTitle() + ", ISBN :" + bookReadingList.getIsbn() ;

        }
        model.addAttribute("title","Create your wish");
        Gift gift = new Gift();
        gift.setName(bookNameISBNAuthor);
        gift.setOccasion("This book is in my reading list");
        model.addAttribute(gift);
        return "gift/add";
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="add",method = RequestMethod.POST)
    public String processCreateGift(@SessionAttribute("user") User user, Model model, @ModelAttribute @Valid Gift gift, Errors errors){

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
        model.addAttribute("title",  gift.getName() + " added to wish list");
        return "gift/index";
    }

    @RequestMapping(value="edit/{giftId}/userId/{userId}",method = RequestMethod.GET)
    public String editGift(@PathVariable int giftId, @PathVariable int userId, Model model){

        Optional<Gift> gift = giftDao.findById(giftId);

        model.addAttribute("title","Edit your Gift");
        model.addAttribute("gift",gift.get());
        return "gift/edit";
   }

    @RequestMapping(value="update",method = RequestMethod.POST)
    public String updateGift(@SessionAttribute("user") User currentUserInSession, Model model,@ModelAttribute("gift") @Valid Gift gift, Errors errors){

        if(errors.hasErrors()){
            return "gift/edit";
        }

        /*
        If the gift object 'id' is null or 0 then it will create new Gigt object in DB.
        If Gift onject has a Id value then it will update the Gift whose primaryKey is equal to id
        */
        giftDao.save(gift);
        model.addAttribute("title", "Successfully updated :  " + gift.getName());
        //return "gift/index";
        int userId = currentUserInSession.getId();
        return "redirect:/gift/list/"+userId;

    }

    @RequestMapping(value="delete/{giftId}/userId/{userId}",method = RequestMethod.GET)
    public String deleteGift(@PathVariable int giftId, @PathVariable int userId){

        giftDao.deleteById(giftId);

        return "redirect:/gift/list/"+userId;
    }

    /*
       Display the Wishlist of user, get the UserId from Pathvariable
    */
    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"list/{userId}"}, method = RequestMethod.GET)
    public String listWishListByUserId( @SessionAttribute("user") User currentUserInSession, Model model, @PathVariable int userId){
        List<Gift> gifts = giftDao.findByUser_Id(userId);
        String username = userDao.findById(userId).get().getName();

        List<GiftAndContibutionAmount> giftAndContibutionAmountList = new ArrayList<>();


        for(Gift gift : gifts){
            Optional<Double> totalContributionAmount = contributionDao.findContributionAmountByGift_Id(gift.getId());
            double amount = 0;
            if(totalContributionAmount.isPresent()) {
                amount = totalContributionAmount.get();
            }


            //Checking if this Gift is associated with any Event
            List<Event> events = gift.getEvents();
            boolean isAssociatedWithAnyEvent = false;
            if(events != null && !events.isEmpty()){
                isAssociatedWithAnyEvent = true;
            }

            giftAndContibutionAmountList.add(new GiftAndContibutionAmount(amount,gift,false,isAssociatedWithAnyEvent));
        }

        /*
        Use can see the Delete / Edit option for their own Gift list.
        The Delete / Edit option will not available for othe usres Gift List
         */
        boolean currentUserFlag = false;
        if(currentUserInSession.getId() == userId){
            currentUserFlag = true;
        }
        model.addAttribute("giftAndContibutionAmountList", giftAndContibutionAmountList);
        model.addAttribute("wishListUserId", userId );
        model.addAttribute("currentUserFlag", currentUserFlag );
        model.addAttribute("title", StringUtils.capitalize(username) + "'s wish list " );
        return "gift/list";
    }


    @RequestMapping(value = {"list/{userId}/event/{eventId}"}, method = RequestMethod.GET)
    public String listWishListByUserIdForEvent( @SessionAttribute("user") User currentUserInSession, Model model, @PathVariable int userId,@PathVariable int eventId){

        //Get all the gift created by the user.
        List<Gift> gifts = giftDao.findByUser_Id(userId);

        String username = userDao.findById(userId).get().getName();

        List<GiftAndContibutionAmount> giftAndContibutionAmountList = new ArrayList<>();

        /*
            Find Gifts associated with the an EventId,
            Add all the Gifts associated with EventId in giftsAttachedWithEvent List
         */
        Optional<Event> _event= eventDao.findById(eventId);
        String eventTitle = "";
        List<Gift> giftsAttachedWithCurrentEvent = null;

        if(_event.isPresent()){
            Event event = _event.get();
            eventTitle = event.getTitle();
            giftsAttachedWithCurrentEvent =  event.getGifts();
        }



        for(Gift gift : gifts){

            //Check if the giftsAttachedWithEvent is not null, & if 'gift' is there in the giftsAttachedWithEvent List.
            //If 'gift' is there in the list that means this gift ia already associated with the 'Event'. So we need to pass a flag to the client end to let the
            // client ends know that this Gift is associated with this Event.
            boolean isAssociatedWithCurrentEvent = false;
            if(giftsAttachedWithCurrentEvent != null){
                if(giftsAttachedWithCurrentEvent.contains(gift)){
                    isAssociatedWithCurrentEvent = true;
                }
            }

            Optional<Double> totalContributionAmount = contributionDao.findContributionAmountByGift_Id(gift.getId());
            double amount = 0;
            if(totalContributionAmount.isPresent()) {
                amount = totalContributionAmount.get();
            }

            List<Event> events = gift.getEvents();
            boolean isAssociatedWithAnyEvent = false;
            if(events != null && !events.isEmpty()){
                isAssociatedWithAnyEvent = true;
            }

            giftAndContibutionAmountList.add(new GiftAndContibutionAmount(amount,gift,isAssociatedWithCurrentEvent,isAssociatedWithAnyEvent));
        }






        /*
        Use can see the Delete / Edit option for their own Gift list.
        The Delete / Edit option will not available for othe usres Gift List
         */
        boolean currentUserFlag = false;
        if(currentUserInSession.getId() == userId){
            currentUserFlag = true;
        }



        model.addAttribute("giftAndContibutionAmountList", giftAndContibutionAmountList);
        model.addAttribute("wishListUserId", userId );
        model.addAttribute("eventId", eventId);
        model.addAttribute("currentUserFlag", currentUserFlag );
        model.addAttribute("title",  "Add/remove wish(s) to the event  '" + eventTitle +"' from " + StringUtils.capitalize(username) + "'s wish list. ");
        return "gift/list";
    }

    /*
        Display the Wishlist of user, get the UserId for current session
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listWishListByUserId(@SessionAttribute("user") User user, Model model){
        /*List<Gift> gifts = giftDao.findByUser_Id(user.getId());
        model.addAttribute("gifts", gifts);
        model.addAttribute("title", StringUtils.capitalize(user.getName()) + "'s wish list " );*/
        return "redirect:/gift/list/"+user.getId();
    }

    @RequestMapping(value = "addToEvent/{eventId}", method = RequestMethod.GET)
    public String redirectToUserWishListWithEventId(@SessionAttribute("user") User user, Model model,@PathVariable int eventId){
        /*List<Gift> gifts = giftDao.findByUser_Id(user.getId());
        model.addAttribute("gifts", gifts);
        model.addAttribute("title", StringUtils.capitalize(user.getName()) + "'s wish list " );*/
        return "redirect:/gift/list/"+user.getId()+"/event/"+eventId;
    }

    @RequestMapping(value = "contribute/{giftId}", method = RequestMethod.GET)
    public String contributionForm(Model model, @PathVariable int giftId){
        Optional<Gift> gifts = giftDao.findById(giftId);
        Optional<Double>  totalAmountContributed = contributionDao.findContributionAmountByGift_Id(giftId);


        double amount = 0;
        if(totalAmountContributed.isPresent()) {
            amount = totalAmountContributed.get();
        }


        Gift gift = null;
        if(gifts.isPresent()){
            gift = gifts.get();
        }
        model.addAttribute("gift", gift);
        model.addAttribute("totalAmountContributed", amount);
        model.addAttribute("title", "Contribute for " + gift.getName() );
        return "gift/contribute";
    }

    @RequestMapping(value = "contribute", method = RequestMethod.POST)
    public String saveContribution(@SessionAttribute User user, @ModelAttribute Gift gift , Model model,double totalamount, int giftId){
        Contribution contribution = new Contribution();
        Optional<Gift> aGifts = giftDao.findById(giftId);
        Gift aGift= null;
        if(aGifts.isPresent()){
            aGift = aGifts.get();
        }
        contribution.setAmount(totalamount);
        contribution.setUser(user);
        contribution.setGift(aGift);
        contributionDao.save(contribution);
        model.addAttribute("gift", aGift);
        model.addAttribute("contribution",contribution);
        //model.addAttribute("title", "Thank you for Gifting $" + amount + "." );
        return "redirect:/gift/contribute/"+giftId;
    }

    @RequestMapping(value = "wishListSeeker", method = RequestMethod.GET)
    public String listWishListSeeker( Model model){
        List<Integer> giftSeekersIds = giftDao.findAllGiftSeekersUserId();
        Iterable<User> giftSeekers = userDao.findAllById(giftSeekersIds);

        model.addAttribute("giftSeekers", giftSeekers);
        model.addAttribute("title", "User's Wish List" );
        return "gift/wishListSeeker";
    }


    @RequestMapping(value = "contributedBy/{giftId}", method = RequestMethod.GET)
    public String contributedByForm(Model model, @PathVariable int giftId){
        List<Integer> usersIds = contributionDao.findUsersContributedByGift_Id(giftId);
        Iterable<User> contributors = new ArrayList<>();

        List<Object[]> usersContributionsByGiftIds = contributionDao.findContributionAmountUsernameEmailByGift_Id(giftId);

        List<UsersContributionsByGiftId> usersContributionsByGiftIdList = new ArrayList<>();

        for(Object[] obj : usersContributionsByGiftIds){

            //Check the findContributionAmountUsernameEmailByGift_Id @Query
            // the query is returning the column in this seqesnce -- >  contribution.totalamount, contribution.gift_id, user.name , user.email
            //So we are retriving the data in same ourder and setting in the constructor
            usersContributionsByGiftIdList.add(new UsersContributionsByGiftId(Double.valueOf(String.valueOf(obj[0])),Integer.valueOf(String.valueOf(obj[1])),String.valueOf(obj[2]),String.valueOf(obj[3])));
        }
        if(!usersIds.isEmpty()){
            contributors = userDao.findAllById(usersIds);
            model.addAttribute("usersContributionsByGiftIdList",usersContributionsByGiftIdList);
            model.addAttribute("title", "Contributed by" );
            model.addAttribute("giftId", giftId );
        }else{
            model.addAttribute("usersContributionsByGiftIdList",usersContributionsByGiftIdList);
            model.addAttribute("title", "Sorry No Contribution. " );
        }

        return "gift/contributedByList";
    }


    @RequestMapping(value = "detail/{giftId}", method = RequestMethod.GET)
    public String getGiftDetail(Model model,@PathVariable int giftId){
        Optional<Gift> _gift = giftDao.findById(giftId);
        Gift gift=null;
        if(_gift.isPresent()){
            gift = _gift.get();
        }


        //START : Get the list of users who all contributed for this Gift .
        List<Integer> usersIds = contributionDao.findUsersContributedByGift_Id(giftId);
        Iterable<User> contributors = new ArrayList<>();

        List<Object[]> usersContributionsByGiftIds = contributionDao.findContributionAmountUsernameEmailByGift_Id(giftId);

        List<UsersContributionsByGiftId> usersContributionsByGiftIdList = new ArrayList<>();

        for(Object[] obj : usersContributionsByGiftIds){

            //Check the findContributionAmountUsernameEmailByGift_Id @Query
            // the query is returning the column in this seqesnce -- >  contribution.totalamount, contribution.gift_id, user.name , user.email
            //So we are retriving the data in same ourder and setting in the constructor
            usersContributionsByGiftIdList.add(new UsersContributionsByGiftId(Double.valueOf(String.valueOf(obj[0])),Integer.valueOf(String.valueOf(obj[1])),String.valueOf(obj[2]),String.valueOf(obj[3])));
        }

        double amount = 0;
        model.addAttribute("totalContributionAmount", amount );
        if(!usersIds.isEmpty()){
            contributors = userDao.findAllById(usersIds);
            model.addAttribute("usersContributionsByGiftIdList",usersContributionsByGiftIdList);
            model.addAttribute("contributedby", "Contributed by" );
            model.addAttribute("giftId", giftId );

            //START - get the total contribution amount of Gift by giftId
            Optional<Double> totalContributionAmount = contributionDao.findContributionAmountByGift_Id(gift.getId());

            if(totalContributionAmount.isPresent()) {
                amount = totalContributionAmount.get();
            }
            model.addAttribute("totalContributionAmount", amount );
            //END - get the total contribution amount of Gift by giftId

        }else{
            model.addAttribute("usersContributionsByGiftIdList",usersContributionsByGiftIdList);
            model.addAttribute("contributedby", "Sorry No Contribution. " );
        }

        //END : Get the list of users who all contributed for this Gift .


        List<Event> events = gift.getEvents();

        String associatedEventsTitle = "No Event Associated";

        if(events != null && !events.isEmpty()){
            model.addAttribute("events", events);
            associatedEventsTitle = "Associated Event(s)";
        }
        model.addAttribute("associatedEventsTitle", associatedEventsTitle );
        model.addAttribute("gift",gift);
        model.addAttribute("title", "My Wish :  " + gift.getName());
        return "gift/detail";
    }

}

