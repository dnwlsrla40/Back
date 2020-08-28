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

    @GetMapping("/velog.io/setting")
    public User getUserSetting(String id){
        return userService.getUserInfo(UUID.fromString(id));
    }

    @PutMapping("/velog.io/setting/thumbnail")
    public User updateUserThumbnail(String id, @RequestBody Blob thumbnail){
        return userService.updateUserThumbnail(UUID.fromString(id), thumbnail);
    }

    @PutMapping("/velog.io/setting/shortbio")
    public User updateUserShortBio(String id, @RequestBody String shortBio){
        return userService.updateUserShortBio(UUID.fromString(id), shortBio);
    }

    @PutMapping("/velog.io/setting/velogname")
    public User updateUserVelogName(String id, @RequestBody String velogName){
        return userService.updateUserVelogName(UUID.fromString(id), velogName);
    }

    @PutMapping("/velog.io/setting/profilelinks")
    public User updateUserProfileLinks(String id, @RequestBody String profileLinks){
        return userService.updateUserProfileLinks(UUID.fromString(id), profileLinks);
    }

    @DeleteMapping("/velog.io/setting")
    public String deleteUser(String id){
        userService.deleteUser(UUID.fromString(id));
        return id;
    }

    @GetMapping("/velog.io/@{username}")
    public UserVelogResponseDto getUserVelog(@PathVariable String username, String tagname){
        return userService.getUserVelog(username, tagname);
    }

    @GetMapping("/velog.io/@{username}/about")
    public User getUserDetail(@PathVariable String username){
        return userService.getUserInfo(username);
    }

    @PutMapping("/velog.io/@{username}/about")
    public User updateUserAbout(@PathVariable String username, @RequestBody String about){
        return userService.updateUserAbout(username, about);
    }

}
