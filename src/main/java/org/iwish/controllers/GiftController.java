package org.iwish.controllers;

import org.iwish.models.Contribution;
import org.iwish.models.Gift;
import org.iwish.models.User;
import org.iwish.models.data.ContributionDao;
import org.iwish.models.data.GiftDao;
import org.iwish.models.data.UserDao;
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

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="add",method = RequestMethod.GET)
    public String displayCreateNewGift(Model model){
        model.addAttribute("title","Create New Gift");
        model.addAttribute(new Gift());
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
        model.addAttribute("title", "Successfully Created :  " + gift.getName());
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
    public String updateGift(Model model,@ModelAttribute("gift") @Valid Gift gift, Errors errors){

        if(errors.hasErrors()){
            return "gift/edit";
        }

        /*
        If the gift object 'id' is null or 0 then it will create new Gigt object in DB.
        If Gift onject has a Id value then it will update the Gift whose primaryKey is equal to id
        */
        giftDao.save(gift);
        model.addAttribute("title", "Successfully updated :  " + gift.getName());
        return "gift/index";
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
    @RequestMapping(value = "list/{userId}", method = RequestMethod.GET)
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

            giftAndContibutionAmountList.add(new GiftAndContibutionAmount(amount,gift));
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
        model.addAttribute("title", "Gift... " );
        return "gift/contribute";
    }

    @RequestMapping(value = "contribute", method = RequestMethod.POST)
    public String saveContribution(@SessionAttribute User user, @ModelAttribute Gift gift , Model model,double amount, int giftId){
        Contribution contribution = new Contribution();
        Optional<Gift> aGifts = giftDao.findById(giftId);
        Gift aGift= null;
        if(aGifts.isPresent()){
            aGift = aGifts.get();
        }
        contribution.setAmount(amount);
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
        model.addAttribute("title", "Wish List Seeker" );
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
        }else{
            model.addAttribute("usersContributionsByGiftIdList",usersContributionsByGiftIdList);
            model.addAttribute("title", "Sorry No Contribution. " );
        }

        return "gift/contributedByList";
    }
}

