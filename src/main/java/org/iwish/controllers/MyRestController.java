package org.iwish.controllers;

import org.iwish.models.BookReadingList;
import org.iwish.models.Event;
import org.iwish.models.Gift;
import org.iwish.models.User;
import org.iwish.models.data.*;
import org.iwish.models.form.UsersContributionsByGiftId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@RestController
public class MyRestController {

    @Autowired
    UserDao userDao;

    @Autowired
    BookReadingListDao bookReadingListDao;

    @Autowired
    ContributionDao contributionDao;

    @Autowired
    GiftDao giftDao;

    @Autowired
    EventDao eventDao;

    @RequestMapping(value="user/{userName}",method = RequestMethod.GET)
    public User getUserByUserName(@PathVariable String userName){
        User user =  userDao.findByName(userName);
        return user;
    }

    @RequestMapping(value="user",method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        Iterable<User> users =  userDao.findAll();
        return users;
    }

    @RequestMapping(value="readinglist/{userId}",method = RequestMethod.GET)
    public List<BookReadingList> getReadingBookListByUser(@PathVariable int userId){
        List<BookReadingList> bookReadingLists =  bookReadingListDao.findByUser_Id(userId);
        return bookReadingLists;
    }


    /*
        This will return the list of user name and his contribution a perticular gift,
        Pass the giftId
    */
    @RequestMapping(value = "api/contributedBy/{giftId}", method = RequestMethod.GET)
    public HashMap<String,Double> contributedByForm(Model model, @PathVariable int giftId){
        List<Integer> usersIds = contributionDao.findUsersContributedByGift_Id(giftId);
        Iterable<User> contributors = new ArrayList<>();

        Optional<Gift> gift = giftDao.findById(giftId);
        float priceOfGift = 0;
        double remainingAmount = 0;
        if (gift.isPresent()){
            priceOfGift = gift.get().getPrice();
            remainingAmount = priceOfGift;

        }

        List<Object[]> usersContributionsByGiftIds = contributionDao.findContributionAmountUsernameEmailByGift_Id(giftId);

        HashMap<String,Double> map = new HashMap<>();

        for(Object[] obj : usersContributionsByGiftIds){

            //Check the findContributionAmountUsernameEmailByGift_Id @Query
            // the query is returning the column in this seqesnce -- >  contribution.totalamount, contribution.gift_id, user.name , user.email
            //So we are retriving the data in same ourder and setting in the constructor

            remainingAmount = remainingAmount - Double.valueOf(String.valueOf(obj[0]));

           //Map will be populated with the username and the total value he cotributed for the gift,
           map.put(String.valueOf(obj[2]),Double.valueOf(String.valueOf(obj[0])));

        }
        //Addng the remeining amount in the map.
        map.put("Remaining",remainingAmount);

        return map;
    }


    /*
    This is a simple REST service to send email.
    curretly to address is Hard Coded, just for an example I have created this web service
     */
    @Autowired
    private JavaMailSender sender;
    @RequestMapping("/sendMail")
    public String sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo("sumitsenpandey@gmail.com");
            helper.setText("Greetings :)");
            helper.setSubject("Mail From Spring Boot");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }


    @RequestMapping(value = "/gift/{giftId}/addtoevent/{eventId}")
    public String addGiftToEvent(@PathVariable int giftId, @PathVariable int eventId){

        Event event = eventDao.findById(eventId).get();
        List<Gift> gifts = event.getGifts();

        Gift gift = giftDao.findById(giftId).get();

        gifts.add(gift);

        event.setGifts(gifts);
        eventDao.save(event);

        return "{ 'gift' : 'Success'}";
    }

    @RequestMapping(value = "/gift/{giftId}/deletefromevent/{eventId}")
    public String deleteGiftFromEvent(@PathVariable int giftId, @PathVariable int eventId){

        Event event = eventDao.findById(eventId).get();
        List<Gift> gifts = event.getGifts();

        Gift gift = giftDao.findById(giftId).get();

        gifts.remove(gift);

        event.setGifts(gifts);
        eventDao.save(event);

        return "{ 'gift' : 'Success'}";
    }

}
