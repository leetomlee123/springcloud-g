package com.example.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component("emailtool")
public class EmailTool {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sendFrom;

    public void sendSimpleMail(String sendTo, String activeLink) {
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setSubject("DataPlatform 账号激活");

            StringBuffer sb = new StringBuffer();
            sb.append("<h1><a href=").append(activeLink).append(" style='color:blue'>点击链接，激活后使用</a></h1>");
            helper.setText(sb.toString(), true);
            String file = this.getClass().getResource("/").getFile();
            FileSystemResource fileSystemResource = new FileSystemResource(new File(file, "阿里巴巴Java开发.pdf"));
            helper.addAttachment("赠品", fileSystemResource);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}