package org.iwish.controllers;

import org.iwish.models.Gift;
import org.iwish.models.pojo.book.BookDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
@RequestMapping("book")
public class BookController {

    @RequestMapping(value="readinglist",method = RequestMethod.GET)
    public String displayReadingList(Model model){
        model.addAttribute("title","My Reading List");
        model.addAttribute("bookDetails",new BookDetails());

        return "book/readinglist";
    }

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

    @RequestMapping(value="addtoreadinglist",method = RequestMethod.POST)
    public String bookSearch(Model model, @RequestParam("isbn") String isbn, @RequestParam("title") String title, @RequestParam("thumbnail") String thumbnail){
        model.addAttribute("title","My Reading List : title : " + title + " isbn : " + isbn +" image url : " + thumbnail );
        model.addAttribute("bookDetails",new BookDetails());

        return "book/readinglist";
    }

    private BookDetails callRestGoogleBookService(String isbn){
        RestTemplate restTemplate = new RestTemplate();
        BookDetails bookDetails = restTemplate.getForObject("https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn,BookDetails.class);
        return bookDetails;
    }
}
