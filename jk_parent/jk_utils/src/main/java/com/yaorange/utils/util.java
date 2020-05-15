package com.yaorange.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class util {
    public static boolean sendMail(String sendAddress,String getAddress,String title,String context ) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.sina.com");
        properties.put("mail.smtp.auth", "true");
        //创建session对象
        Session session = Session.getInstance(properties);

        //创建消息对象
        Message message = new MimeMessage(session);

        //设置发件人
        Address sendMsg = new InternetAddress(sendAddress);
        message.setFrom(sendMsg);

        //设置收件人
        Address getMsg = new InternetAddress(getAddress);
        message.setRecipient(Message.RecipientType.TO, getMsg);

        //创建消息内容
        message.setSubject(title);//标题
        message.setText(context);//正文

        //发送邮件
        Transport smtp = session.getTransport("smtp");
        //连接POP服务器
        smtp.connect("smtp.sina.com", sendAddress, "f6752ccf9fa71ae6");
        //发送消息方法
        smtp.sendMessage(message, message.getAllRecipients());
        return true;

    }

}
