package com.campuspick.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Blob;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Series {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "BLOB")
    private String thumbnail;

    // series url 중복 방지(url Slug 포함되야함)
    @Column(length = 100, name="url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Series(User user, String name, String thumbnail, String url){
        Assert.notNull(name, "name must be not null");
        Assert.notNull(user, "user must be not null");

        this.name = name;
        this.thumbnail = thumbnail;
        this.url = url;
        this.user = user;
    }

    // null 값이 들어올 경우 초기화
    @PrePersist
    public void prePersist(){
        this.url = this.url == null ? "/@"+this.user.getUsername()+"/series/"+this.name : this.url;
    }

}
