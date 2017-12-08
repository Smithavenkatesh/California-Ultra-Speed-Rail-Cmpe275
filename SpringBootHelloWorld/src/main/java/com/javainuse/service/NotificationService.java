package com.javainuse.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("notificationService")
public class NotificationService {

  private JavaMailSender mailSender;

  @Autowired
  public NotificationService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }
  
  @Async
  public void sendEmail(SimpleMailMessage email) {
    mailSender.send(email);
  }
}