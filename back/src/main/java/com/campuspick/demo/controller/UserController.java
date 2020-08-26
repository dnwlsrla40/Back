package com.campuspick.demo.controller;

import com.campuspick.demo.domain.entity.User;
import com.campuspick.demo.dto.UserVelogResponseDto;
import com.campuspick.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/setting")
    public User getUserSetting(String id){
        return userService.getUserInfo(UUID.fromString(id));
    }

    @PutMapping("/setting/thumbnail")
    public User updateUserThumbnail(String id, @RequestBody Blob thumbnail){
        return userService.updateUserThumbnail(UUID.fromString(id), thumbnail);
    }

    @PutMapping("/setting/shortbio")
    public User updateUserShortBio(String id, @RequestBody String shortBio){
        return userService.updateUserShortBio(UUID.fromString(id), shortBio);
    }

    @PutMapping("/setting/velogname")
    public User updateUserVelogName(String id, @RequestBody String velogName){
        return userService.updateUserVelogName(UUID.fromString(id), velogName);
    }

    @PutMapping("/setting/socialinfo")
    public User updateUserSocialInfo(String id, @RequestBody Blob profileLinks){
        return userService.updateUserSocialInfo(UUID.fromString(id), profileLinks);
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
