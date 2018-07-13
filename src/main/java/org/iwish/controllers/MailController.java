package org.iwish.controllers;

import org.iwish.mail.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MailController {

    @Autowired
    public EmailServiceImpl emailService;

    @RequestMapping(value = "/mailsend", method = RequestMethod.GET)
    public String createMail() {

        emailService.sendSimpleMessage("sumitsenpaney@gmail.com",
                "Test-Subject", "Testing email from Spring Boot");

        return "index";
    }

}
