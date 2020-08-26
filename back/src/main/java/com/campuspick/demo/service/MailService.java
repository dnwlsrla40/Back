package com.campuspick.demo.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    // 회원가입 메일 전송
    public void sendRegister(String to_email) throws Exception{
        String code = setCode(to_email);

        String text = new StringBuffer().append("<h1>velog</h1>")
                .append("<p>안녕하세요! 회원가입을 계속하시려면 하단의 링크를 클릭하세요.<br> 만약에 실수로 요청하셨거나, 본인이 요청하지 않았다면, 이 메일을 무시하세요.</p>")
                .append("<p><a href='http://localhost:8080/velog.io/register?code="+code+"'>계속하기</a></p>" )
                .append("<p>위 버튼을 클릭하시거나, 다음 링크를 열으세요:</p>")
                .append("<p><a href='http://localhost:8080/velog.io/register?code="+code+"'>http://localhost:8080/velog.io/register?code="+code+"</a></p>" )
                .append("<p>이 링크는 24시간 동안 유효합니다.</p>")
                .toString();

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setSubject("campuspick-velog 회원가입");
            helper.setFrom("dnwlsrla40@naver.com");
            helper.setTo(to_email);
            helper.setText(text, true);
            mailSender.send(message);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    // 회원가입 메일 전송
    public void sendLogin(String to_email) throws Exception{
        String code = setCode(to_email);

        String text = new StringBuffer().append("<h1>velog</h1>")
                .append("<p>안녕하세요! 로그인을 계속하시려면 하단의 링크를 클릭하세요.<br> 만약에 실수로 요청하셨거나, 본인이 요청하지 않았다면, 이 메일을 무시하세요.</p>")
                .append("<p><a href='http://localhost:8080/velog.io/email-login?code="+code+"'>계속하기</a></p>" )
                .append("<p>위 버튼을 클릭하시거나, 다음 링크를 열으세요:</p>")
                .append("<p><a href='http://localhost:8080/velog.io/email-login?code="+code+"'>http://localhost:8080/velog.io/email-login?code="+code+"</a></p>" )
                .append("<p>이 링크는 24시간 동안 유효합니다.</p>")
                .toString();

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setSubject("campuspick-velog 로그인");
            helper.setFrom("dnwlsrla40@naver.com");
            helper.setTo(to_email);
            helper.setText(text, true);
            mailSender.send(message);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    // code 생성 및 redis에 해쉬 저장{ "email" : "to_email", "code" : "random code"}
    private String setCode(String to_email) {
        String random = RandomStringUtils.randomAlphanumeric(10);
        HashOperations hash = redisTemplate.opsForHash();
        hash.put("MailDto:"+random, "email", to_email);
        hash.put("MailDto:"+random, "code", random);
        return random;
    }

    // code 유효성 검사
    public boolean checkCode(String code) {
        HashOperations hash = redisTemplate.opsForHash();
        Map<String,String> mailInfo = hash.entries("MailDto:" + code);
        if(mailInfo.get("code").equals(code)){
            return true;
        } else{
            return false;
        }
    }

    // email 가져오기
    public String getEmail(String code){
        HashOperations hash = redisTemplate.opsForHash();
        Map<String,String> mailInfo = hash.entries("MailDto:" + code);
        return mailInfo.get("email");
    }
}
