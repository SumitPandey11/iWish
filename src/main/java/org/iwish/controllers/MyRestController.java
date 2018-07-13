package org.iwish.controllers;

import org.iwish.models.BookReadingList;
import org.iwish.models.User;
import org.iwish.models.data.BookReadingListDao;
import org.iwish.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyRestController {

    @Autowired
    UserDao userDao;

    @Autowired
    BookReadingListDao bookReadingListDao;

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

}
