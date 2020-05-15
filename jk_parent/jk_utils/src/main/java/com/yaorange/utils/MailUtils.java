package com.yaorange.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    public static Boolean sendMail(String toAddress,String title,String text){
        Properties properties = new Properties();
        //邮件发送的地址：properties.put("mail.smtp.host","邮件服务器地址");
        properties.put("mail.smtp.host","smtp.sina.com");
        properties.put("mail.smtp.auth","true");
        //创建session对象
        Session session = Session.getInstance(properties);

        //创建消息对象
        Message message = new MimeMessage(session);

        try {
            //设置发件人
            Address sendMsg = new InternetAddress("yaorange_test@sina.com");
            message.setFrom(sendMsg);//将发件人添加到消息对象中

            //设置收件人
            Address getMsg = new InternetAddress(toAddress);
            message.setRecipient(Message.RecipientType.TO ,getMsg);

            //创建消息内容
            message.setSubject(title);//标题
            message.setText(text);//正文

            //发送邮件
            Transport smtp = session.getTransport("smtp");
            //连接POP服务器
            smtp.connect("smtp.sina.com","yaorange_test@sina.com","14f180d729be2dfc");

            smtp.sendMessage(message,message.getAllRecipients());
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    //测试
    public static void main(String[] args) {
        Boolean rs = sendMail("286526748@qq.com", "test", "邮件测试");
        if(rs){
            System.out.println("发送成功");
        }
    }
}
