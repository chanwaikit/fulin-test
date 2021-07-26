package com.mazentop.excel.service;

import com.mazentop.modules.emp.service.EmailService;
import com.mztframework.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.util.ByteArrayDataSource;
import java.util.concurrent.CompletableFuture;

@Service
public class SendEmail {
    @Autowired
    EmailService emailService;

    public void sendEmail(String title,String emailAddress, byte[] bytes,String fileName) {
        CompletableFuture.runAsync(()-> {
            Email email = Email.create().to(emailAddress)
                    .subject(title)
                    .attachments(fileName, new ByteArrayDataSource(bytes, "application/msexcel"));
            emailService.sendMail("", email);
        });

    }
}
