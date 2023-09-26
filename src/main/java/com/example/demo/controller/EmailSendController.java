package com.example.demo.controller;

import com.example.demo.connfig.EmailConfig;
import com.example.demo.model.Email;
import com.example.demo.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.Callable;

@Controller
public class EmailSendController {
	@Autowired
	private EmailSendService mailSender;
	@Autowired
	private EmailConfig emailConfig;

	@PostMapping("/sendEmail")
	public String sendEmail(
			@RequestParam(name = "to") String from,
			@RequestParam(name = "from") String to,
			@RequestParam(required = false, name = "subject") String subject,
			@RequestParam(required = false, name = "body") String body,
			Model model) {

		Email newEmail = new Email(to,from ,subject, body);
		mailSender.sendMail(newEmail,to);
		return "redirect:/emails";
	}
}

