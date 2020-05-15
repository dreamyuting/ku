package com.yaorange.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.sina.com");
        properties.put("mail.smtp.auth","true");
        //创建session对象
        Session session = Session.getInstance(properties);

        //创建消息对象
        Message message = new MimeMessage(session);

        //设置发件人
        Address sendMsg = new InternetAddress("17828169429yt@sina.com");
        message.setFrom(sendMsg);

        //设置收件人
        Address getMsg = new InternetAddress("bingbing_4817@sina.com");
        message.setRecipient(Message.RecipientType.TO ,getMsg);

        //创建消息内容
        message.setSubject("hello world");//标题
        message.setText("邮件发送测试.....");//正文

        //发送邮件
        Transport smtp = session.getTransport("smtp");
        //连接POP服务器
        smtp.connect("smtp.sina.com","17828169429yt@sina.com","f6752ccf9fa71ae6");
        //发送消息方法
        smtp.sendMessage(message,message.getAllRecipients());
        System.out.println("发送成功");
    }
}
