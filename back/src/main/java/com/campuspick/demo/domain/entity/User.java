package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 32)
    private String password;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 20)
    private String displayName;

    @Column(length = 100)
    private String shortBio;

    @Lob
    @Column
    private Blob thumbnail;

    @Lob
    @Column
    private Blob profileLinks;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(length = 4)
    private String salt;

    @Builder
    public User(String email, String displayName, String username, String shortBio) {
        this.email = email;
        this.displayName = displayName;
        this.username = username;
        this.shortBio = shortBio;
    }

    public void updateUserInfo(String displayName, String shortBio, Blob thumbnail, Blob profileLinks){
        this.displayName = displayName;
        this.shortBio = shortBio;
        this.thumbnail = thumbnail;
        this.profileLinks = profileLinks;
    }

    public void updateUserAbout(String about){
        this.about = about;
    }

}
