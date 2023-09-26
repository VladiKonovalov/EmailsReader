package com.example.demo.controller;

import com.example.demo.connfig.EmailConfig;
import com.example.demo.model.Email;
import com.example.demo.service.EmailReciveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Controller

public class EmailReceiveController {
    @Autowired
    private final  EmailReciveService emailReceiveService;

    public EmailReceiveController(EmailReciveService emailReceiveService) {
        this.emailReceiveService = emailReceiveService;
    }

    @Autowired
    private EmailConfig emailConfig;

    public List<Email> getMessages() throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newFixedThreadPool(3); // Number of threads
    List<Future<List<Email>>> futures = new ArrayList<>();

    Callable<List<Email>> task1 = () -> emailReceiveService.getMail(emailConfig.getAccount1Host(), emailConfig.getAccount1Username(), emailConfig.getAccount1Password());
    Callable<List<Email>> task2 = () -> emailReceiveService.getMail(emailConfig.getAccount2Host(), emailConfig.getAccount2Username(), emailConfig.getAccount2Password());
    Callable<List<Email>> task3 = () -> emailReceiveService.getMail(emailConfig.getAccount3Host(), emailConfig.getAccount3Username(), emailConfig.getAccount3Password());

        futures.add(executorService.submit(task1));
        futures.add(executorService.submit(task2));
       futures.add(executorService.submit(task3));

    executorService.shutdown();

    List<Email> emails = new ArrayList<>();
     {
        for (Future<List<Email>> future : futures) {
                emails.addAll(future.get());
        }
    }

    return emails;
}

    @RequestMapping(value = "/emails", method = RequestMethod.GET)
    public String showEmails(Model model) throws InterruptedException, ExecutionException{
        model.addAttribute("messages", getMessages());
        return "emails";
    }
    @GetMapping("/email")
    public String showEmailForm(@RequestParam(name = "from") String from,
                                @RequestParam(name = "to") String to,
                                Model model) {
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("subject"," ");
        model.addAttribute("body", " ");

        return "email";
    }
}
