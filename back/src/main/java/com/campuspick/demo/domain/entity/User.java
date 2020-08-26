package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class User {


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 20, name = "velog_name")
    private String velogName;

    @Column(length = 100, name = "short_bio")
    private String shortBio;

    @Lob
    @Column
    private Blob thumbnail;

    @Lob
    @Column(name = "profile_links")
    private Blob profileLinks;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Builder
    public User(String email, String velogName, String username, String shortBio) {
        this.email = email;
        this.velogName = velogName+".log";
        this.username = username;
        this.shortBio = shortBio;
    }

    public void updateUserInfo(Blob thumbnail, String shortBio, String velogName, Blob profileLinks){
        this.velogName = velogName;
        this.shortBio = shortBio;
        this.thumbnail = thumbnail;
        this.profileLinks = profileLinks;
    }

    public void updateUserAbout(String about){
        this.about = about;
    }

}
