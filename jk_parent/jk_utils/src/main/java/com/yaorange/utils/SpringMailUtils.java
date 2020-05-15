package com.yaorange.utils;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component("mailUtils")
public class SpringMailUtils {
    @Resource(name = "mailMessage")
    private SimpleMailMessage mailMessage;
    @Resource(name = "mailSender")
    private JavaMailSender mailSender;
    public boolean sendMail(String toAddress,String title,String text){
        try {
            //设置收件人
            mailMessage.setTo(toAddress);
            //设置标题
            mailMessage.setSubject(title);
            //设置正文
            mailMessage.setText(text);
            //发送邮件
            mailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            return false;
        }
    }
}
