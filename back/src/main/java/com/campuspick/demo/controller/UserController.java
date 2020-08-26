package com.campuspick.demo.controller;

import com.campuspick.demo.config.jwt.JwtTokenUtil;
import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.dto.UserUpdateRequestDto;
import com.campuspick.demo.dto.UserVelogResponseDto;
import com.campuspick.demo.service.MailService;
import com.campuspick.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/velog.io")
@RestController
public class UserController {

    private final MailService mailService;
    private final UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/setting")
    public User getUserInfo(UUID id){
        return userService.getUserInfo(id);
    }

    @PutMapping("/setting")
    public User updateUserInfo(UUID id, @RequestBody UserUpdateRequestDto requestDto){
        return userService.updateUserInfo(id, requestDto);
    }

    @DeleteMapping("/setting")
    public UUID deleteUser(UUID id){
        userService.deleteUser(id);
        return id;
    }

    @GetMapping("/@{username}")
    public UserVelogResponseDto getUserVelog(@PathVariable String username, String tagname){
        return userService.getUserVelog(username, tagname);
    }

    @GetMapping("/@{username}/about")
    public User getUserInfo(@PathVariable String username){
        return userService.getUserInfo(username);
    }

    @PutMapping("/@{username}/about")
    public User updateUserAbout(@PathVariable String username, @RequestBody String about){
        return userService.updateUserAbout(username, about);
    }

}
