package org.iwish.controllers;

import org.iwish.models.BookReadingList;
import org.iwish.models.User;
import org.iwish.models.data.BookReadingListDao;
import org.iwish.models.data.UserDao;
import org.iwish.models.pojo.book.BookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookReadingListDao bookReadingListDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="booksearch",method = RequestMethod.GET)
    public String displayBookSearch(Model model){
        model.addAttribute("title","Search book by ISBN number");
        model.addAttribute("bookDetails",new BookDetails());

        return "book/bookSearch";
    }

    @RequestMapping(value="booksearch",method = RequestMethod.POST)
    public String bookSearch(Model model, @RequestParam("isbn") String isbn){
        BookDetails bookDetails = callRestGoogleBookService(isbn);
        model.addAttribute("title","My Reading List");
        model.addAttribute("bookDetails",bookDetails);
        model.addAttribute("isbn",isbn);

        return "book/bookSearch";
    }

    @RequestMapping(value = "readinglist", method = RequestMethod.GET)
    public String displayMyReadingList(@SessionAttribute("user") User user, Model model){

        Integer userId = new Integer(user.getId());
        List<BookReadingList> bookReadingListList = bookReadingListDao.findByUser_Id(userId);
        model.addAttribute("title","My Reading List ");
        model.addAttribute("bookReadingListList",bookReadingListList);

        return "book/readinglist";
    }

    @RequestMapping(value="addtoreadinglist",method = RequestMethod.POST)
    public String addToReadingList(@SessionAttribute("user") User user,Model model, @RequestParam("isbn") String isbn, @RequestParam("title") String title, @RequestParam("thumbnail") String thumbnail){



        BookReadingList readingList = new BookReadingList();

        Integer userId = new Integer(user.getId());
        Optional<User> u = userDao.findById(userId);

        if(u.isPresent()){
            user = u.get();

            //Check if the Book is already in the Reading List then dont add the book.
            if (bookReadingListDao.findByIsbnAndUser_Id(isbn,userId).isEmpty()) {
                readingList.setUser(user);
                readingList.setBookTitle(title);
                readingList.setBookThumbNailImage(thumbnail);
                readingList.setIsbn(isbn);
                bookReadingListDao.save(readingList);
            }
            else{
                model.addAttribute("error","You already have the " + title + " in your realing list");
            }

        }

        List<BookReadingList> bookReadingListList = bookReadingListDao.findByUser_Id(userId);
        model.addAttribute("title","My Reading List ");
        model.addAttribute("bookReadingListList",bookReadingListList);

        return "book/readinglist";
    }

    @RequestMapping(value="delete/{id}",method = RequestMethod.GET)
    public String deleteBookFromReadingList(@SessionAttribute("user") User user, Model model, @PathVariable int id){

        BookReadingList readingList = new BookReadingList();

        Integer userId = new Integer(user.getId());
        Optional<User> u = userDao.findById(userId);

        if(u.isPresent()){
            user = u.get();
            bookReadingListDao.deleteById(id);
        }

        List<BookReadingList> bookReadingListList = bookReadingListDao.findByUser_Id(userId);
        model.addAttribute("title","My Reading List ");
        model.addAttribute("bookReadingListList",bookReadingListList);

        return "book/readinglist";
    }

    @RequestMapping(value="complete/{id}",method = RequestMethod.GET)
    public String markBookAsComplete(@SessionAttribute("user") User user, Model model, @PathVariable int id){

        Integer userId = new Integer(user.getId());
        Optional<User> u = userDao.findById(userId);

        if(u.isPresent()){
            user = u.get();
            Optional<BookReadingList> bookReadingList = bookReadingListDao.findById(id);
            if(bookReadingList.isPresent()){
                BookReadingList bookReadingListToUpdate = bookReadingList.get();
                bookReadingListToUpdate.setCompletedOn(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
                bookReadingListDao.save(bookReadingListToUpdate);

            }
        }

        List<BookReadingList> bookReadingListList = bookReadingListDao.findByUser_Id(userId);
        model.addAttribute("title","My Reading List ");
        model.addAttribute("bookReadingListList",bookReadingListList);

        return "book/readinglist";
    }

    private BookDetails callRestGoogleBookService(String isbn){
        RestTemplate restTemplate = new RestTemplate();
        BookDetails bookDetails = restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn,BookDetails.class);
        return bookDetails;
    }
}
