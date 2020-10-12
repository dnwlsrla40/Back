package com.campuspick.demo.controller;

import com.campuspick.demo.util.JwtTokenUtil;
import com.campuspick.demo.domain.entity.SecurityUser;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.dto.*;
import com.campuspick.demo.service.MailService;
import com.campuspick.demo.dto.UserVelogResponseDto;
import com.campuspick.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/velog.io")
@RestController
public class UserController {

    private final MailService mailService;
    private final UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // 회원가입 메일 전송
    @PostMapping("/register-mail")
    public void sendRegister(@RequestBody MailDto mailDto) throws Exception {
        mailService.sendRegister(mailDto.getEmail());
    }

    // 회원가입 페이지로 이동
    @GetMapping("/register")
    public String register(@RequestParam String code) throws Exception{
        if(mailService.checkCode(code)){
            return mailService.getEmail(code);
        }
        return "error";
    }

    // 회원가입
    @PostMapping("/register")
    public JwtResponseDto register(@RequestBody RegisterDto registerDto) {
        SecurityUser user = new SecurityUser(userService.save(registerDto));
        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername());

        JwtResponseDto jwtResponseDto = new JwtResponseDto(accessToken, refreshToken);

        return jwtResponseDto;
    }

    // 로그인 메일 전송
    @PostMapping("/login-mail")
    public void sendLogin(@RequestBody MailDto mailDto) throws Exception {
        mailService.sendLogin(mailDto.getEmail());
    }

    // 로그인
    @GetMapping("/email-login")
    public JwtResponseDto login(@RequestParam String code) throws Exception{
        if(mailService.checkCode(code)){
            // 등록된 유저인지 검증
            String email = mailService.getEmail(code);
            SecurityUser user = new SecurityUser(userService.findByEmail(email));

            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername());

            JwtResponseDto jwtResponseDto = new JwtResponseDto(accessToken, refreshToken);

            return jwtResponseDto;
        }
        return null;
    }

    // Token 만기 Check
    @GetMapping("/check")
    public String check(@RequestParam String token) throws Exception{
        System.out.println("Boolean: " +jwtTokenUtil.isTokenExpired(token));
        System.out.println("Date : " + jwtTokenUtil.getExpirationDateFromToken(token));
        return "error";
    }

    @GetMapping("/setting")
    public User getUserSetting(String id){
        return userService.getUserInfo(UUID.fromString(id));
    }

    @PutMapping("/setting/thumbnail")
    public User updateUserThumbnail(String id, @RequestBody Blob thumbnail){
        return userService.updateUserThumbnail(UUID.fromString(id), thumbnail);
    }

    @DeleteMapping("/setting/thumbnail")
    public User deleteUserThumbnail(String id){
        return userService.deleteUserThumbnail(UUID.fromString(id));
    }

    @PutMapping("/setting/shortbio")
    public User updateUserShortBio(String id, @RequestBody String shortBio){
        return userService.updateUserShortBio(UUID.fromString(id), shortBio);
    }

    @PutMapping("/setting/velogname")
    public User updateUserVelogName(String id, @RequestBody String velogName){
        return userService.updateUserVelogName(UUID.fromString(id), velogName);
    }

    @PutMapping("/setting/profilelinks")
    public User updateUserProfileLinks(String id, @RequestBody String profileLinks){
        return userService.updateUserProfileLinks(UUID.fromString(id), profileLinks);
    }

    @DeleteMapping("/setting")
    public String deleteUser(String id){
        userService.deleteUser(UUID.fromString(id));
        return id;
    }

    @GetMapping("/@{username}")
    public UserVelogResponseDto getUserVelog(@PathVariable String username, String tagname){
        return userService.getUserVelog(username, tagname);
    }

    @GetMapping("/@{username}/about")
    public User getUserDetail(@PathVariable String username){
        return userService.getUserInfo(username);
    }

    @PutMapping("/@{username}/about")
    public User updateUserAbout(@PathVariable String username, @RequestBody String about){
        return userService.updateUserAbout(username, about);
    }

}
